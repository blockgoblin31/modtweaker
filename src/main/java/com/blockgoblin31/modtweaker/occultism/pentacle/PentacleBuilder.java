package com.blockgoblin31.modtweaker.occultism.pentacle;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.klikli_dev.modonomicon.api.multiblock.StateMatcher;
import com.klikli_dev.modonomicon.multiblock.DenseMultiblock;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;

@ZenRegister
@Document("mods/modtweaker/occultism/pentacles/PentacleBuilder")
@ZenCodeType.Name("mods.modtweaker.occultism.pentacle.PentacleBuilder")
public class PentacleBuilder {
    @ZenCodeType.Method
    public static void build(String id, ZenStateMatcher[][] blocks) {
        String[][] strings = new String[blocks.length][blocks[0].length];
        HashMap<Character, StateMatcher> map = new HashMap<>();
        int k = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                strings[i][j] = String.valueOf((char) k);
                map.put((char) k, blocks[i][j].getInternal());
                k++;
            }
        }
        DenseMultiblock multiblock = new DenseMultiblock(strings, map);
        MultiblockDataManagerMixin.get.addMultiblock(ResourceLocation.parse("crafttweaker:"+id), multiblock);
        }
}
