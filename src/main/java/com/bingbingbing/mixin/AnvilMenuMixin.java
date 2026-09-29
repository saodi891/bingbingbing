package com.bingbingbing.mixin;

import com.bingbingbing.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla blocks taking an anvil result when the level cost is 0. Our bingbingbing enchant
 * is meant to be free, so allow pickup whenever the bingbingbing book is the right-hand input.
 */
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void bingbingbing$freePickup(Player player, boolean hasStack, CallbackInfoReturnable<Boolean> cir) {
        AbstractContainerMenu menu = (AbstractContainerMenu) (Object) this;
        // Slot 1 is the right-hand input in an anvil (0 = left, 1 = right, 2 = result).
        ItemStack right = menu.getSlot(1).getItem();
        if (right.is(ModItems.BINGBINGBING_BOOK.get())) {
            cir.setReturnValue(true);
        }
    }
}
