package dev.tellinq.vignettestatus.client.util;

/**
 * Provides a simple utility for measuring the time elapsed between successive
 * frames or update cycles. Internally tracks the timestamp of the last
 * calculation to compute a delta time on each invocation.
 */
public class TimeProvider {
    /**
     * The timestamp of the last frame/update in milliseconds since the Unix epoch.
     * Initialized to zero until {@link #computeDeltaTime()} is called for the first time.
     */
    private static long lastFrameTime = 0L;

    /**
     * Computes the elapsed time, in seconds, since the previous call to this method.
     *
     * <p>
     * On the very first invocation, returns a default value of {@code 0.016f}
     * (approximately 60 frames per second). On subsequent calls, calculates the
     * real elapsed interval by subtracting the previously stored timestamp
     * from the current system time and converting to seconds. To avoid
     * excessively large deltas that could destabilize animations or physics
     * updates, the result is clamped to a maximum of {@code 0.1f}.
     * </p>
     *
     * @return the time elapsed since the last call, in seconds,
     * guaranteed to be in the range [0.0, 0.1]
     */
    public static float computeDeltaTime() {
        long now = System.currentTimeMillis();
        float delta = lastFrameTime == 0L
                ? 0.016f
                : (now - lastFrameTime) / 1000.0f;
        lastFrameTime = now;

        return Math.min(delta, 0.1f);
    }

    /**
     * Returns the raw timestamp (in milliseconds since the Unix epoch) of the
     * last recorded frame time. This value is updated each time
     * {@link #computeDeltaTime()} is called.
     *
     * @return the last frame timestamp in milliseconds, or zero if
     * {@code computeDeltaTime()} has not yet been invoked
     */
    public static long getLastFrameTime() {
        return lastFrameTime;
    }
}
