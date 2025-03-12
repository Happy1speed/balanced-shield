package net.happyspeed.balancedshield.mixin;
import net.happyspeed.balancedshield.BalancedShieldMod;
import net.happyspeed.balancedshield.access.PlayerClassAccess;
import net.happyspeed.balancedshield.config.ModConfigs;
import net.happyspeed.balancedshield.network.S2CShieldToleranceSyncPacket;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntity.class, priority = 900)
abstract class PlayerEntityMixin extends LivingEntity implements PlayerClassAccess {

    @Shadow public abstract void disableShield(boolean sprinting);

    @Unique
    public int shieldTolerance = 12;

    @Unique
    public int shieldRecharge = 100;

    @Unique
    public int shieldIFrames = 0;

    @Unique
    private float playerPastAttackCooldown = 1.0f;

    @Unique
    public boolean wasBlockingFrameBefore = false;

    @Unique
    public int ticksSinceBlock = 0;

    @Unique
    int pastShieldTolerance = shieldTolerance;

    @Shadow
    private final ItemCooldownManager itemCooldownManager;

    @Shadow
    public ItemCooldownManager getItemCooldownManager() {
        return this.itemCooldownManager;
    }

    @Shadow @Final private PlayerInventory inventory;

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow
    public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world, ItemCooldownManager itemCooldownManager) {
        super(entityType, world);
        this.itemCooldownManager = itemCooldownManager;

    }

    @Override
    public float balancedShield_1_20_1$accessPlayerPastCooldown() {
        return this.playerPastAttackCooldown;
    }

    //If its a player, Test if the attack is charged enough, if not, return weak value.
    @Unique
    private void calcShieldLogic(LivingEntity attacker, Integer shieldhitstrong, Integer shieldhitweak) {
        if (attacker instanceof PlayerEntity player) {
            if (((PlayerClassAccess) player).balancedShield_1_20_1$accessPlayerPastCooldown() > 0.75f) {
                this.shieldTolerance -= shieldhitstrong;
            }
            else {
                this.shieldTolerance -= shieldhitweak;
            }
        }
        else {
            this.shieldTolerance -= shieldhitstrong;
        }
    }

    //Shield Tier Evaluation
    @Unique
    private int getMaxShieldTolerance() {
        Hand holdingHand = this.getActiveHand();
        ItemStack shieldStack = inventory.getMainHandStack();
        if (holdingHand == Hand.MAIN_HAND) {
            shieldStack = inventory.getMainHandStack();
        }
        if (holdingHand == Hand.OFF_HAND) {
            shieldStack = inventory.getStack(PlayerInventory.OFF_HAND_SLOT);
        }
        //Tiers
        if (shieldStack.isIn(ModTags.Items.TIERONECHARGE)) {
            return 6;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERTWOCHARGES)) {
            return 8;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERTHREECHARGES)) {
            return 12;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERFOURCHARGES)) {
            return 16;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERFIVECHARGES)) {
            return 20;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERSIXCHARGES)) {
            return 24;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERSEVENCHARGES)) {
            return 28;
        }
        else if (shieldStack.isIn(ModTags.Items.TIEREIGHTCHARGES)) {
            return 32;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERNINECHARGES)) {
            return 36;
        }
        else if (shieldStack.isIn(ModTags.Items.TIERTENCHARGES)) {
            return 40;
        }
        //Default to 12 if shield isn't in tags
        return 12;
    }

    @Inject(method = "takeShieldHit", at = @At("HEAD"), cancellable = true)
    public void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        super.takeShieldHit(attacker);
        if (!(this.getActiveItem().isIn(ModTags.Items.SHIELD))) {
            ci.cancel();
            return;
        }
        if (ModConfigs.BLOCKPARRYENABLED) {
            if (this.getActiveItem().getMaxUseTime() - this.itemUseTimeLeft <= ModConfigs.BLOCKPARRYTICKWINDOW) {
                if (!this.getWorld().isClient()) {
                    this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, this.getSoundCategory(), (float) 0.5, 0.95f);
                }
                ci.cancel();
                return;
            }
        }
        this.shieldRecharge = 160;
        if (this.shieldIFrames < 1) {
            int maxShield = this.getMaxShieldTolerance();
            if (this.getMaxShieldTolerance() < this.shieldTolerance) {
                this.shieldTolerance = this.getMaxShieldTolerance();
            }
            if (attacker.getMainHandStack().isIn(ModTags.Items.POWERFULLHIT) || attacker.getType().isIn(ModTags.Entity.POWERFULLHITENTITY)) {
                this.calcShieldLogic(attacker, maxShield, 2);
                this.shieldIFrames = 4;

            } else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERSIXTEENHIT) || attacker.getType().isIn(ModTags.Entity.POWERSIXTEENHITENTITY)) {
                this.calcShieldLogic(attacker, 16, 2);
                this.shieldIFrames = 4;

            } else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERTWELVEHIT) || attacker.getType().isIn(ModTags.Entity.POWERTWELVEHITENTITY)) {
                this.calcShieldLogic(attacker, 12, 2);
                this.shieldIFrames = 4;

            } else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERSIXHIT) || attacker.getType().isIn(ModTags.Entity.POWERSIXHITENTITY)) {
                this.calcShieldLogic(attacker, 6, 1);
                this.shieldIFrames = 4;

            } else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERFOURHIT) || attacker.getType().isIn(ModTags.Entity.POWERFOURHITENTITY)) {
                this.calcShieldLogic(attacker, 4, 2);
                this.shieldIFrames = 4;

            } else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERTHREEHIT) || attacker.getType().isIn(ModTags.Entity.POWERTHREEHITENTITY)) {
                this.calcShieldLogic(attacker, 3, 1);
                this.shieldIFrames = 4;

            } else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERTWOHIT) || attacker.getType().isIn(ModTags.Entity.POWERTWOHITENTITY)) {
                this.calcShieldLogic(attacker, 2, 1);
                this.shieldIFrames = 4;

            } else if (attacker.isPlayer()) {
                this.shieldTolerance--;
                this.shieldIFrames = 4;
            } else {
                this.shieldTolerance -= ModConfigs.DEFAULTENTITYHITPOINTS;
                this.shieldIFrames = 4;
            }
        }
        if (this.shieldTolerance < 0) {
            this.shieldTolerance = 0;
        }
        if (!this.getWorld().isClient()) {
            var player = ((PlayerEntity) ((Object) this));
            if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                S2CShieldToleranceSyncPacket.send(serverPlayerEntity, this.getId(), (float) shieldTolerance);
            }
        }
        //this.sendMessage(Text.literal("Shield: " + String.valueOf((int) (((double) this.shieldTolerance / getMaxShieldTolerance()) * 100)) + "%").formatted(Formatting.GOLD).formatted(Formatting.BOLD),true);
        updateDisableShieldCheck();
        ci.cancel();
    }

    //Shield Disable Time Teirs.
    @Unique
    private void calcDisableTime() {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack currentitem = inventory.getStack(i);
            int c = -1;
            if (currentitem.isIn(ModTags.Items.SHIELDTIERONE)) {
                c = 170;
            }
            else if (currentitem.isIn(ModTags.Items.SHIELDTIERTWO)) {
                c = 150;
            }
            else if (currentitem.isIn(ModTags.Items.SHIELDTIERTHREE)) {
                c = 130;
            }
            else if (currentitem.isIn(ModTags.Items.SHIELDTIERFOUR)) {
                c = 100;
            }
            else if (currentitem.getItem() instanceof ShieldItem || currentitem.isIn(ModTags.Items.SHIELD)) {
                c = 200;
            }

            //Set Cooldown
            if (c != -1) {
                this.getItemCooldownManager().remove(inventory.getStack(i).getItem());
                this.getItemCooldownManager().set(inventory.getStack(i).getItem(), c);
            }
        }
    }

    //If Tolerance is under 1, Fix shield state.
    @Unique
    private void updateDisableShieldCheck() {
        if (this.shieldTolerance < 1) {
            this.disableShield(true);
            this.shieldTolerance = this.getMaxShieldTolerance();
        }
    }

    @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    public void disableShield(boolean sprinting, CallbackInfo ci) {
        calcDisableTime();
        this.clearActiveItem();
        this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
        ci.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (!this.getWorld().isClient()) {
            if (!this.getWorld().isClient()) {
                var player = ((PlayerEntity) ((Object) this));
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    S2CShieldToleranceSyncPacket.send(serverPlayerEntity, this.getId(), (float) shieldTolerance);
                }
            }
        }
        if (this.shieldRecharge < 1) {
            this.shieldTolerance = this.getMaxShieldTolerance();
        }
        else {
            this.shieldRecharge --;
        }
        updateDisableShieldCheck();
        if (this.shieldIFrames > 0) {
            this.shieldIFrames--;
        }
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attackInject(CallbackInfo ci) {
        this.playerPastAttackCooldown = this.getAttackCooldownProgress(1.0f);
    }

    //A very incompatible Method
    @Override
    public boolean blockedByShield(DamageSource source) {
        Entity entity = source.getSource();
        boolean bl = false;
        if (entity instanceof PersistentProjectileEntity persistentProjectileEntity) {
            if (persistentProjectileEntity.getPierceLevel() > 0) {
                bl = true;
            }
        }

        if (!source.isIn(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking() && !bl) {
            Vec3d vec3d = source.getPosition();
            if (vec3d != null) {
                Vec3d vec3d2 = this.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.relativize(this.getPos()).normalize();
                vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
                if (vec3d3.dotProduct(vec3d2) < 0.0) {
                    if (this.isPlayer() && this.shieldIFrames < 1) {
                        if (!(this.getActiveItem().getMaxUseTime() - this.itemUseTimeLeft <= ModConfigs.BLOCKPARRYTICKWINDOW) || !ModConfigs.BLOCKPARRYENABLED) {
                            if (this.getMaxShieldTolerance() < this.shieldTolerance) {
                                this.shieldTolerance = this.getMaxShieldTolerance();
                            }
                            if (source.isIn(DamageTypeTags.IS_PROJECTILE)) {
                                this.shieldTolerance -= ModConfigs.PROJECTILEHITPOINTS;
                                this.shieldRecharge = 160;
                                this.shieldIFrames = 4;
                            } else if (source.isOf(DamageTypes.FIREWORKS)) {
                                this.shieldTolerance -= ModConfigs.FIREWORKHITPOINTS;
                                this.shieldRecharge = 160;
                                this.shieldIFrames = 4;
                            } else if (source.isIn(DamageTypeTags.IS_EXPLOSION) && source.isIndirect()) {
                                this.shieldTolerance -= ModConfigs.BLOCKEXPLOSIONHITPOINTS;
                                this.shieldRecharge = 160;
                                this.shieldIFrames = 4;
                            } else if (source.isOf(DamageTypes.INDIRECT_MAGIC)) {
                                this.shieldTolerance -= ModConfigs.INDIRECTMAGICHITPOINTS;
                                this.shieldRecharge = 160;
                                this.shieldIFrames = 4;
                            }
                            if (this.shieldTolerance < 0) {
                                this.shieldTolerance = 0;
                            }
                            if (!(this.getActiveItem().isIn(ModTags.Items.SHIELD))) {
                                return true;
                            }
                            if (!this.getWorld().isClient()) {
                                var player = ((PlayerEntity) ((Object) this));
                                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                                    S2CShieldToleranceSyncPacket.send(serverPlayerEntity, this.getId(), (float) shieldTolerance);
                                }
                            }
                            //this.sendMessage(Text.literal("Shield: " + String.valueOf((int) (((double) this.shieldTolerance / getMaxShieldTolerance()) * 100)) + "%").formatted(Formatting.GOLD).formatted(Formatting.BOLD), true);
                        }
                        if ((this.getActiveItem().getMaxUseTime() - this.itemUseTimeLeft <= ModConfigs.BLOCKPARRYTICKWINDOW) && ModConfigs.BLOCKPARRYENABLED) {
                            if (!this.getWorld().isClient()) {
                                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, this.getSoundCategory(), (float) 0.5, 0.9f);
                            }
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }
}