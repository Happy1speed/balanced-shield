package net.happyspeed.balancedshield;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BalancedShieldMod implements ModInitializer {
	public static final String MOD_ID = "balancedshield";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean GuardingModLoaded = false;

	@Override
	public void onInitialize() {
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(BalancedShieldMod.MOD_ID);
		if (FabricLoader.getInstance().isModLoaded("guarding")) {
			GuardingModLoaded = true;
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(BalancedShieldMod.MOD_ID, "balanced_shield_guarding_compat"), (ModContainer) modContainer.get(), Text.translatable("pack.balancedshieldguardingcompat"), ResourcePackActivationType.ALWAYS_ENABLED);
		}
		LOGGER.info("Balanced Shield Initialized");
	}
}