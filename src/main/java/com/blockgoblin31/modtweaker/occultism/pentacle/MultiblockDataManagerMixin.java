package com.blockgoblin31.modtweaker.occultism.pentacle;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import com.klikli_dev.modonomicon.data.MultiblockDataManager;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(MultiblockDataManager.class)
public class MultiblockDataManagerMixin {
    public static MultiblockDataManagerMixin get = new MultiblockDataManagerMixin();
    @Shadow
    private Map<ResourceLocation, Multiblock> multiblocks;

    public void addMultiblock(ResourceLocation location, Multiblock multiblock) {
        multiblocks.put(location, multiblock);
    }
}
