package dev.tellinq.vignettestatus.client.util;

public class ColorUtil {

    /**
     * Inverts a normalized color component and scales it by a factor.
     *
     * <p>
     * Given a base component value in [0.0–1.0], this method computes
     * (1.0 – {@code baseComponent}) × {@code scaleFactor}. If {@code scaleFactor} is
     * less than or equal to zero, returns {@code originalComponent}
     * unchanged.
     * </p>
     *
     * @param baseComponent     the normalized component to invert (0.0–1.0)
     * @param scaleFactor       the multiplier to apply after inversion
     * @param originalComponent the fallback component value if {@code scaleFactor} ≤ 0
     * @return the inverted-and-scaled component, or {@code originalComponent}
     *         when {@code scaleFactor} ≤ 0
     */
    public static float invertAndScaleComponent(
            float baseComponent,
            float scaleFactor,
            float originalComponent
    ) {
        if (scaleFactor <= 0f) {
            return originalComponent;
        }
        return (1f - baseComponent) * scaleFactor;
    }
}
