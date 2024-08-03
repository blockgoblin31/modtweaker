package com.blockgoblin31.modtweaker.occultism.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.klikli_dev.occultism.common.entity.job.CrusherJob;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(modDeps = {"occultism"})
@Document("mods/modtweaker/occultism/recipes/Crushing")
@ZenCodeType.Name("mods.modtweaker.occultism.recipes.managers.CrusherSpiritRecipeManager")
public class CrusherSpiritRecipeManager implements IRecipeManager<CrushingRecipe> {
    @Override
    public RecipeType<CrushingRecipe> getRecipeType() {
        return OccultismRecipes.CRUSHING_TYPE.get();
    }

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input, int crushingTime, @ZenCodeType.OptionalBoolean boolean ignoreMultiplier, @ZenCodeType.OptionalInt(1) int minTier, @ZenCodeType.OptionalInt(5) int maxTier) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:" + name);
        CrushingRecipe recipe = new CrushingRecipe(input.asVanillaIngredient(), RecipeResult.of(output.getInternal()), minTier, maxTier, crushingTime, ignoreMultiplier);
        RecipeHolder<CrushingRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }
}
