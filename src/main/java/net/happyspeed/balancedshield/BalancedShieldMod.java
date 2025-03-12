package net.happyspeed.balancedshield;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.happyspeed.balancedshield.access.ClientPlayerClassAccess;
import net.happyspeed.balancedshield.config.ModConfigs;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BalancedShieldMod implements ModInitializer {
	public static final String MOD_ID = "balancedshield";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean GuardingModLoaded = false;
	public static boolean BumblezoneModLoaded = false;

	//Todo Config Option for All #Shield being added to the Mod Shield Tag

	public static Identifier id(String value) {
		return new Identifier(MOD_ID, value);
	}

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(BalancedShieldMod.MOD_ID);
		if (FabricLoader.getInstance().isModLoaded("guarding")) {
			GuardingModLoaded = true;
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(BalancedShieldMod.MOD_ID, "balanced_shield_guarding_compat"), (ModContainer) modContainer.get(), Text.translatable("pack.balancedshieldguardingcompat"), ResourcePackActivationType.ALWAYS_ENABLED);
		}
		if (FabricLoader.getInstance().isModLoaded("the_bumblezone")) {
			BumblezoneModLoaded = true;
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(BalancedShieldMod.MOD_ID, "balanced_shield_bumblezone_compat"), (ModContainer) modContainer.get(), Text.translatable("pack.balancedshieldbumblezonecompat"), ResourcePackActivationType.ALWAYS_ENABLED);
		}
		LOGGER.info("Balanced Shield Initialized");




		HudRenderCallback.EVENT.register((context, tickDelta) -> {
			MinecraftClient mc = MinecraftClient.getInstance();
			Window window = mc.getWindow();

			if (mc.player != null && window != null) {
				ItemCooldownManager cooldownManager = mc.player.getItemCooldownManager();
				if (mc.player.getOffHandStack().isIn(ModTags.Items.SHIELD) || mc.player.getOffHandStack().getItem() instanceof ShieldItem || mc.player.getMainHandStack().isIn(ModTags.Items.SHIELD) || mc.player.getMainHandStack().getItem() instanceof ShieldItem) {
					if (mc.player.getStackInHand(mc.player.getActiveHand()).isIn(ModTags.Items.SHIELD) || mc.player.getStackInHand(mc.player.getActiveHand()).getItem() instanceof ShieldItem) {
						int Orange = ColorHelper.Argb.getArgb(240, 222, 123, 42);
						int Green = ColorHelper.Argb.getArgb(220, 10, 193, 4);
						int Gray = ColorHelper.Argb.getArgb(200, 137, 135, 135);


						int barX1 = (int) (((window.getScaledWidth() * 0.5f) - (((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient() * 0.5) * window.getScaleFactor()));
						int barY1 = (int) (window.getScaledHeight() * 0.5f + (9 * window.getScaleFactor()));
						int barX2 = (int) (barX1 + (((ClientPlayerClassAccess) mc.player).balanced_shield$getClientShieldTolerance()) * window.getScaleFactor());
						int barY2 = (int) ((window.getScaledHeight() * 0.5f) + (12 * window.getScaleFactor()));

						int barBGX1 = (int) (((window.getScaledWidth() * 0.5f) - (((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient() * 0.5) * window.getScaleFactor()));
						int barBGY1 = (int) (window.getScaledHeight() * 0.5f + (9 * window.getScaleFactor()));
						int barBGX2 = (int) (barBGX1 + (((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient()) * window.getScaleFactor());
						int barBGY2 = (int) ((window.getScaledHeight() * 0.5f) + (12 * window.getScaleFactor()));

						if (!(cooldownManager.getCooldownProgress(mc.player.getStackInHand(mc.player.getActiveHand()).getItem(), tickDelta) == 0)) {
							context.fill(barBGX1, barBGY1, barBGX2, barBGY2, 1, Orange);
						}
						else if (((ClientPlayerClassAccess) mc.player).balanced_shield$getClientShieldTolerance() < ((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient()) {
							if (mc.player.getStackInHand(mc.player.getActiveHand()).isIn(ModTags.Items.SHIELD) || mc.player.getStackInHand(mc.player.getActiveHand()).getItem() instanceof ShieldItem) {
								context.fill(barX1, barY1, barX2, barY2, 1, Green);
								context.fill(barBGX1, barBGY1, barBGX2, barBGY2, 0, Gray);
							}
						}

						//Plan: S2C packet with tolerance, duck interface for client player, get variable from cast and draw here, server sends packet variable update (copy var declared at start of tick loop, checks if same at end of loop)
					}
				}
			}

		});
	}
}