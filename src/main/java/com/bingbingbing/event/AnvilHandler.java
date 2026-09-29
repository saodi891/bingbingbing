package com.bingbingbing.event;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.registry.ModEnchantments;
import com.bingbingbing.util.ModEnchantHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Applies the bingbingbing enchantment through the anvil with:
 *  - no experience/level cost (setCost(0)),
 *  - no "prior work" penalty (the output keeps the input's repair cost untouched).
 *
 * Also enforces that bingbingbing can never be merged into a composite enchanted book.
 */
@Mod.EventBusSubscriber(modid = Bingbingbing.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) {
            return;
        }
        // Never let bingbingbing combine with another enchanted book into a composite book.
        if (left.is(Items.ENCHANTED_BOOK) && right.is(Items.ENCHANTED_BOOK)
                && (ModEnchantHelper.hasEnchant(left) || ModEnchantHelper.hasEnchant(right))) {
            event.setCanceled(true);
            return;
        }
        // Accept our custom bingbingbing book.
        if (!right.is(com.bingbingbing.registry.ModItems.BINGBINGBING_BOOK.get())) {
            return;
        }
        // Only tools/weapons/armor (matches the enchantment category), and don't re-apply.
        if (!ModEnchantments.CATEGORY.canEnchant(left.getItem())) {
            return;
        }
        if (ModEnchantHelper.hasEnchant(left)) {
            return;
        }

        ItemStack output = left.copy();
        output.enchant(ModEnchantments.BINGBINGBING.get(), 1);

        // Optional rename typed into the anvil.
        String name = event.getName();
        if (name != null && !name.isBlank()) {
            output.setHoverName(net.minecraft.network.chat.Component.literal(name));
        }

        event.setOutput(output);
        event.setMaterialCost(1); // consume one book
        event.setCost(0);         // free: no XP, no level cost
    }
}
