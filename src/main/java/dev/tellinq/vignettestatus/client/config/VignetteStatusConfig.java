package dev.tellinq.vignettestatus.client.config;

import dev.tellinq.vignettestatus.client.api.Vignette;
import net.minecraft.entity.player.PlayerEntity;
import dev.tellinq.vignettestatus.VignetteStatusModConstants;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.*;
import org.polyfrost.oneconfig.api.config.v1.annotations.Number;
import org.polyfrost.polyui.color.ColorUtils;
import org.polyfrost.polyui.color.PolyColor;

/**
 * The main Config entrypoint that extends the Config type and initializes the config options.
 *
 * @see <a href="https://docsv1.polyfrost.org/configuration/available-options">OneConfig configuration documentation</a> for more config Options
 */
public class VignetteStatusConfig extends Config {


    @Slider(
            title = "Maximum Opacity",
            description = "Maximum opacity of all vignette effects (higher = stronger)",
            min = 0.0f,
            max = 100.0f
    )
    public static float maximumOpacity = 30.0f;

    @Number(
            title = "Fade In Speed",
            description = "How quickly the vignette fades in (higher = faster)",
            min = 0.0f
    )
    public static float fadeInSpeed = 0.0035f;

    @Number(
            title = "Fade Out Speed",
            description = "How quickly the vignette fades out (higher = faster)",
            min = 0.0f
    )
    public static float fadeOutSpeed = 0.0035f;

    @Accordion(
            title = "Health Vignette"
    )
    public static class HealthVignette extends Vignette {

        @Include
        public static boolean enabled = true;

        @Color(title = "Color")
        public static PolyColor color = ColorUtils.rgba(255, 0, 0, 255);

        @Slider(
                title = "Start Threshold",
                min = 0.0f,
                max = 100.0f
        )
        public static float minimumStrengthThreshold = 30.0f;

        @Slider(
                title = "Maximum Threshold",
                min = 0.0f,
                max = 100.0f
        )
        public static float maximumStrengthThreshold = 90.0f;

        @Override public boolean isEnabled() { return enabled; }
        @Override public PolyColor getColor() { return color; }
        @Override public float getMinimumStrengthThreshold() { return minimumStrengthThreshold; }
        @Override public float getMaximumStrengthThreshold() { return maximumStrengthThreshold; }

        @Override
        public float getStrength(PlayerEntity player) {
            if (player == null) {
                return 0.0f;
            }

            if (player.getHealth() >= player.getMaxHealth()) {
                return 0.0f;
            }

            return (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth();
        }

    }

    @Accordion(title = "Hunger Vignette")
    public static class HungerVignette extends Vignette {

        @Include
        public static boolean enabled = true;

        @Color(
                title = "Color"
        )
        public static PolyColor color = ColorUtils.rgba(153, 102, 0, 255);

        @Slider(
                title = "Start Threshold",
                min = 0.0f,
                max = 100.0f
        )
        public static float minimumStrengthThreshold = 60.0f;

        @Slider(
                title = "Maximum Threshold",
                min = 0.0f,
                max = 100.0f
        )
        public static float maximumStrengthThreshold = 90.0f;

        @Override public boolean isEnabled() { return enabled; }
        @Override public PolyColor getColor() { return color; }
        @Override public float getMinimumStrengthThreshold() { return minimumStrengthThreshold; }
        @Override public float getMaximumStrengthThreshold() { return maximumStrengthThreshold; }

        @Override
        public float getStrength(PlayerEntity player) {
            if (player == null) {
                return 0.0f;
            }

            if (player.getHungerManager().getFoodLevel() >= 20) {
                return 0.0f;
            }

            return (20.0f - player.getHungerManager().getFoodLevel()) / 20.0f;
        }

    }

    @Accordion(title = "Air Vignette")
    public static class AirVignette extends Vignette {

        @Include
        public static boolean enabled = true;

        @Color(
                title = "Color"
        )
        public static PolyColor color = ColorUtils.rgba(0, 0, 255, 255);

        @Slider(
                title = "Start Threshold",
                min = 0.0f,
                max = 100.0f
        )
        public static float minimumStrengthThreshold = 40.0f;

        @Slider(
                title = "Maximum Threshold",
                min = 0.0f,
                max = 100.0f
        )
        public static float maximumStrengthThreshold = 90.0f;

        @Override public boolean isEnabled() { return enabled; }
        @Override public PolyColor getColor() { return color; }
        @Override public float getMinimumStrengthThreshold() { return minimumStrengthThreshold; }
        @Override public float getMaximumStrengthThreshold() { return maximumStrengthThreshold; }

        @Override
        public float getStrength(PlayerEntity player) {
            if (player == null) {
                return 0.0f;
            }

            if (player.getAir() >= player.getMaxAir()) {
                return 0.0f;
            }

            return (float)(player.getMaxAir() - player.getAir()) / (float)player.getMaxAir();
        }

    }

    public static final VignetteStatusConfig INSTANCE = new VignetteStatusConfig();

    public VignetteStatusConfig() {
        super(VignetteStatusModConstants.ID + ".json", VignetteStatusModConstants.NAME, Category.QOL); // TODO: Change your category here.
    }

}

