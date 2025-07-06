package dev.tellinq.vignettestatus.client.event;

import dev.deftu.omnicore.client.OmniClient;
import dev.tellinq.vignettestatus.client.config.VignetteStatusConfig;

import java.util.*;

import dev.tellinq.vignettestatus.client.api.Vignette;
import dev.tellinq.vignettestatus.client.transition.VignetteTransitionManager;
import dev.tellinq.vignettestatus.client.util.TimeProvider;
import net.minecraft.client.network.ClientPlayerEntity;
import org.polyfrost.polyui.color.PolyColor;

/**
 * Orchestrates the calculation of RGBA components for the vignette overlay
 * each frame based on player status and configured transition rules.
 *
 * <p>
 * This class queries the current player, delegates to the transition manager
 * to select and process active/fading vignettes, and then updates the
 * static {@code red}, {@code green}, {@code blue}, and {@code alpha}
 * fields for rendering.
 * </p>
 */
public class VignetteColor {

    public static float red;
    public static float green;
    public static float blue;
    public static float alpha;

    /**
     * Drives one iteration of vignette color computation.
     *
     * <p>
     * Retrieves the current {@link ClientPlayerEntity}, lets
     * {@link VignetteTransitionManager} select and update active/fading
     * vignettes, and then writes the final normalized color values into the
     * public RGBA fields for use by a mixin or renderer.
     * </p>
     */
    public void calculateVignetteColors() {
        ClientPlayerEntity player = OmniClient.getInstance().player;
        if (player == null) return;

        Vignette active = VignetteTransitionManager.selectActiveVignette(player);
        VignetteTransitionManager.handleVignetteTransitions(active);
        Map<Vignette, Float> renderQueue =
                VignetteTransitionManager.buildRenderQueue(
                        active,
                        TimeProvider.computeDeltaTime()
                );

        if (renderQueue.isEmpty()) return;

        for (Map.Entry<Vignette, Float> en : renderQueue.entrySet()) {
            PolyColor base = en.getKey().getColor();
            red   = base.red()   / 255.0f;
            green = base.green() / 255.0f;
            blue  = base.blue()  / 255.0f;
            alpha = Math.min(
                    VignetteStatusConfig.maximumOpacity / 100.0f,
                    en.getValue()
            );
        }
    }

}

