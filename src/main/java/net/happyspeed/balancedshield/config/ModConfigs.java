package net.happyspeed.balancedshield.config;

import com.mojang.datafixers.util.Pair;
import net.happyspeed.balancedshield.BalancedShieldMod;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int PROJECTILEHITPOINTS;
    public static int FIREWORKHITPOINTS;
    public static int BLOCKEXPLOSIONHITPOINTS;
    public static int INDIRECTMAGICHITPOINTS;
    public static int DEFAULTENTITYHITPOINTS;
    public static boolean BLOCKPARRYENABLED;
    public static int BLOCKPARRYTICKWINDOW;
    public static boolean CLIENTPULSEDISABLEDCOLOR;
    public static boolean CLIENTSHOWWORDINFO;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(BalancedShieldMod.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("projectile_hit_points", 3));
        configs.addKeyValuePair(new Pair<>("firework_hit_points", 12));
        configs.addKeyValuePair(new Pair<>("block_explosion_hit_points", 6));
        configs.addKeyValuePair(new Pair<>("indirect_magic_hit_points", 12));
        configs.addKeyValuePair(new Pair<>("block_parry_enabled", false));
        configs.addKeyValuePair(new Pair<>("block_parry_tick_window", 3));
        configs.addKeyValuePair(new Pair<>("client_show_word_info", true));
        configs.addKeyValuePair(new Pair<>("client_pulse_disabled_color", true));
    }

    private static void assignConfigs() {
        PROJECTILEHITPOINTS = CONFIG.getOrDefault("projectile_hit_points", 3);
        FIREWORKHITPOINTS = CONFIG.getOrDefault("firework_hit_points", 12);
        BLOCKEXPLOSIONHITPOINTS = CONFIG.getOrDefault("block_explosion_hit_points", 6);
        INDIRECTMAGICHITPOINTS = CONFIG.getOrDefault("indirect_magic_hit_points", 12);
        DEFAULTENTITYHITPOINTS = CONFIG.getOrDefault("default_entity_hit_points", 4);
        BLOCKPARRYENABLED = CONFIG.getOrDefault("block_parry_enabled", false);
        BLOCKPARRYTICKWINDOW = CONFIG.getOrDefault("block_parry_tick_window", 3);
        CLIENTSHOWWORDINFO = CONFIG.getOrDefault("client_show_word_info", true);
        CLIENTPULSEDISABLEDCOLOR = CONFIG.getOrDefault("client_pulse_disabled_color", true);




        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}