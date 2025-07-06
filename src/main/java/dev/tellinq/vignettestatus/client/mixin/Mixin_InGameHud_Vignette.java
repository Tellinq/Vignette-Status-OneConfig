package dev.tellinq.vignettestatus.client.mixin;

import dev.tellinq.vignettestatus.client.util.ColorUtil;
import net.minecraft.client.gui.hud.InGameHud;
//#if MC >= 1.21.2
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//#endif
import dev.tellinq.vignettestatus.client.event.VignetteColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//#if MC <= 1.21.1
//$$ import org.spongepowered.asm.mixin.injection.ModifyArgs;
//$$ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
//#endif


@Mixin(InGameHud.class)
public class Mixin_InGameHud_Vignette {

    //#if MC >= 1.21.2
    @WrapOperation
    //#else
    //$$ @ModifyArgs
    //#endif
            (
            method = "renderVignetteOverlay",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.21.2
                    target = "Lnet/minecraft/util/math/ColorHelper;fromFloats(FFFF)I",
                    //#elseif MC <= 1.21.1 && MC >= 1.20.1
                    //$$ target = "Lnet/minecraft/client/gui/DrawContext;setShaderColor(FFFF)V",
                    //#elseif MC <= 1.19.4
                    //$$ target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V",
                    //#endif
                    ordinal = 1
            )
    )
    //#if MC >= 1.21.2
    private int vignettestatus$wrapColorHelperFromFloats(

            float alpha, float red, float green, float blue,
            Operation<Integer> original
    ) {
        return original.call(
                alpha,
                ColorUtil.invertAndScaleComponent(VignetteColor.red,   VignetteColor.alpha,   red),
                ColorUtil.invertAndScaleComponent(VignetteColor.green, VignetteColor.alpha,   green),
                ColorUtil.invertAndScaleComponent(VignetteColor.blue,  VignetteColor.alpha,   blue)
        );
    }
    //#elseif MC <= 1.21.1
    //$$ private void vignettestatus$wrapColorHelperFromFloats(
    //$$         Args args
    //$$ ) {
    //$$     args.set(0, ColorUtil.invertAndScaleComponent(VignetteColor.red,   VignetteColor.alpha,   args.get(0)));
    //$$     args.set(1, ColorUtil.invertAndScaleComponent(VignetteColor.green, VignetteColor.alpha,   args.get(1)));
    //$$     args.set(2, ColorUtil.invertAndScaleComponent(VignetteColor.blue,  VignetteColor.alpha,   args.get(2)));
    //$$ }
    //#endif

}
