package com.bingbingbing.registry;

import com.bingbingbing.Bingbingbing;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Bingbingbing.MODID);

    public static final RegistryObject<SoundEvent> BINGBINGBING = SOUND_EVENTS.register(
            "bingbingbing",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bingbingbing.MODID, "bingbingbing")));

    public static final RegistryObject<SoundEvent> ICE_ANVIL_USE = SOUND_EVENTS.register(
            "ice_anvil_use",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bingbingbing.MODID, "ice_anvil_use")));

    /** Note-block instrument "制冰机 / Ice Maker": note block on plain ice, plays the bing sound. */
    public static final RegistryObject<SoundEvent> BING = SOUND_EVENTS.register(
            "bing",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bingbingbing.MODID, "bing")));
}
