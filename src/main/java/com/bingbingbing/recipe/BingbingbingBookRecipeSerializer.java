package com.bingbingbing.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

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
        return new BingbingbingBookRecipe(id, CraftingBookCategory.MISC, ingredients);
    }

    public void toJson(BingbingbingBookRecipe recipe, JsonObject json) {
        json.addProperty("category", recipe.category().getSerializedName());
        JsonArray array = new JsonArray();
        for (Ingredient ingredient : recipe.getIngredients()) {
            array.add(ingredient.toJson());
        }
        json.add("ingredients", array);
    }

    @Override
    public BingbingbingBookRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (int i = 0; i < size; i++) {
            ingredients.add(Ingredient.fromNetwork(buf));
        }
        return new BingbingbingBookRecipe(id, CraftingBookCategory.MISC, ingredients);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, BingbingbingBookRecipe recipe) {
        buf.writeVarInt(recipe.getIngredients().size());
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buf);
        }
    }
}