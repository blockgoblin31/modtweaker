package com.blockgoblin31.modtweaker.occultism.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/modtweaker/occultism/recipes/Miner")
@ZenCodeType.Name("mods.modtweaker.occultism.recipe.manager.MinerRecipeManager")
public class MinerRecipeManager implements IRecipeManager<MinerRecipe> {
    @Override
    public RecipeType<MinerRecipe> getRecipeType() {
        return OccultismRecipes.MINER_TYPE.get();
    }

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, int weight, IIngredient input) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:" + name);
        MinerRecipe recipe = new MinerRecipe(input.asVanillaIngredient(), WeightedRecipeResult.of(output.getInternal(), weight));
        RecipeHolder<MinerRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }

    @ZenCodeType.Method
    public void addRecipe(String name, KnownTag<Item> output, int weight, IIngredient input) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:" + name);
        MinerRecipe recipe = new MinerRecipe(input.asVanillaIngredient(), WeightedRecipeResult.of(TagKey.create(Registries.ITEM, output.id()), weight));
        RecipeHolder<MinerRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }
}
