package net.salju.curse.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

public class CurseManager {
	public static boolean isCursed(Player player) {
		return player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getBoolean("isCursed");
	}

	public static void setCursed(Player player, boolean check) {
		if (!check) {
			CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
			data.remove("isCursed");
			player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
			player.displayClientMessage(Component.translatable("gui.curse.cure_message"), true);
		} else {
			CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
			data.putBoolean("isCursed", true);
			player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
			player.displayClientMessage(Component.translatable("gui.curse.curse_message"), true);
		}
	}
}