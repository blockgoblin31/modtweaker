package com.blockgoblin31.modtweaker.ae2.recipe.manager;

import appeng.recipes.transform.TransformCircumstance;
import appeng.recipes.transform.TransformRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/modtweaker/ae2/recipes/In World Transformation")
@ZenCodeType.Name("mods.modtweaker.ae2.recipe.manager.TransformRecipeManager")
public class TransformRecipeManager implements IRecipeManager<TransformRecipe> {
    @Override
    public RecipeType<TransformRecipe> getRecipeType() {
        return TransformRecipe.TYPE;
    }

    /**
     *
     * @param name The recipe name
     * @param output The output item
     * @param inputs The input items
     * @docParam name "compacting_coal"
     * @docParam output <item:minecraft:diamond>
     * @docParam inputs [<tag:item:c:storage_blocks/coal>, <tag:item:c:storage_blocks/coal>, <tag:item:c:storage_blocks/coal>]
     */
    @ZenCodeType.Method
    public void addExplosionTransformRecipe(String name, IItemStack output, IIngredient[] inputs) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:"+name);
        NonNullList<Ingredient> ingredients = convert(inputs);
        TransformRecipe recipe = new TransformRecipe(ingredients, output.getImmutableInternal(), TransformCircumstance.EXPLOSION);
        RecipeHolder<TransformRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder, "explosion"));
    }

    /**
     *
     * @param name The recipe name
     * @param output The output item
     * @param inputs The input items
     * @param fluid The fluid to drop the items in
     * @docParam name "wetting_sponges"
     * @docParam output <item:minecraft:wet_sponge>
     * @docParam inputs [<item:minecraft:sponge>]
     * @docParam fluid <tag:fluid:minecraft:water>
     */
    @ZenCodeType.Method
    public void addFluidTransformRecipe(String name, IItemStack output, IIngredient[] inputs, KnownTag<Fluid> fluid) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:"+name);
        NonNullList<Ingredient> ingredients = convert(inputs);
        TransformRecipe recipe = new TransformRecipe(ingredients, output.getImmutableInternal(), TransformCircumstance.fluid(TagKey.create(Registries.FLUID, fluid.id())));
        RecipeHolder<TransformRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder, "fluid"));
    }

    private NonNullList<Ingredient> convert(IIngredient[] inputs) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (IIngredient input : inputs) {
            ingredients.add(input.asVanillaIngredient());
        }
        return ingredients;
    }
}
