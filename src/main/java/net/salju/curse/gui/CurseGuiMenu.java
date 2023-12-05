package net.salju.curse.gui;

import net.salju.curse.init.CursedMenus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import javax.annotation.Nullable;

public class CurseGuiMenu extends AbstractContainerMenu {
	public CurseGuiMenu(int id, Inventory inv, @Nullable FriendlyByteBuf extraData) {
		super(CursedMenus.CURSE_GUI.get(), id);
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int i) {
		return ItemStack.EMPTY;
	}
}