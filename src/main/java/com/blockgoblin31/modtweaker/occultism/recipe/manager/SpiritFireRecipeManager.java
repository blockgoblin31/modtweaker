package com.blockgoblin31.modtweaker.occultism.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(modDeps = {"occultism"})
@Document("mods/modtweaker/occultism/recipes/Spirit Fire")
@ZenCodeType.Name("mods.modtweaker.recipe.manager.SpiritFireRecipeManager")
public class SpiritFireRecipeManager implements IRecipeManager<SpiritFireRecipe> {
    @Override
    public RecipeType<SpiritFireRecipe> getRecipeType() {
        return OccultismRecipes.SPIRIT_FIRE_TYPE.get();
    }

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:" + name);
        SpiritFireRecipe recipe = new SpiritFireRecipe(input.asVanillaIngredient(), output.getImmutableInternal());
        RecipeHolder<SpiritFireRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }
}
