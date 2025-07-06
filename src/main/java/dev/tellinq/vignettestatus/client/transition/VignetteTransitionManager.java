package dev.tellinq.vignettestatus.client.transition;

import dev.tellinq.vignettestatus.client.VignetteStatus;
import dev.tellinq.vignettestatus.client.api.Vignette;
import dev.tellinq.vignettestatus.client.util.TimeProvider;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * Manages activation and fading queues for multiple vignettes over time.
 *
 * <p>
 * Selects the highest-priority active vignette, tracks transitions
 * to/from previous vignettes, and builds a render queue pairing each
 * vignette with its current smoothed intensity.
 * </p>
 */
public class VignetteTransitionManager {
    /** Desired intensity for each registered vignette. */
    public static final Map<Vignette, Float> targetIntensityValues = new HashMap<>();

    /** Timestamp (ms) of the last update for each vignette. */
    private static final Map<Vignette, Long> lastUpdateTimes = new HashMap<>();

    /** Intensities of vignettes currently fading out. */
    private static final Map<Vignette, Float> fadingVignettes = new HashMap<>();


    /** The vignette that was active in the previous frame. */
    private static Vignette previousVignette = null;

    /** The last computed strength of the previous vignette. */
    private static float previousStrength = 0.0f;

    /**
     * Chooses the first enabled vignette (by priority) whose computed
     * intensity exceeds a small threshold.
     *
     * @param player the player whose status drives vignette strength
     * @return the selected {@link Vignette} or {@code null} if none qualify
     */
    public static Vignette selectActiveVignette(PlayerEntity player) {
        List<Vignette> priority = Arrays.asList(
                VignetteStatus.healthVignette,
                VignetteStatus.airVignette,
                VignetteStatus.hungerVignette
        );

        for (Vignette v : priority) {
            float raw = v.getStrength(player);
            if (raw < 0.05f) continue;

            float scaled = v.computeIntensity(raw);
            targetIntensityValues.put(v, scaled);

            if (scaled > 0.01f) {
                return v;
            }
        }

        return null;
    }

    /**
     * Records transitions when the active vignette changes, enqueueing the
     * previous one for fade-out if necessary.
     *
     * @param active the newly selected vignette (or {@code null})
     */
    public static void handleVignetteTransitions(Vignette active) {
        long now = TimeProvider.getLastFrameTime();
        if (active != previousVignette) {
            if (previousVignette != null && previousStrength > 0.01f) {
                fadingVignettes.put(previousVignette, previousStrength);
                lastUpdateTimes.put(previousVignette, now);
            }
            previousVignette = active;
            if (active != null) {
                lastUpdateTimes.put(active, now);
            }
        }
    }

    /**
     * Builds a map of each vignette to its current intensity after smoothing.
     *
     * @param active    the currently active vignette (or {@code null})
     * @param deltaTime elapsed seconds since last update
     * @return a map from each vignette to its smoothed intensity value
     */
    public static Map<Vignette, Float> buildRenderQueue(
            Vignette active,
            float deltaTime
    ) {
        Map<Vignette, Float> queue = new HashMap<>();

        // Active vignette smoothing
        if (active != null) {
            float smoothed = VignetteInterpolator.applySmoothing(
                    active,
                    targetIntensityValues.get(active),
                    deltaTime
            );
            if (smoothed > 0.01f) {
                queue.put(active, smoothed);
                previousStrength = smoothed;
            }
        } else {
            previousStrength = 0.0f;
        }

        // Fading vignettes
        for (Iterator<Map.Entry<Vignette, Float>> it =
             fadingVignettes.entrySet().iterator();
             it.hasNext(); ) {
            Map.Entry<Vignette, Float> entry = it.next();
            Vignette v = entry.getKey();
            targetIntensityValues.put(v, 0.0f);

            float smoothed = VignetteInterpolator.applySmoothing(
                    v,
                    0.0f,
                    deltaTime
            );
            boolean shouldQueue = smoothed > 0.01f
                    && (v.equals(previousVignette) || active == null);

            if (shouldQueue) {
                queue.put(v, smoothed);
            } else {
                it.remove();
            }
        }

        return queue;
    }

}
