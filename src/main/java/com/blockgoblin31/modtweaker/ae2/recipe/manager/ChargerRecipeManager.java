package com.blockgoblin31.modtweaker.ae2.recipe.manager;

import appeng.recipes.handlers.ChargerRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(modDeps = {"ae2"})
@Document("mods/modtweaker/ae2/recipes/Charger")
@ZenCodeType.Name("mods.modtweaker.ae2.recipe.manager.ChargerRecipeManager")
public class ChargerRecipeManager implements IRecipeManager<ChargerRecipe> {

    /**
     *
     * @param name The recipe name
     * @param result The output item
     * @param input The input IIngredient
     * @docParam name "charging_singularity"
     * @docParam result <item:ae2:quantum_entangled_singularity> * 2
     * @docParam input <item:ae2:singularity>
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack result, IIngredient input) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:"+name);
        ChargerRecipe recipe = new ChargerRecipe(input.asVanillaIngredient(), result.getInternal());
        RecipeHolder<ChargerRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }

    @Override
    public RecipeType<ChargerRecipe> getRecipeType() {
        return ChargerRecipe.TYPE;
    }
}
