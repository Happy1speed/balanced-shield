package net.happyspeed.balancedshield;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BalancedShieldMod implements ModInitializer {
	public static final String MOD_ID = "balancedshield";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Balanced Shield is now Loading");
	}
}