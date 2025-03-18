package net.happyspeed.balancedshield.access;

import net.minecraft.util.Hand;

public interface ClientPlayerClassAccess {
    void balanced_shield$setClientShieldTolerance(int clientShieldTolerance);
    int balanced_shield$getClientShieldTolerance();
    int balanced_shield$getMaxShieldToleranceClient();
    Hand priorityShieldDetection();


    }
