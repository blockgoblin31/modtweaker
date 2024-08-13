package com.blockgoblin31.ct_integrations.occultism.actions;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blockgoblin31.ct_integrations.CTIntegrations;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.data.MultiblockDataManager;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.util.Map;

public class ActionRegisterPentacle implements IRuntimeAction {
    static Map<ResourceLocation, Multiblock> multiblocks;

    final ResourceLocation id;
    final Multiblock multiblock;

    public ActionRegisterPentacle(ResourceLocation id, Multiblock multiblock) {
        if (id == null) throw new NullPointerException();
        this.id = id;
        this.multiblock = multiblock;
    }

    @Override
    public void apply() {
        try {
            Field m = MultiblockDataManager.class.getDeclaredField("multiblocks");
            m.setAccessible(true);
            multiblocks = (Map<ResourceLocation, Multiblock>) m.get(MultiblockDataManager.get());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        multiblocks.put(id, multiblock);
    }

    @Override
    public String describe() {
        return "Registering new pentacle with id '%s'".formatted(id);
    }

    @Override
    public String systemName() {
        return CTIntegrations.MODID;
    }
}
