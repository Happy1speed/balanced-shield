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
    public static boolean DEFAULTVALUESDATAPACKENABLED;
    public static boolean BUMBLEZONEDATAPACKENABLED;
    public static boolean GUARDINGDATAPACKENABLED;
    public static boolean MODSHIELDTAGENABLED;
    public static boolean CLIENTPULSEDISABLEDCOLOR;
    public static boolean CLIENTSHOWWORDINFO;
    public static int CLIENTSHIELDINDICATOROFFSETY;

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
        configs.addKeyValuePair(new Pair<>("default_values_datapack_enabled", true));
        configs.addKeyValuePair(new Pair<>("bumblezone_datapack_enabled", true));
        configs.addKeyValuePair(new Pair<>("guarding_datapack_enabled", true));
        configs.addKeyValuePair(new Pair<>("mod_shield_tag_required_to_act_as_shield", true));
        configs.addKeyValuePair(new Pair<>("client_show_word_info", true));
        configs.addKeyValuePair(new Pair<>("client_pulse_disabled_color", true));
        configs.addKeyValuePair(new Pair<>("client_shield_indicator_offset_y", 0));
    }

    private static void assignConfigs() {
        PROJECTILEHITPOINTS = CONFIG.getOrDefault("projectile_hit_points", 3);
        FIREWORKHITPOINTS = CONFIG.getOrDefault("firework_hit_points", 12);
        BLOCKEXPLOSIONHITPOINTS = CONFIG.getOrDefault("block_explosion_hit_points", 6);
        INDIRECTMAGICHITPOINTS = CONFIG.getOrDefault("indirect_magic_hit_points", 12);
        DEFAULTENTITYHITPOINTS = CONFIG.getOrDefault("default_entity_hit_points", 4);
        BLOCKPARRYENABLED = CONFIG.getOrDefault("block_parry_enabled", false);
        BLOCKPARRYTICKWINDOW = CONFIG.getOrDefault("block_parry_tick_window", 3);
        DEFAULTVALUESDATAPACKENABLED = CONFIG.getOrDefault("default_values_datapack_enabled", true);
        BUMBLEZONEDATAPACKENABLED = CONFIG.getOrDefault("bumblezone_datapack_enabled", true);
        GUARDINGDATAPACKENABLED = CONFIG.getOrDefault("guarding_datapack_enabled", true);
        MODSHIELDTAGENABLED = CONFIG.getOrDefault("mod_shield_tag_required_to_act_as_shield", true);
        CLIENTSHOWWORDINFO = CONFIG.getOrDefault("client_show_word_info", true);
        CLIENTPULSEDISABLEDCOLOR = CONFIG.getOrDefault("client_pulse_disabled_color", true);
        CLIENTSHIELDINDICATOROFFSETY = CONFIG.getOrDefault("client_shield_indicator_offset_y", 0);




        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}