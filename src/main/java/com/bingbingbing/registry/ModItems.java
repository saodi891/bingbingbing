package com.bingbingbing.registry;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.recipe.BingbingbingBookRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModItems {
    /**
     * The bingbingbing book: a vanilla enchanted_book pre-enchanted with bingbingbing,
     * tagged with CustomModelData so it renders with its own texture.
     */
    public static ItemStack bingbingbingBookStack() {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.enchant(ModEnchantments.BINGBINGBING.get(), 1);
        book.getOrCreateTag().putInt("CustomModelData", BingbingbingBookRecipe.TEXTURE_ID);
        return book;
    }
}
