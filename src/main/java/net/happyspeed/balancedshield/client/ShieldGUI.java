package net.happyspeed.balancedshield.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.happyspeed.balancedshield.access.ClientPlayerClassAccess;
import net.happyspeed.balancedshield.config.ModConfigs;
import net.happyspeed.balancedshield.util.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class ShieldGUI {
    public static void initGUI() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            Window window = mc.getWindow();
            if (mc.player != null && window != null) {
                ItemCooldownManager cooldownManager = mc.player.getItemCooldownManager();

                int Orange = ColorHelper.Argb.getArgb(220, 210, 123, 42);
                int Red = ColorHelper.Argb.getArgb(240, 240, 59, 5);
                int Green = ColorHelper.Argb.getArgb(180, 10, 193, 4);
                int Gray = ColorHelper.Argb.getArgb(200, 137, 135, 135);


                int barX1 = (int) (((window.getScaledWidth() * 0.5f) - (((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient() * 0.5) * window.getScaleFactor()));
                int barY1 = (int) ((window.getScaledHeight() * 0.5f) + (12 + ModConfigs.CLIENTSHIELDINDICATOROFFSETY) * window.getScaleFactor());
                int barX2 = (int) (barX1 + ((ClientPlayerClassAccess) mc.player).balanced_shield$getClientShieldTolerance() * window.getScaleFactor());
                int barY2 = (int) ((window.getScaledHeight() * 0.5f) + (16 + ModConfigs.CLIENTSHIELDINDICATOROFFSETY) * window.getScaleFactor());

                int barBGX1 = (int) (((window.getScaledWidth() * 0.5f) - (((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient() * 0.5) * window.getScaleFactor()));
                int barBGY1 = (int) ((window.getScaledHeight() * 0.5f) + (12 + ModConfigs.CLIENTSHIELDINDICATOROFFSETY) * window.getScaleFactor());
                int barBGX2 = (int) (barBGX1 + ((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient() * window.getScaleFactor());
                int barBGY2 = (int) ((window.getScaledHeight() * 0.5f) + (16 + ModConfigs.CLIENTSHIELDINDICATOROFFSETY) * window.getScaleFactor());


                if (!(cooldownManager.getCooldownProgress(mc.player.getStackInHand(((ClientPlayerClassAccess) mc.player).priorityShieldDetection()).getItem(), tickDelta) == 0)) {

                    if (ModConfigs.CLIENTPULSEDISABLEDCOLOR) {
                        float lerpedAmount = MathHelper.abs(MathHelper.sin(mc.player.age / 10F));
                        int lerpedColor = ColorHelper.Argb.lerp(lerpedAmount, Red, Orange);
                        context.fill(barBGX1, barBGY1, barBGX2, barBGY2, 2, lerpedColor);
                    }
                    else {
                        context.fill(barBGX1, barBGY1, barBGX2, barBGY2, 2, Orange);
                    }

                    if (ModConfigs.CLIENTSHOWWORDINFO) {
                        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.translatable("text.balancedshield.info_disabled"), (int) (window.getScaledWidth() * 0.5f), (int) (barBGY2 + (3 * window.getScaleFactor())), ColorHelper.Argb.getArgb(160, 200, 200, 200));
                    }
                }
                else if (((ClientPlayerClassAccess) mc.player).balanced_shield$getClientShieldTolerance() < ((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient() || mc.player.isBlocking()) {
                    if (mc.player.getStackInHand(mc.player.getActiveHand()).isIn(ModTags.Items.SHIELD) || mc.player.getStackInHand(mc.player.getActiveHand()).getItem() instanceof ShieldItem) {
                        context.fill(barX1, barY1, barX2, barY2, 2, Green);
                        context.fill(barBGX1, barBGY1, barBGX2, barBGY2, 1, Gray);
                        if (ModConfigs.CLIENTSHOWWORDINFO) {
                            context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal("Shield: " + (int) (((double) (((ClientPlayerClassAccess) mc.player).balanced_shield$getClientShieldTolerance()) / (((ClientPlayerClassAccess) mc.player).balanced_shield$getMaxShieldToleranceClient())) * 100) + "%"), (int) (window.getScaledWidth() * 0.5f), (int) (barBGY2 + (3 * window.getScaleFactor())), ColorHelper.Argb.getArgb(160, 200, 200, 200));
                        }
                    }
                }

            }

        });
    }
}
