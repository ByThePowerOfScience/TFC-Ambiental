package com.lumintorious.tfcambiental.event;

import com.lumintorious.tfcambiental.curios.ClothesCurioRenderer;
import com.lumintorious.tfcambiental.item.TFCAmbientalItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientEventHandler
{
    public static void init(IEventBus bus) {
        bus.addListener(ClientEventHandler::setup);
    }

    private static void setup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(TFCAmbientalItems.STRAW_HAT.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.LEATHER_APRON.get(), ClothesCurioRenderer::new);

        CuriosRendererRegistry.register(TFCAmbientalItems.WOOL_HAT.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.WOOL_SWEATER.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.WOOL_PANTS.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.WOOL_BOOTS.get(), ClothesCurioRenderer::new);

        CuriosRendererRegistry.register(TFCAmbientalItems.SILK_COWL.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.SILK_SHIRT.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.SILK_PANTS.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.SILK_SHOES.get(), ClothesCurioRenderer::new);

        CuriosRendererRegistry.register(TFCAmbientalItems.BURLAP_COWL.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.BURLAP_SHIRT.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.BURLAP_PANTS.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.BURLAP_SHOES.get(), ClothesCurioRenderer::new);

        CuriosRendererRegistry.register(TFCAmbientalItems.LEATHER_HAT.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.LEATHER_TUNIC.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.LEATHER_PANTS.get(), ClothesCurioRenderer::new);
        CuriosRendererRegistry.register(TFCAmbientalItems.LEATHER_BOOTS.get(), ClothesCurioRenderer::new);
    }
}
