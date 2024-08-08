package com.blockgoblin31.modtweaker.occultism.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.BracketHandlers;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blockgoblin31.modtweaker.helper.NonNullListHelper;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister(modDeps = {"occultism"})
@Document("mods/modtweaker/occultism/builder/Ritual Recipe Builder")
@ZenCodeType.Name("mods.modtweaker.occultism.recipe.builder.RitualRecipeBuilder")
public class RitualRecipeBuilder {
    final ResourceLocation pentacleId;
    final ResourceLocation ritualType;
    final IItemStack ritualDummy;
    final IItemStack resultItem;
    final IIngredient activationItem;
    final NonNullList<Ingredient> ingredients;
    final RitualRecipeManager manager;
    EntityType<Entity> entityToSummon;
    TagKey<EntityType<?>> entityToSummonTag;
    CompoundTag entityNbt;
    int duration = 30;
    int spiritMaxAge = -1;
    int summonNumber = 1;
    ResourceLocation spiritJobType;
    TagKey<EntityType<?>> entityToSacrifice;
    String entityToSacrificeDisplayName;
    IIngredient itemToUse = BracketHandlers.getItem("minecraft:air");
    String command;

    public RitualRecipeBuilder(RitualRecipeManager manager, ResourceLocation pentacleId, ResourceLocation ritualType, IItemStack displayItem, IItemStack resultItem, IIngredient activationItem, NonNullList<Ingredient> ingredients) {
        this.pentacleId = pentacleId;
        this.ritualType = ritualType;
        this.ritualDummy = displayItem;
        this.resultItem = resultItem;
        this.activationItem = activationItem;
        this.ingredients = ingredients;
        this.manager = manager;
    }

    @ZenCodeType.Method
    public void setSummonEntity(EntityType<Entity> entityToSummon, @ZenCodeType.OptionalInt(1) int amountToSummon, @ZenCodeType.Optional CompoundTag nbt) {
        this.entityToSummon = entityToSummon;
        entityToSummonTag = null;
        summonNumber = amountToSummon;
        entityNbt = nbt;
    }

    @ZenCodeType.Method
    public void setSummonEntity(KnownTag<EntityType<?>> entityToSummon, @ZenCodeType.OptionalInt(1) int amountToSummon, @ZenCodeType.Optional CompoundTag nbt) {
        this.entityToSummon = null;
        entityToSummonTag = TagKey.create(Registries.ENTITY_TYPE, entityToSummon.id());
        summonNumber = amountToSummon;
        entityNbt = nbt;
    }

    @ZenCodeType.Method
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @ZenCodeType.Method
    public void setSpiritJobType(String type) {
        spiritJobType = ResourceLocation.parse(type);
    }

    @ZenCodeType.Method
    public void setEntityToSacrifice(KnownTag<EntityType<Entity>> entityToSacrifice) {
        this.entityToSacrifice = TagKey.create(Registries.ENTITY_TYPE, entityToSacrifice.id());
    }

    @ZenCodeType.Method
    public void setEntityToSacrificeName(String displayName) {
        this.entityToSacrificeDisplayName = displayName;
    }

    @ZenCodeType.Method
    public void setItemToUse(IIngredient item) {
        this.itemToUse = item;
    }

    @ZenCodeType.Method
    public void setCommand(String command) {
        this.command = command;
    }

    @ZenCodeType.Method
    public void build(String name) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:"+name);
        RitualRecipe recipe = new RitualRecipe(pentacleId, ritualType, ritualDummy.getInternal(), resultItem.getInternal(), entityToSummon,
                entityToSummonTag, entityNbt, activationItem.asVanillaIngredient(), ingredients, duration, spiritMaxAge, summonNumber, spiritJobType,
                new RitualRecipe.EntityToSacrifice(entityToSacrifice, entityToSacrificeDisplayName), itemToUse.asVanillaIngredient(), command);
        RecipeHolder<RitualRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(manager, holder));
    }

    @ZenRegister(modDeps = {"occultism"})
    @Document("mods/modtweaker/occultism/recipe/Ritual")
    @ZenCodeType.Name("mods.modtweaker.occultism.recipe.manager.RitualRecipeManager")
    public static class RitualRecipeManager implements IRecipeManager<RitualRecipe> {
        @Override
        public RecipeType<RitualRecipe> getRecipeType() {
            return OccultismRecipes.RITUAL_TYPE.get();
        }

        @ZenCodeType.Method
        public RitualRecipeBuilder builder(String pentacleId, String ritualType, IItemStack ritualDummy, IItemStack resultItem, IIngredient activationItem, IIngredient[] inputs) {
            NonNullList<Ingredient> ingredients = NonNullListHelper.convert(Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new));
            return new RitualRecipeBuilder(this, ResourceLocation.parse(pentacleId), ResourceLocation.parse(ritualType), ritualDummy, resultItem, activationItem, ingredients);
        }
    }
}
