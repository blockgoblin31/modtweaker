package com.blockgoblin31.ct_integrations.occultism.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionModifyTooltip;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.BracketHandlers;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blockgoblin31.ct_integrations.helper.NonNullListHelper;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
@Document("mods/ct_integrations/occultism/builder/Ritual Recipe Builder")
@ZenCodeType.Name("mods.ct_integrations.occultism.recipe.builder.RitualRecipeBuilder")
public class RitualRecipeBuilder {
    final ResourceLocation pentacleId;
    final ResourceLocation ritualType;
    IItemStack ritualDummy;
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
    RitualRecipe.EntityToSacrifice entityToSacrifice;
    String entityToSacrificeDisplayName = "";
    IIngredient itemToUse = BracketHandlers.getItem("minecraft:air");
    String command;
    ActionModifyTooltip tooltip;

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
        this.entityToSacrifice = new RitualRecipe.EntityToSacrifice(TagKey.create(Registries.ENTITY_TYPE, entityToSacrifice.id()), entityToSacrificeDisplayName);
    }

    @ZenCodeType.Method
    public void setEntityToSacrificeName(String displayName) {
        if (this.entityToSacrifice != null) {
            this.entityToSacrifice = new RitualRecipe.EntityToSacrifice(entityToSacrifice.tag(), displayName);
        }
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
                entityToSummonTag, entityNbt, activationItem.asVanillaIngredient(), ingredients, duration, spiritMaxAge, summonNumber,
                spiritJobType, entityToSacrifice, itemToUse.asVanillaIngredient(), command);
        RecipeHolder<RitualRecipe> holder = new RecipeHolder<>(location, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(manager, holder));
        if (tooltip != null) CraftTweakerAPI.apply(tooltip);
    }

    @ZenCodeType.Method
    public void setDisplayItem(String name, String description) {
        IItemStack item = BracketHandlers.getItem("occultism:ritual_dummy/craft_dimensional_matrix");
        item = item.withJsonComponent(DataComponents.ITEM_NAME, new StringData(name));
        tooltip = new ActionModifyTooltip(item, (stack, tooltip, context, flag) -> {
            if (!stack.has(DataComponents.ITEM_NAME) || !stack.get(DataComponents.ITEM_NAME).getString().equals(name)) return;
            tooltip.replaceAll((component -> component.equals(Component.translatable(stack.getInternal().getDescriptionId() + ".tooltip")) ? Component.literal(description) : component));
        });
        ritualDummy = item;
    }

    @ZenCodeType.Method
    public void setDisplayItem(String name, Component component) {
        IItemStack item = BracketHandlers.getItem("occultism:ritual_dummy/craft_dimensional_matrix");
        item = item.withJsonComponent(DataComponents.ITEM_NAME, new StringData(name));
        tooltip = new ActionModifyTooltip(item, (stack, tooltip, context, flag) -> {
            if (!stack.has(DataComponents.ITEM_NAME) || !stack.get(DataComponents.ITEM_NAME).getString().equals(name)) return;
            tooltip.replaceAll(component1 -> component1.equals(Component.translatable(stack.getInternal().getDescriptionId() + ".tooltip")) ? component : component1);
        });
        ritualDummy = item;
    }

    @ZenRegister(modDeps = {"occultism"})
    @Document("mods/ct_integrations/occultism/recipe/Ritual")
    @ZenCodeType.Name("mods.ct_integrations.occultism.recipe.manager.RitualRecipeManager")
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
