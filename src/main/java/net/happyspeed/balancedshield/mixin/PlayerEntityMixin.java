package net.happyspeed.balancedshield.mixin;
import net.fabricmc.loader.api.FabricLoader;
import net.happyspeed.balancedshield.BalancedShieldMod;
import net.happyspeed.balancedshield.BalancedShieldModClient;
import net.happyspeed.balancedshield.access.MyClassAccess;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity implements MyClassAccess {

    @Shadow public abstract void disableShield(boolean sprinting);

    @Unique
    public int shieldTolerance = 12;

    @Unique
    public int shieldRecharge = 100;

    @Unique
    public int fixcount = 1;

    @Unique
    private float playerPastAttackCooldown = 1.0f;

//    Example of Duck interfaces:
//
//    io/github/mymod/mixin/MyClassMixin
//// This mixin implements the duck interface onto `MyClass`
//    @Mixin(MyClass.class)
//    public class MyClassMixin implements MyClassAccess {
//        @Override
//        public void access() {
//            System.out.println("Accessed!");
//        }
//    }
//
//
//    io/github/mymod/access/MyClassAccess
//    // The duck interface being implemented onto `MyClass`
//    public interface MyClassAccess {
//        void access();
//    }
//
//
//    To use it:
//    public class Container {
//        public void slapHaykam(MyClass instance) {
//            ((MyClassAccess)instance).access(); // Will print "Accessed!"
//        }
//    }

    @Shadow
    public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world, ItemCooldownManager itemCooldownManager) {
        super(entityType, world);
        this.itemCooldownManager = itemCooldownManager;

    }

    @Override
    public float access() {
        return this.playerPastAttackCooldown;
    }

    @Unique
    private void calcShieldLogic(LivingEntity attacker, Integer shieldhitstrong, Integer shieldhitweak) {
        if (attacker instanceof PlayerEntity player) {
            if (((MyClassAccess) player).access() > 0.75f) {
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

    @Inject(method = "takeShieldHit", at = @At("HEAD"), cancellable = true)
    public void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        super.takeShieldHit(attacker);
        this.shieldRecharge = 160;
        if (attacker.getMainHandStack().isIn(ItemTags.AXES) || attacker.getMainHandStack().isIn(ModTags.Items.POWERFULLHIT)) {
            if (attacker instanceof PlayerEntity player) {
                if (((MyClassAccess) player).access() > 0.75) {
                    this.disableShield(true);
                    this.shieldTolerance = 12;
                }
                else {
                    this.shieldTolerance -= 2;
                }
            }
            else {
                this.disableShield(true);
                this.shieldTolerance = 12;
            }

        }
        else if (attacker.getMainHandStack().isIn(ItemTags.SWORDS) || attacker.getMainHandStack().isIn(ModTags.Items.POWERTHIRDHIT)) {
            this.calcShieldLogic(attacker, 4,2);
        }
        else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERHALFHIT)) {
            this.calcShieldLogic(attacker, 6, 1);
        }
        else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERQUARTERHIT)) {
            this.calcShieldLogic(attacker, 3,1);
        }
        else if (attacker.getMainHandStack().isIn(ModTags.Items.POWERSIXTHHIT)) {
            this.calcShieldLogic(attacker, 2,1);
        }
        else if (attacker.isPlayer()) {
            this.shieldTolerance --;
        }
        else {
            this.shieldTolerance -= 4;
        }
        if (this.shieldTolerance < 0) {
            this.shieldTolerance = 0;
        }
        this.sendMessage(Text.literal("Shield: " + String.valueOf((int) (((double) this.shieldTolerance / 12) * 100)) + "%").formatted(Formatting.GOLD).formatted(Formatting.BOLD),true);

    }
    @Shadow
    private final ItemCooldownManager itemCooldownManager;

    @Shadow
    public ItemCooldownManager getItemCooldownManager() {
        return this.itemCooldownManager;
    }

    @Shadow @Final private PlayerInventory inventory;

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    public void disableShield(boolean sprinting, CallbackInfo ci) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i).isIn(ModTags.Items.SHIELDTIERONE)) {
                this.getItemCooldownManager().set(inventory.getStack(i).getItem(), 150);
            }
            else if (inventory.getStack(i).isIn(ModTags.Items.SHIELDTIERTWO)) {
                this.getItemCooldownManager().set(inventory.getStack(i).getItem(), 100);
            }
            else if (inventory.getStack(i).getItem() instanceof ShieldItem shieldItem) {
                this.getItemCooldownManager().set(shieldItem, 200);
            }
            if (inventory.getStack(i).isIn(ModTags.Items.SHIELD)) {
                this.getItemCooldownManager().set(inventory.getStack(i).getItem(), 200);
            }

        }
        this.clearActiveItem();
        this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
        ci.cancel();
    }
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (this.shieldRecharge < 1) {
            this.shieldTolerance = 12;
        }
        else {
            this.shieldRecharge --;
        }
        if (this.shieldTolerance < 1) {
            this.disableShield(true);
            this.shieldTolerance = 12;
        }
        if (this.fixcount > 0) {
            this.fixcount --;
        }
    }
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attackInject(CallbackInfo ci) {
        this.playerPastAttackCooldown = this.getAttackCooldownProgress(1.0f);
    }
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
                    if (this.isPlayer() && this.fixcount < 1) {
                        if (source.isIn(DamageTypeTags.IS_PROJECTILE)) {
                            this.shieldTolerance -= 3;
                            this.shieldRecharge = 160;
                            this.fixcount = 1;
                        } else if (source.isOf(DamageTypes.FIREWORKS)) {
                            this.shieldTolerance -= 12;
                            this.shieldRecharge = 160;
                            this.fixcount = 1;
                        } else if (source.isIn(DamageTypeTags.IS_EXPLOSION) && source.isIndirect()) {
                            this.shieldTolerance -= 6;
                            this.shieldRecharge = 160;
                            this.fixcount = 1;
                        } else if (source.isOf(DamageTypes.INDIRECT_MAGIC)) {
                            this.shieldTolerance -= 12;
                            this.shieldRecharge = 160;
                            this.fixcount = 1;
                        }
                        if (this.shieldTolerance < 0) {
                            this.shieldTolerance = 0;
                        }
                        this.sendMessage(Text.literal("Shield: " + String.valueOf((int) (((double) this.shieldTolerance / 12) * 100)) + "%").formatted(Formatting.GOLD).formatted(Formatting.BOLD),true);
                    }
                    return true;
                }
            }
        }

        return false;
    }
}