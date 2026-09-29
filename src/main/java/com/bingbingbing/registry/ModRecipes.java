package com.bingbingbing.registry;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.recipe.BingbingbingBookRecipe;
import com.bingbingbing.recipe.BingbingbingBookRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bingbingbing.MODID);

    public static final RegistryObject<RecipeSerializer<BingbingbingBookRecipe>> BINGBINGBING_BOOK =
            RECIPES.register("bingbingbing_book", () -> new BingbingbingBookRecipeSerializer());
}
