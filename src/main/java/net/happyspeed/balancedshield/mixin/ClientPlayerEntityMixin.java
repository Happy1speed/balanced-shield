package net.happyspeed.balancedshield.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyConstant(method = "tickMovement", constant = @Constant(floatValue = 0.2f, ordinal = 0))
    private float shieldMovementStrafe(float constant) {
        ItemStack shield = this.getActiveItem();
        if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTONE)) {
            return 0.1f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTTWO)) {
            return 0.3f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTTHREE)) {
            return 0.4f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTFOUR)) {
            return 0.5f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTFIVE)) {
            return 0.6f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTSIX)) {
            return 0.7f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTSEVEN)) {
            return 0.8f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTEIGHT)) {
            return 0.9f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDSTRAFEMOVEMENTNINE)) {
            return 1.0f;
        }

        return constant;
    }

    @ModifyConstant(method = "tickMovement", constant = @Constant(floatValue = 0.2f, ordinal = 1))
    private float shieldMovement(float constant) {
        ItemStack shield = this.getActiveItem();
        if (shield.isIn(ModTags.Items.SHIELDMOVEMENTONE)) {
            return 0.1f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTTWO)) {
            return 0.3f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTTHREE)) {
            return 0.4f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTFOUR)) {
            return 0.5f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTFIVE)) {
            return 0.6f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTSIX)) {
            return 0.7f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTSEVEN)) {
            return 0.8f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTEIGHT)) {
            return 0.9f;
        }
        else if (shield.isIn(ModTags.Items.SHIELDMOVEMENTNINE)) {
            return 1.0f;
        }

        return constant;
    }

    @ModifyExpressionValue(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean noDisableSprinting(boolean original){
        return (!this.getActiveItem().isIn(ModTags.Items.SHIELDSPRINT)) && original;
    }
}
