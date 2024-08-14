package net.happyspeed.balancedshield.util;

import net.happyspeed.balancedshield.BalancedShieldMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> POWERFULLHIT =
                createTag("power_full_hit");
        public static final TagKey<Item> POWERSIXTEENHIT =
                createTag("power_sixteen_hit");
        public static final TagKey<Item> POWERTWELVEHIT =
                createTag("power_twelve_hit");
        public static final TagKey<Item> POWERSIXHIT =
                createTag("power_six_hit");
        public static final TagKey<Item> POWERFOURHIT =
                createTag("power_four_hit");
        public static final TagKey<Item> POWERTHREEHIT =
                createTag("power_three_hit");
        public static final TagKey<Item> POWERTWOHIT =
                createTag("power_two_hit");

        public static final TagKey<Item> SHIELD =
                createTag("shield");

        public static final TagKey<Item> SHIELDTIERONE =
                createTag("shield_tier_one");
        public static final TagKey<Item> SHIELDTIERTWO =
                createTag("shield_tier_two");
        public static final TagKey<Item> SHIELDTIERTHREE =
                createTag("shield_tier_three");
        public static final TagKey<Item> SHIELDTIERFOUR =
                createTag("shield_tier_four");

        public static final TagKey<Item> TIERONECHARGE =
                createTag("tier_one_charge");
        public static final TagKey<Item> TIERTWOCHARGES =
                createTag("tier_two_charges");
        public static final TagKey<Item> TIERTHREECHARGES =
                createTag("tier_three_charges");
        public static final TagKey<Item> TIERFOURCHARGES =
                createTag("tier_four_charges");
        public static final TagKey<Item> TIERFIVECHARGES =
                createTag("tier_five_charges");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(BalancedShieldMod.MOD_ID, name));
        }
    }
}
