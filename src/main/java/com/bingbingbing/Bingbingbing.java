package com.bingbingbing;

import com.bingbingbing.registry.ModEnchantments;
import com.bingbingbing.registry.ModRecipes;
import com.bingbingbing.registry.ModSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Bingbingbing.MODID)
public class Bingbingbing {
    public static final String MODID = "bingbingbing";

    public Bingbingbing() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEnchantments.ENCHANTMENTS.register(modBus);
        ModSounds.SOUND_EVENTS.register(modBus);
        ModRecipes.RECIPES.register(modBus);
    }
}
