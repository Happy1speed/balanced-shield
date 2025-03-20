package net.happyspeed.balancedshield;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.happyspeed.balancedshield.access.ClientPlayerClassAccess;
import net.happyspeed.balancedshield.client.ShieldGUI;
import net.happyspeed.balancedshield.config.ModConfigs;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.intellij.lang.annotations.JdkConstants;
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
		if (FabricLoader.getInstance().isModLoaded("guarding") && ModConfigs.GUARDINGDATAPACKENABLED) {
			GuardingModLoaded = true;
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(BalancedShieldMod.MOD_ID, "balanced_shield_guarding_compat"), (ModContainer) modContainer.get(), Text.translatable("pack.balancedshieldguardingcompat"), ResourcePackActivationType.ALWAYS_ENABLED);
		}
		if (FabricLoader.getInstance().isModLoaded("the_bumblezone") && ModConfigs.BUMBLEZONEDATAPACKENABLED) {
			BumblezoneModLoaded = true;
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(BalancedShieldMod.MOD_ID, "balanced_shield_bumblezone_compat"), (ModContainer) modContainer.get(), Text.translatable("pack.balancedshieldbumblezonecompat"), ResourcePackActivationType.ALWAYS_ENABLED);
		}

		if (ModConfigs.DEFAULTVALUESDATAPACKENABLED) {
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(BalancedShieldMod.MOD_ID, "balanced_shield_default_values"), (ModContainer) modContainer.get(), Text.translatable("pack.balancedshielddefaults"), ResourcePackActivationType.ALWAYS_ENABLED);
		}

		LOGGER.info("Balanced Shield Initialized");

		ShieldGUI.initGUI();
	}
}