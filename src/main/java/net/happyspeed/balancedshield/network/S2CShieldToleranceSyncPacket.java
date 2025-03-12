package net.happyspeed.balancedshield.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.happyspeed.balancedshield.BalancedShieldMod;
import net.happyspeed.balancedshield.access.ClientPlayerClassAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class S2CShieldToleranceSyncPacket {
	public static final Identifier ID = BalancedShieldMod.id("sc_shield_sync");

	public static void send(ServerPlayerEntity player, int id, float tolerance) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(id);
		buf.writeFloat(tolerance);
		ServerPlayNetworking.send(player, ID, buf);

	}

	public static class Receiver implements ClientPlayNetworking.PlayChannelHandler {
		@Override
		public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
			int id = buf.readInt();
			float tolerance = buf.readFloat();
			client.execute(() -> {
				Entity entity = handler.getWorld().getEntityById(id);
				if (entity != null) {
					if (entity instanceof PlayerEntity player) {
						((ClientPlayerClassAccess) player).balanced_shield$setClientShieldTolerance((int) (tolerance));
					}
				}
			});
		}
	}
}