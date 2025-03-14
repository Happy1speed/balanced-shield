package net.happyspeed.balancedshield.util;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.happyspeed.balancedshield.BalancedShieldMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Entity {
        public static final TagKey<EntityType<?>> POWERFULLHITENTITY =
                createTag("power_full_hit_entity");
        public static final TagKey<EntityType<?>> POWERSIXTEENHITENTITY =
                createTag("power_sixteen_hit_entity");
        public static final TagKey<EntityType<?>> POWERTWELVEHITENTITY =
                createTag("power_twelve_hit_entity");
        public static final TagKey<EntityType<?>> POWERSIXHITENTITY =
                createTag("power_six_hit_entity");
        public static final TagKey<EntityType<?>> POWERFOURHITENTITY =
                createTag("power_four_hit_entity");
        public static final TagKey<EntityType<?>> POWERTHREEHITENTITY =
                createTag("power_three_hit_entity");
        public static final TagKey<EntityType<?>> POWERTWOHITENTITY =
                createTag("power_two_hit_entity");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(BalancedShieldMod.MOD_ID, name));
        }
    }
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
        public static final TagKey<Item> TIERSIXCHARGES =
                createTag("tier_six_charges");
        public static final TagKey<Item> TIERSEVENCHARGES =
                createTag("tier_seven_charges");
        public static final TagKey<Item> TIEREIGHTCHARGES =
                createTag("tier_eight_charges");
        public static final TagKey<Item> TIERNINECHARGES =
                createTag("tier_nine_charges");
        public static final TagKey<Item> TIERTENCHARGES =
                createTag("tier_ten_charges");

        public static final TagKey<Item> SHIELDMOVEMENTONE =
                createTag("shield_movement_one");
        public static final TagKey<Item> SHIELDMOVEMENTTWO =
                createTag("shield_movement_two");
        public static final TagKey<Item> SHIELDMOVEMENTTHREE =
                createTag("shield_movement_three");
        public static final TagKey<Item> SHIELDMOVEMENTFOUR =
                createTag("shield_movement_four");
        public static final TagKey<Item> SHIELDMOVEMENTFIVE =
                createTag("shield_movement_five");
        public static final TagKey<Item> SHIELDMOVEMENTSIX =
                createTag("shield_movement_six");
        public static final TagKey<Item> SHIELDMOVEMENTSEVEN =
                createTag("shield_movement_seven");
        public static final TagKey<Item> SHIELDMOVEMENTEIGHT =
                createTag("shield_movement_eight");
        public static final TagKey<Item> SHIELDMOVEMENTNINE =
                createTag("shield_movement_nine");

        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTONE =
                createTag("shield_strafe_movement_one");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTTWO =
                createTag("shield_strafe_movement_two");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTTHREE =
                createTag("shield_strafe_movement_three");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTFOUR =
                createTag("shield_strafe_movement_four");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTFIVE =
                createTag("shield_strafe_movement_five");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTSIX =
                createTag("shield_strafe_movement_six");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTSEVEN =
                createTag("shield_strafe_movement_seven");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTEIGHT =
                createTag("shield_strafe_movement_eight");
        public static final TagKey<Item> SHIELDSTRAFEMOVEMENTNINE =
                createTag("shield_strafe_movement_nine");

        public static final TagKey<Item> SHIELDRECHARGETIMEONE =
                createTag("shield_recharge_time_one");
        public static final TagKey<Item> SHIELDRECHARGETIMETWO =
                createTag("shield_recharge_time_two");
        public static final TagKey<Item> SHIELDRECHARGETIMETHREE =
                createTag("shield_recharge_time_three");
        public static final TagKey<Item> SHIELDRECHARGETIMEFOUR =
                createTag("shield_recharge_time_four");
        public static final TagKey<Item> SHIELDRECHARGETIMEFIVE =
                createTag("shield_recharge_time_five");
        public static final TagKey<Item> SHIELDRECHARGETIMESIX =
                createTag("shield_recharge_time_six");
        public static final TagKey<Item> SHIELDRECHARGETIMESEVEN =
                createTag("shield_recharge_time_seven");
        public static final TagKey<Item> SHIELDRECHARGETIMEEIGHT =
                createTag("shield_recharge_time_eight");
        public static final TagKey<Item> SHIELDRECHARGETIMENINE =
                createTag("shield_recharge_time_nine");
        public static final TagKey<Item> SHIELDRECHARGETIMETEN =
                createTag("shield_recharge_time_ten");

        public static final TagKey<Item> SHIELDSPRINT =
                createTag("shield_sprint");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(BalancedShieldMod.MOD_ID, name));
        }
    }
}
