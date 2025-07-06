package dev.tellinq.vignettestatus.client.api;

import net.minecraft.entity.player.PlayerEntity;

/**
 * Supplies raw strength values for vignette effects based on
 * player state or context.
 */
public interface IVignetteStrengthProvider {

    /**
     * Retrieves the current vignette strength for the given player.
     *
     * @param player the player whose status drives the strength
     * @return a strength value, typically in the range 0.0–100.0
     */
    float getStrength(PlayerEntity player);

}

