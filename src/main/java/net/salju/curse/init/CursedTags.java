package net.salju.curse.init;

import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class CursedTags {
	public static final TagKey ANGRY = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("curse:no_angry"));
	public static final TagKey<Item> RARES = ItemTags.create(new ResourceLocation("curse:rares"));
}