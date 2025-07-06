package dev.tellinq.vignettestatus.client.transition;

import dev.tellinq.vignettestatus.client.config.VignetteStatusConfig;
import dev.tellinq.vignettestatus.client.api.Vignette;

import java.util.HashMap;
import java.util.Map;

/**
 * Applies time-based smoothing to vignette intensities, preventing
 * abrupt changes by interpolating toward a target value.
 *
 * <p>
 * Maintains a static map of previous intensities per {@link Vignette}
 * instance for use in subsequent smoothing calls.
 * </p>
 */
public class VignetteInterpolator {

    /** Tracks the last computed intensity for each vignette. */
    public static final Map<Vignette, Float> lastIntensityValues = new HashMap<>();

    /**
     * Smooths the transition of a vignette’s intensity toward a
     * {@code targetIntensity} given the elapsed {@code deltaTime}.
     *
     * @param vignette        the vignette whose intensity is being smoothed
     * @param targetIntensity the desired intensity to approach (0.0–1.0)
     * @param deltaTime       seconds elapsed since the last call
     * @return the new, smoothed intensity value
     */
    public static float applySmoothing(
            Vignette vignette,
            float targetIntensity,
            float deltaTime
    ) {
        float last = lastIntensityValues.computeIfAbsent(vignette, v -> 0.0f);
        float speedUp   = VignetteStatusConfig.fadeInSpeed  * deltaTime * 60.0f;
        float speedDown = VignetteStatusConfig.fadeOutSpeed * deltaTime * 60.0f;
        float diff      = targetIntensity - last;
        float speed     = diff > 0.0f
                ? speedUp   * (0.5f + 0.5f * (diff / Math.max(0.1f, targetIntensity)))
                : speedDown * (0.5f + 0.5f * (Math.abs(diff) / Math.max(0.1f, last)));

        speed = Math.max(speed, 0.001f);

        float next = Math.abs(diff) < speed
                ? targetIntensity
                : last + Math.signum(diff) * speed;

        lastIntensityValues.put(vignette, next);

        return next;
    }
}

