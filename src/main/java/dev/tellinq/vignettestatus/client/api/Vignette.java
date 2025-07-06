package dev.tellinq.vignettestatus.client.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.polyfrost.polyui.color.PolyColor;

/**
 * Defines the core API for all vignette types, providing color and
 * threshold properties and a default intensity computation.
 */
public abstract class Vignette implements IVignetteStrengthProvider {

    /**
     * Indicates whether this vignette effect is currently enabled.
     *
     * @return {@code true} if enabled; {@code false} otherwise
     */
    public abstract boolean isEnabled();

    /**
     * Returns the base color of this vignette.
     *
     * @return a {@link PolyColor} representing the vignette tint
     */
    public abstract PolyColor getColor();

    /**
     * The minimum raw strength (0–100) required before this vignette
     * begins to appear.
     *
     * @return the lower threshold percentage
     */
    public abstract float getMinimumStrengthThreshold();

    /**
     * The raw strength (0–100) at or above which this vignette
     * is fully opaque.
     *
     * @return the upper threshold percentage
     */
    public abstract float getMaximumStrengthThreshold();

    /**
     * Converts a raw strength value into a normalized intensity (0.0–1.0)
     * by applying thresholds and clamping.
     *
     * @param strength the raw strength (0.0–100.0) from
     *                 {@link #getStrength(PlayerEntity)}
     * @return the normalized intensity for rendering
     */
    public float computeIntensity(float strength) {
        if (!isEnabled()) return 0.0f;

        float minT = getMinimumStrengthThreshold() / 100.0f;
        float maxT = getMaximumStrengthThreshold() / 100.0f;
        if (strength <= minT) return 0.0f;
        if (strength >= maxT) return 1.0f;

        float min = MathHelper.clamp(minT, 0.0f, maxT);
        float max = MathHelper.clamp(maxT, min, 1.0f);
        return MathHelper.clamp((strength - min) / (max - min), 0.0f, 1.0f);
    }

}

