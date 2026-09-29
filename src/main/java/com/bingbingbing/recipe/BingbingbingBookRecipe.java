package com.bingbingbing.recipe;

import com.bingbingbing.registry.ModEnchantments;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

/**
 * Crafts a real vanilla enchanted book that already carries the bingbingbing enchantment.
 * A datapack recipe's "result" cannot carry NBT, so a custom recipe is the clean way to
 * hand out an already-enchanted book.
 */
public class BingbingbingBookRecipe extends CustomRecipe {

    /** Drives the model override that gives the book its own texture. */
    public static final int TEXTURE_ID = 1;

    private final NonNullList<Ingredient> ingredients;

    public BingbingbingBookRecipe(ResourceLocation id, CraftingBookCategory category,
                                  NonNullList<Ingredient> ingredients) {
        super(id, category);
        this.ingredients = ingredients;
    }

    /** The book this recipe hands out; also used to seed the creative tab. */
    public static ItemStack createBook() {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.addEnchantment(book, ModEnchantments.BINGBINGBING.get(), 1);
        book.getOrCreateTag().putInt("CustomModelData", TEXTURE_ID);
        return book;
    }

    @Override
    public boolean matches(Inventory inv, Level level) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!ingredients.get(i).test(inv.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(Inventory inv, RegistryAccess access) {
        return createBook();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }
}
