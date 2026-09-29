package com.bingbingbing.menu;

import com.bingbingbing.registry.ModBlocks;
import com.bingbingbing.registry.ModEnchantments;
import com.bingbingbing.registry.ModItems;
import com.bingbingbing.registry.ModMenuTypes;
import com.bingbingbing.registry.ModSounds;
import com.bingbingbing.util.ModEnchantHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The ice anvil's menu. Unlike a vanilla anvil:
 *  - the right slot only accepts the (single-enchant) bingbingbing book,
 *  - applying the enchantment is free (no XP / level cost),
 *  - taking the result does NOT consume the book, and never damages the anvil,
 *  - taking the result plays the custom ice-anvil use sound.
 *
 * It extends {@link AnvilMenu} to reuse the vanilla anvil slot layout and screen, but reports its own
 * {@link MenuType} so the client builds this class (and the correct result) instead of a plain anvil.
 */
public class IceAnvilMenu extends AnvilMenu {

    public IceAnvilMenu(int containerId, Inventory inventory) {
        super(containerId, inventory);
    }

    public IceAnvilMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(containerId, inventory, access);
    }

    @Override
    public MenuType<?> getType() {
        return ModMenuTypes.ICE_ANVIL.get();
    }

    /**
     * Vanilla {@link AnvilMenu#isValidBlock} accepts only {@code BlockTags.ANVIL}; without this override the
     * server's {@code stillValid} check fails on the ice anvil and closes the screen the instant it opens.
     */
    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.ICE_ANVIL.get());
    }

    /** Right slot (index 1) only accepts the pure bingbingbing book. */
    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, stack -> true)
                .withSlot(1, 76, 47, stack -> stack.is(ModItems.BINGBINGBING_BOOK.get()))
                .withResultSlot(2, 134, 47)
                .build();
    }

    @Override
    public void createResult() {
        ItemStack base = this.inputSlots.getItem(0);
        ItemStack book = this.inputSlots.getItem(1);

        boolean valid = !base.isEmpty()
                && book.is(ModItems.BINGBINGBING_BOOK.get())
                && ModEnchantments.CATEGORY.canEnchant(base.getItem())
                && !ModEnchantHelper.hasEnchant(base);

        if (!valid) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            return;
        }

        ItemStack output = base.copy();
        output.enchant(ModEnchantments.BINGBINGBING.get(), 1);
        this.resultSlots.setItem(0, output);
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return !this.resultSlots.getItem(0).isEmpty();
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        // The base item became the result, so clear the left slot; the book (right) is preserved.
        // No XP cost and no anvil degradation.
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        this.access.execute((level, pos) ->
                level.playSound(null, pos, ModSounds.ICE_ANVIL_USE.get(), SoundSource.BLOCKS, 1.0F, 1.0F));
    }
}
