package com.bingbingbing.client;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.registry.ModSounds;
import com.bingbingbing.util.ModEnchantHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-side "true replacement" for tool sounds (mining/breaking blocks and hoe/axe/shovel actions).
 *
 * These sounds are dispatched through the sound engine without any tool context, so we hook the sound
 * engine and gate on the LOCAL player currently holding a bingbingbing tool near the sound source.
 * Limitation: this only affects what the holding player hears (not remote observers). Melee/armor
 * sounds are handled server-side by the mixins instead, so those are heard by everyone.
 */
@Mod.EventBusSubscriber(modid = Bingbingbing.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientSoundHandler {

    // NEW: 1s cooldown so the tool sound doesn't spam
    private static long lastToolSound = 0L;

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.getSound();
        if (sound == null) {
            return;
        }
        ResourceLocation loc = sound.getLocation();
        // never rewrite our own sound (avoid loops)
        if (Bingbingbing.MODID.equals(loc.getNamespace())) {
            return;
        }
        if (!isToolSound(loc.getPath())) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || !ModEnchantHelper.hasEnchant(player.getMainHandItem())) {
            return;
        }

        // Only replace sounds happening close to the player (the ones they likely caused).
        double dx = sound.getX() - player.getX();
        double dy = sound.getY() - player.getY();
        double dz = sound.getZ() - player.getZ();
        if (dx * dx + dy * dy + dz * dz > 36.0) {
            return;
        }

        // 0.4s cooldown, timed from when a bingbingbing sound actually starts playing.
        // Only tools (axe/pickaxe/shovel/hoe = DiggerItem) are rate-limited; other sounds replace freely.
        if (player.getMainHandItem().getItem() instanceof net.minecraft.world.item.DiggerItem) {
            long now = System.nanoTime();
            if (now - lastToolSound < 400_000_000L) {
                return;
            }
            lastToolSound = now;
        }

        RandomSource random = player.level().getRandom();
        event.setSound(new SimpleSoundInstance(
                ModSounds.BINGBINGBING.get(), SoundSource.BLOCKS,
                1.0F, 1.0F, random,
                sound.getX(), sound.getY(), sound.getZ()));
    }

    private static boolean isToolSound(String path) {
        // block digging loop + block break
        if (path.startsWith("block.") && (path.endsWith(".hit") || path.endsWith(".break"))) {
            return true;
        }
        // tool right-click actions: hoe till, axe strip/scrape/wax_off, shovel flatten
        return path.startsWith("item.hoe.") || path.startsWith("item.axe.") || path.startsWith("item.shovel.");
    }
}
