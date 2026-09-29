package com.bingbingbing.mixin;

import com.bingbingbing.registry.ModSounds;
import com.bingbingbing.util.ModEnchantHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Replaces the melee attack sounds (sweep / crit / knockback / strong / weak / no-damage) with
 * bingbingbing when the attacker's main-hand item carries the enchantment. Runs on the logical
 * server, so the replacement is broadcast to every nearby client.
 */
@Mixin(Player.class)
public abstract class PlayerMixin {

    @Redirect(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void bingbingbing$replaceAttackSound(Level level, Player player, double x, double y, double z,
                                                 SoundEvent original, SoundSource source, float volume, float pitch) {
        Player self = (Player) (Object) this;
        SoundEvent toPlay = ModEnchantHelper.hasEnchant(self.getMainHandItem())
                ? ModSounds.BINGBINGBING.get()
                : original;
        level.playSound(player, x, y, z, toPlay, source, volume, pitch);
    }
}
