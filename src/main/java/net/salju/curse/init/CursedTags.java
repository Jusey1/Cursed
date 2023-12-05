package net.salju.curse.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class CursedTags {
	public static final TagKey ANGRY = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("curse:no_angry"));
	public static final TagKey<Block> BONUS = BlockTags.create(new ResourceLocation("curse:bonus"));
}