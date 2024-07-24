package net.happyspeed.balancedshield.util;

import net.happyspeed.balancedshield.BalancedShieldMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> POWERTHIRDHIT =
                createTag("power_four_hit");
        public static final TagKey<Item> POWERHALFHIT =
                createTag("power_half_hit");
        public static final TagKey<Item> POWERFULLHIT =
                createTag("power_full_hit");
        public static final TagKey<Item> POWERQUARTERHIT =
                createTag("power_quarter_hit");
        public static final TagKey<Item> POWERSIXTHHIT =
                createTag("power_sixth_hit");
        public static final TagKey<Item> SHIELD =
                createTag("shield");
        public static final TagKey<Item> SHIELDTIERONE =
                createTag("shield_tier_one");
        public static final TagKey<Item> SHIELDTIERTWO =
                createTag("shield_tier_two");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(BalancedShieldMod.MOD_ID, name));
        }
    }
}
