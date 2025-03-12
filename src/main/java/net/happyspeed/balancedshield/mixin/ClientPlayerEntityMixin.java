package net.happyspeed.balancedshield.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.happyspeed.balancedshield.access.ClientPlayerClassAccess;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements ClientPlayerClassAccess {
    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Unique
    int clientShieldTolerance = this.balanced_shield$getMaxShieldToleranceClient();

    @Unique
    public int balanced_shield$getClientShieldTolerance() {
        return clientShieldTolerance;
    }

    @Unique
    public void balanced_shield$setClientShieldTolerance(int clientShieldTolerance) {
        this.clientShieldTolerance = clientShieldTolerance;
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

    @Unique
    public int balanced_shield$getMaxShieldToleranceClient() {
        Hand holdingHand = this.getActiveHand();
        ItemStack shieldStack = this.getInventory().getMainHandStack();

        if (holdingHand == Hand.MAIN_HAND) {
            shieldStack = getInventory().getMainHandStack();
        }
        if (holdingHand == Hand.OFF_HAND) {
            shieldStack = getInventory().getStack(PlayerInventory.OFF_HAND_SLOT);
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

}
