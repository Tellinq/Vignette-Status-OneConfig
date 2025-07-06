package dev.tellinq.vignettestatus.client;

import dev.tellinq.vignettestatus.client.command.VignetteStatusCommand;
import dev.tellinq.vignettestatus.client.config.VignetteStatusConfig;
import dev.tellinq.vignettestatus.client.event.VignetteColor;
import org.polyfrost.oneconfig.api.commands.v1.CommandManager;
import org.polyfrost.oneconfig.api.event.v1.EventManager;
import org.polyfrost.oneconfig.api.event.v1.events.HudRenderEvent;
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe;

public class VignetteStatus {

    public static final VignetteStatus INSTANCE = new VignetteStatus();

    public static final VignetteStatusConfig.HealthVignette healthVignette = new VignetteStatusConfig.HealthVignette();
    public static final VignetteStatusConfig.HungerVignette hungerVignette = new VignetteStatusConfig.HungerVignette();
    public static final VignetteStatusConfig.AirVignette airVignette = new VignetteStatusConfig.AirVignette();

    public static final VignetteColor VIGNETTE_COLOR = new VignetteColor();

    public void initialize() {
        VignetteStatusConfig.INSTANCE.preload();
        CommandManager.register(new VignetteStatusCommand());
        EventManager.INSTANCE.register(this);
    }

    @Subscribe
    public void onRenderHud(HudRenderEvent event) {
        VIGNETTE_COLOR.calculateVignetteColors();
    }

}
