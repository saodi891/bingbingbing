package com.bingbingbing.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class BingbingbingBookRecipeSerializer implements RecipeSerializer<BingbingbingBookRecipe> {

    @Override
    public BingbingbingBookRecipe fromJson(ResourceLocation id, JsonObject json) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        JsonArray array = GsonHelper.getAsJsonArray(json, "ingredients", null);
        if (array != null) {
            for (JsonElement element : array) {
                ingredients.add(Ingredient.fromJson(element));
            }
        }
        return new BingbingbingBookRecipe(id, net.minecraft.world.item.crafting.CraftingBookCategory.MISC, ingredients);
    }

    @Override
    public void toJson(BingbingbingBookRecipe recipe, JsonObject json) {
        JsonArray array = new JsonArray();
        for (Ingredient ingredient : recipe.getIngredients()) {
            array.add(ingredient.toJson());
        }
        json.add("ingredients", array);
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }
}
