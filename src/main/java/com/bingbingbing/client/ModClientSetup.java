package com.bingbingbing.client;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/** Client-only registration (menu screens). */
@Mod.EventBusSubscriber(modid = Bingbingbing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // IceAnvilScreen is the vanilla anvil screen with our own GUI texture; the menu type reports itself
        // so the client builds an IceAnvilMenu (with the correct free result) rather than a plain AnvilMenu.
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.ICE_ANVIL.get(), IceAnvilScreen::new));
    }
}
