package com.blockgoblin31.modtweaker.ae2.recipe.manager;

import appeng.recipes.handlers.InscriberProcessType;
import appeng.recipes.handlers.InscriberRecipe;
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

@ZenRegister
@Document("mods/modtweaker/ae2/recipes/Inscriber")
@ZenCodeType.Name("mods.modtweaker.ae2.recipe.manager.InscriberRecipeManager")
public class InscriberRecipeManager implements IRecipeManager<InscriberRecipe> {
    @Override
    public RecipeType<InscriberRecipe> getRecipeType() {
        return InscriberRecipe.TYPE;
    }

    /**
     *
     * @param name The name of the recipe
     * @param output The output item
     * @param topItem The item in the top slot
     * @param middleItem The item in the middle slot
     * @param bottomItem The item in the bottom slot
     * @param consumeSideInputs Whether the top and bottom inputs are consumed
     * @docParam name "gravel_compacting"
     * @docParam output <item:minecraft:cobblestone>
     * @docParam topItem <item:minecraft:gravel>
     * @docParam middleItem <item:minecraft:gravel>
     * @docParam bottomItem <item:minecraft:gravel>
     * @docParam consumeSideInputs
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient topItem, IIngredient middleItem, @ZenCodeType.Optional("<item:minecraft:air>") IIngredient bottomItem, @ZenCodeType.OptionalBoolean(true) boolean consumeSideInputs) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:" + name);
        InscriberProcessType processType = consumeSideInputs ? InscriberProcessType.PRESS : InscriberProcessType.INSCRIBE;
        InscriberRecipe recipe = new InscriberRecipe(middleItem.asVanillaIngredient(), output.getImmutableInternal(), topItem.asVanillaIngredient(), bottomItem.asVanillaIngredient(), processType);
        RecipeHolder<InscriberRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }
}
