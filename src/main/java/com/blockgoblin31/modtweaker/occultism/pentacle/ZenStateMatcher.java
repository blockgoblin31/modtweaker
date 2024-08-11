package com.blockgoblin31.modtweaker.occultism.pentacle;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.klikli_dev.modonomicon.api.multiblock.StateMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.BlockMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.BlockStateMatcher;
import com.klikli_dev.modonomicon.multiblock.matcher.TagMatcher;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;
import java.util.function.Supplier;

@ZenRegister
@Document("mods/modtweaker/occultism/pentacle/StateMatcher")
@ZenCodeType.Name("mods.modtweaker.occultism.pentacle.StateMatcher")
public class ZenStateMatcher {
    private final StateMatcher internal;
    public StateMatcher getInternal() {
        return internal;
    }
    public ZenStateMatcher(StateMatcher internal) {
        this.internal = internal;
    }

    @ZenRegister
    @ZenCodeType.Expansion("crafttweaker.api.block.Block")
    public static class BlockExpansion {
        @ZenCodeType.Caster(implicit = true)
        public static StateMatcher asStateMatcher(Block internal) {
            return BlockMatcher.from(internal);
        }
    }

    @ZenRegister
    @ZenCodeType.Expansion("crafttweaker.api.block.BlockState")
    public static class BlockStateExpansion {
        @ZenCodeType.Caster(implicit = true)
        public static StateMatcher asStateMatcher(BlockState internal) {
            return BlockStateMatcher.from(internal);
        }
    }

    @ZenRegister
    @ZenCodeType.Expansion("crafttweaker.api.tag.type.KnownTag<crafttweaker.api.block.Block>")
    public static class BlockTagExpansion extends TagMatcher {
        protected BlockTagExpansion(Supplier<TagKey<Block>> tag, Supplier<Map<String, String>> props) {
            super(tag, props);
        }

        @ZenCodeType.Caster(implicit = true)
        public static StateMatcher asStateMatcher(KnownTag<Block> internal) {
            return new BlockTagExpansion(() -> TagKey.create(Registries.BLOCK, internal.id()), Map::of);
        }
    }
}
