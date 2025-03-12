package net.happyspeed.balancedshield;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.happyspeed.balancedshield.network.S2CShieldToleranceSyncPacket;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.SweepAttackParticle;

public class BalancedShieldModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(S2CShieldToleranceSyncPacket.ID, new S2CShieldToleranceSyncPacket.Receiver());
    }
}
