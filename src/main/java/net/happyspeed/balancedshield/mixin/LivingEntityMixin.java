package net.happyspeed.balancedshield.mixin;

import net.happyspeed.balancedshield.BalancedShieldMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract void takeKnockback(double strength, double x, double z);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    public int tol = 12;

//    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
//    public void blockedByShield(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
//        Entity entity = source.getSource();
//        if (this.isPlayer()) {
//            BalancedShieldMod.LOGGER.info("Say");
//            if (source.isIn(DamageTypeTags.IS_PROJECTILE)) {
//                this.shieldTolerance -= 3;
//                BalancedShieldMod.LOGGER.info(String.valueOf(this.shieldTolerance));
//            } else if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
//                this.shieldTolerance -= 4;
//            } else if (source.isOf(DamageTypes.FIREWORKS)) {
//                this.shieldTolerance -= 12;
//            } else if (source.isOf(DamageTypes.INDIRECT_MAGIC)) {
//                this.shieldTolerance -= 12;
//            }
//        }
//    }
}
