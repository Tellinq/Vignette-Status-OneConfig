package dev.tellinq.vignettestatus;

//#if FABRIC
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
//#elseif FORGE
//#if MC >= 1.16.5
//$$ import net.minecraftforge.eventbus.api.IEventBus;
//$$ import net.minecraftforge.fml.common.Mod;
//$$ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//$$ import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//$$ import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//#else
//$$ import net.minecraftforge.fml.common.Mod;
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#endif
//#elseif NEOFORGE
//$$ import net.neoforged.bus.api.IEventBus;
//$$ import net.neoforged.fml.common.Mod;
//$$ import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
//$$ import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
//$$ import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
//#endif

import dev.tellinq.vignettestatus.client.VignetteStatus;

//#if FORGE-LIKE
//$$ import dev.tellinq.vignettestatus.VignetteStatusModConstants;
//$$
//#if MC >= 1.16.5
//$$ @Mod(VignetteStatusModConstants.ID)
//#else
//$$ @Mod(modid = VignetteStatusModConstants.ID, version = VignetteStatusModConstants.VERSION)
//#endif
//#endif
public class VignetteStatusEntrypoint
    //#if FABRIC
    implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer
    //#endif
{

    //#if FORGE && MC >= 1.16.5
    //$$ public VignetteStatusEntrypoint() {
    //$$     setupForgeEvents(FMLJavaModLoadingContext.get().getModEventBus());
    //$$ }
    //#elseif NEOFORGE
    //$$ public VignetteStatusEntrypoint(IEventBus modEventBus) {
    //$$     setupForgeEvents(modEventBus);
    //$$ }
    //#endif

    //#if FABRIC
    @Override
    //#elseif FORGE && MC <= 1.12.2
    //$$ @Mod.EventHandler
    //#endif
    public void onInitialize(
            //#if FORGE-LIKE
            //#if MC >= 1.16.5
            //$$ FMLCommonSetupEvent event
            //#else
            //$$ FMLInitializationEvent event
            //#endif
            //#endif
    ) {
        // no code here
    }

    //#if FABRIC
    @Override
    //#elseif FORGE && MC <= 1.12.2
    //$$ @Mod.EventHandler
    //#endif
    public void onInitializeClient(
            //#if FORGE-LIKE
            //#if MC >= 1.16.5
            //$$ FMLClientSetupEvent event
            //#else
            //$$ FMLInitializationEvent event
            //#endif
            //#endif
    ) {
        //#if FORGE && MC <= 1.12.2
        //$$ if (!event
        //#if MC <= 1.8.9
        //$$ .side.isClient
        //#else
        //$$ .getSide().isClient()
        //#endif
        //$$ ) {
        //$$     return;
        //$$ }
        //#endif

        VignetteStatus.INSTANCE.initialize();
    }

    //#if FABRIC
    @Override
    //#elseif FORGE && MC <= 1.12.2
    //$$ @Mod.EventHandler
    //#endif
    public void onInitializeServer(
            //#if FORGE-LIKE
            //#if MC >= 1.16.5
            //$$ FMLDedicatedServerSetupEvent event
            //#else
            //$$ FMLInitializationEvent event
            //#endif
            //#endif
    ) {
        //#if FORGE && MC <= 1.12.2
        //$$ if (!event.
        //#if MC <= 1.8.9
        //$$ side.isServer
        //#else
        //$$ getSide().isServer()
        //#endif
        //$$ ) {
        //$$     return;
        //$$ }
        //#endif

        // no code here
    }

    //#if FORGE-LIKE && MC >= 1.16.5
    //$$ private void setupForgeEvents(IEventBus modEventBus) {
    //$$     modEventBus.addListener(this::onInitialize);
    //$$     modEventBus.addListener(this::onInitializeClient);
    //$$     modEventBus.addListener(this::onInitializeServer);
    //$$ }
    //#endif

}
