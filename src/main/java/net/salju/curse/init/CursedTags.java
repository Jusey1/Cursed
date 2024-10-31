package net.salju.curse.init;

import net.salju.curse.Curse;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class CursedTags {
	public static final TagKey<EntityType<?>> ANGRY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Curse.MODID, "no_angry"));
	public static final TagKey<Item> RARES = ItemTags.create(ResourceLocation.fromNamespaceAndPath(Curse.MODID, "rares"));
}