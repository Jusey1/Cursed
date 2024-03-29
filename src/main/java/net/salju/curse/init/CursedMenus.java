package net.salju.curse.init;

import net.salju.curse.gui.CurseGuiMenu;
import net.salju.curse.CurseMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraft.world.inventory.MenuType;

public class CursedMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, CurseMod.MODID);
	public static final RegistryObject<MenuType<CurseGuiMenu>> CURSE_GUI = REGISTRY.register("curse_gui", () -> IForgeMenuType.create(CurseGuiMenu::new));
}