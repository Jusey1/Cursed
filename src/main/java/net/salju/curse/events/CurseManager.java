package net.salju.curse.events;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import java.util.Map;
import java.util.HashMap;

public class CurseManager {
	public static boolean isCursed(LivingEntity target) {
		return (target.getPersistentData().getBoolean("isCursed"));
	}

	public static void setCursed(Player player, boolean check) {
		if (!check) {
			player.getPersistentData().remove("isCursed");
		} else {
			player.getPersistentData().putBoolean("isCursed", true);
		}
	}

	public static Item getItem(EntityType type) {
		return CurseManager.getMap().getOrDefault(type, Items.AIR);
	}

	public static Map<EntityType, Item> getMap() {
		Map<EntityType, Item> typeMap = new HashMap<>();
		typeMap.put(EntityType.COW, Items.LEATHER);
		typeMap.put(EntityType.CHICKEN, Items.FEATHER);
		typeMap.put(EntityType.ZOMBIE, Items.SLIME_BALL);
		typeMap.put(EntityType.DROWNED, Items.CLAY_BALL);
		typeMap.put(EntityType.HUSK, Items.SAND);
		typeMap.put(EntityType.SKELETON, Items.ARROW);
		typeMap.put(EntityType.STRAY, Items.ARROW);
		typeMap.put(EntityType.CREEPER, Items.GUNPOWDER);
		typeMap.put(EntityType.SPIDER, Items.FERMENTED_SPIDER_EYE);
		typeMap.put(EntityType.CAVE_SPIDER, Items.FERMENTED_SPIDER_EYE);
		typeMap.put(EntityType.ENDERMAN, Items.ECHO_SHARD);
		typeMap.put(EntityType.WITCH, Items.PHANTOM_MEMBRANE);
		typeMap.put(EntityType.EVOKER, Items.DIAMOND);
		typeMap.put(EntityType.PILLAGER, Items.EMERALD);
		typeMap.put(EntityType.VINDICATOR, Items.LAPIS_LAZULI);
		typeMap.put(EntityType.MAGMA_CUBE, Items.GLOWSTONE_DUST);
		typeMap.put(EntityType.BLAZE, Items.BLAZE_ROD);
		typeMap.put(EntityType.WITHER_SKELETON, Items.NETHERITE_SCRAP);
		typeMap.put(EntityType.PIGLIN_BRUTE, Items.NETHERITE_SCRAP);
		typeMap.put(EntityType.ZOMBIFIED_PIGLIN, Items.GOLD_INGOT);
		typeMap.put(EntityType.PIGLIN, Items.GOLD_INGOT);
		return typeMap;
	}

	public static Component createComp(String string, Object... values) {
		Component[] obj = new Component[values.length];
		int i = 0;
		for (Object value : values) {
			MutableComponent comp;
			if (value instanceof MutableComponent) {
				comp = (MutableComponent) value;
			} else {
				comp = Component.literal(value.toString());
			}
			obj[i] = comp;
			i++;
		}
		return Component.translatable(string, (Object[]) obj);
	}
}