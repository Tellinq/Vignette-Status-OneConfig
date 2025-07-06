package dev.tellinq.vignettestatus.client.command;

import dev.tellinq.vignettestatus.VignetteStatusModConstants;
import dev.tellinq.vignettestatus.client.VignetteStatus;
import dev.tellinq.vignettestatus.client.config.VignetteStatusConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command;
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Handler;
import org.polyfrost.oneconfig.utils.v1.dsl.ScreensKt;

/**
 * A command implementing the {@link Command} api of OneConfig.
 * Registered in {@link VignetteStatus}`
 *
 * @see Command
 * @see VignetteStatus
 * @see <a href="https://docsv1.polyfrost.org/commands/annotation-based-commands">OneConfig annotation based commands documentation</a> for more information
 */
@Command(VignetteStatusModConstants.ID)
public class VignetteStatusCommand {

    @Handler
    private void main() {
        ScreensKt.openUI(VignetteStatusConfig.INSTANCE);
    }

}
