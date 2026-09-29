package com.bingbingbing.mixin;

import com.bingbingbing.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Replaces a note block's instrument sound when it sits directly on certain ice blocks:
 *  - plain ice      -> the "bing" sound,
 *  - blue ice       -> the "bingbingbing" sound.
 *
 * Packed ice is intentionally left alone so it keeps vanilla's chime instrument. We take over
 * {@code triggerEvent} at HEAD and reproduce the vanilla behaviour (note pitch + note particle)
 * with our sound.
 */
@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

    @Inject(method = "triggerEvent", at = @At("HEAD"), cancellable = true)
    private void bingbingbing$replaceNoteSound(BlockState state, Level level, BlockPos pos, int id, int param,
                                               CallbackInfoReturnable<Boolean> cir) {
        BlockState below = level.getBlockState(pos.below());
        SoundEvent sound;
        if (below.is(Blocks.ICE)) {
            sound = ModSounds.BING.get();
        } else if (below.is(Blocks.BLUE_ICE)) {
            sound = ModSounds.BINGBINGBING.get();
        } else {
            return;
        }
        int note = state.getValue(BlockStateProperties.NOTE);
        float pitch = (float) Math.pow(2.0D, (double) (note - 12) / 12.0D);
        level.addParticle(ParticleTypes.NOTE,
                (double) pos.getX() + 0.5D, (double) pos.getY() + 1.2D, (double) pos.getZ() + 0.5D,
                (double) note / 24.0D, 0.0D, 0.0D);
        level.playSound(null, pos, sound, SoundSource.RECORDS, 3.0F, pitch);
        cir.setReturnValue(true);
    }
}