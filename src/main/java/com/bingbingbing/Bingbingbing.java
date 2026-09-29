package com.bingbingbing;

import com.bingbingbing.registry.ModBlocks;
import com.bingbingbing.registry.ModEnchantments;
import com.bingbingbing.registry.ModItems;
import com.bingbingbing.registry.ModMenuTypes;
import com.bingbingbing.registry.ModSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Bingbingbing.MODID)
public class Bingbingbing {
    public static final String MODID = "bingbingbing";

    public Bingbingbing() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModEnchantments.ENCHANTMENTS.register(modBus);
        ModSounds.SOUND_EVENTS.register(modBus);
        ModMenuTypes.MENUS.register(modBus);
    }
}
