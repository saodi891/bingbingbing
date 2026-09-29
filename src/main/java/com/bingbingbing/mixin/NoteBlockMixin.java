package com.bingbingbing.mixin;

import com.bingbingbing.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
 * When a note block sits directly on top of ice / packed ice / blue ice, its instrument sound
 * is replaced with the bingbingbing sound. We take over {@code triggerEvent} at HEAD and
 * reproduce the vanilla behaviour (note pitch + note particle) with our sound.
 */
@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

    @Inject(method = "triggerEvent", at = @At("HEAD"), cancellable = true)
    private void bingbingbing$replaceNoteSound(BlockState state, Level level, BlockPos pos, int id, int param,
                                               CallbackInfoReturnable<Boolean> cir) {
        if (!isOnIce(level, pos)) {
            return;
        }
        int note = state.getValue(BlockStateProperties.NOTE);
        float pitch = (float) Math.pow(2.0D, (double) (note - 12) / 12.0D);
        level.addParticle(ParticleTypes.NOTE,
                (double) pos.getX() + 0.5D, (double) pos.getY() + 1.2D, (double) pos.getZ() + 0.5D,
                (double) note / 24.0D, 0.0D, 0.0D);
        level.playSound(null, pos, ModSounds.BINGBINGBING.get(), SoundSource.RECORDS, 3.0F, pitch);
        cir.setReturnValue(true);
    }

    private static boolean isOnIce(Level level, BlockPos notePos) {
        BlockState below = level.getBlockState(notePos.below());
        return below.is(Blocks.ICE) || below.is(Blocks.PACKED_ICE) || below.is(Blocks.BLUE_ICE);
    }
}