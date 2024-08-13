package com.blockgoblin31.ct_integrations.occultism.pentacle;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blockgoblin31.ct_integrations.occultism.actions.ActionRegisterPentacle;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.api.multiblock.StateMatcher;
import com.klikli_dev.modonomicon.multiblock.DenseMultiblock;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;

@ZenRegister(modDeps = {"occultism"})
@Document("mods/ct_integrations/occultism/pentacles/PentacleBuilder")
@ZenCodeType.Name("mods.ct_integrations.occultism.pentacle.PentacleBuilder")
public class PentacleBuilder {
    public static HashMap<ResourceLocation,Multiblock> multiblocks = new HashMap<>();
    @ZenCodeType.Method
    public static void build(String id, ZenStateMatcher[][] blocks) {
        ResourceLocation location = ResourceLocation.parse("crafttweaker:"+id);
        if (blocks.length % 2 == 0) throw new IllegalArgumentException("pentacle must have one block center");
        if (blocks[0].length % 2 ==0) throw new IllegalArgumentException("pentacle must have one block center");
        String[][] strings = new String[blocks.length][blocks[0].length];
        HashMap<Character, StateMatcher> map = new HashMap<>();
        int k = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (k == '0') k++;
                if (i == blocks.length / 2 + 1 && j == blocks[0].length / 2 + 1) {
                    strings[i][j] = "0";
                    map.put('0', blocks[i][j].getInternal());
                } else {
                    strings[i][j] = String.valueOf((char) k);
                    map.put((char) k, blocks[i][j].getInternal());
                    k++;
                }
            }
        }
        DenseMultiblock multiblock = new DenseMultiblock(strings, map);
        multiblock.setId(location);
        CraftTweakerAPI.apply(new ActionRegisterPentacle(location, multiblock));
    }
}
