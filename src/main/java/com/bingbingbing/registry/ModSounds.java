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
}
