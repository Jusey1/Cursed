package net.salju.curse.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

public class CurseManager {
	public static boolean isCursed(Player player) {
		if (player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).isPresent()) {
			return player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).get().getBooleanOr("isCursed", false);
		}
		return false;
	}

	public static void setCursed(Player player, boolean check) {
		if (player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).isPresent()) {
			if (!check) {
				CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).get();
				data.remove("isCursed");
				player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
				player.displayClientMessage(Component.translatable("gui.curse.cure_message"), true);
			} else {
				CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).get();
				data.putBoolean("isCursed", true);
				player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
				player.displayClientMessage(Component.translatable("gui.curse.curse_message"), true);
			}
		}
	}
}