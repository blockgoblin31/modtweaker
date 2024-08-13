package com.blockgoblin31.ct_integrations.occultism.pentacle;

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

@ZenRegister(modDeps = {"modonomicon"})
@Document("mods/ct_integrations/occultism/pentacle/StateMatcher")
@ZenCodeType.Name("mods.ct_integrations.occultism.pentacle.StateMatcher")
public class ZenStateMatcher {
    private final StateMatcher internal;
    public StateMatcher getInternal() {
        return internal;
    }

    public ZenStateMatcher(StateMatcher internal) {
        this.internal = internal;
    }

    @ZenRegister(modDeps = {"modonomicon"})
    @ZenCodeType.Expansion("crafttweaker.api.block.Block")
    public static class BlockExpansion {
        @ZenCodeType.Caster(implicit = true)
        public static ZenStateMatcher asStateMatcher(Block internal) {
            return new ZenStateMatcher(BlockMatcher.from(internal));
        }
    }

    @ZenRegister(modDeps = {"modonomicom"})
    @ZenCodeType.Expansion("crafttweaker.api.block.BlockState")
    public static class BlockStateExpansion {
        @ZenCodeType.Caster(implicit = true)
        public static ZenStateMatcher asStateMatcher(BlockState internal) {
            return new ZenStateMatcher(BlockStateMatcher.from(internal));
        }
    }

    @ZenRegister(modDeps = {"modonomicon"})
    @ZenCodeType.Expansion("crafttweaker.api.tag.type.KnownTag<crafttweaker.api.block.Block>")
    public static class BlockTagExpansion {
        private static class TagMatcherAccess extends TagMatcher {
            protected TagMatcherAccess(Supplier<TagKey<Block>> tag, Supplier<Map<String, String>> props) {
                super(tag, props);
            }
        }
        @ZenCodeType.Caster(implicit = true)
        public static ZenStateMatcher asStateMatcher(KnownTag<Block> internal) {
            return new ZenStateMatcher(new TagMatcherAccess((() -> TagKey.create(Registries.BLOCK, internal.id())), Map::of));
        }
    }
}
