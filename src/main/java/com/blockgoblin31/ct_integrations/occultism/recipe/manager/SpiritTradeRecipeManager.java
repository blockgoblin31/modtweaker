package com.blockgoblin31.ct_integrations.occultism.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.klikli_dev.occultism.crafting.recipe.SpiritTradeRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/ct_integrations/occultism/recipes/Spirit Trade")
@ZenCodeType.Name("mods.ct_integrations.occultism.recipe.manager.SpiritTradeRecipeManager")
public class SpiritTradeRecipeManager implements IRecipeManager<SpiritTradeRecipe> {
    @Override
    public RecipeType<SpiritTradeRecipe> getRecipeType() {
        return OccultismRecipes.SPIRIT_TRADE_TYPE.get();
    }

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:"+name);
        SpiritTradeRecipe recipe = new SpiritTradeRecipe(input.asVanillaIngredient(), output.getInternal());
        RecipeHolder<SpiritTradeRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }
}
