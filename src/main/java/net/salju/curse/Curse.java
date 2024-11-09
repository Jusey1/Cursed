package net.salju.curse;

import net.salju.curse.init.*;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.bus.api.IEventBus;

@Mod("curse")
public class Curse {
	public static final String MODID = "curse";

	public Curse(ModContainer mod, IEventBus bus) {
		CursedData.REGISTRY.register(bus);
		mod.registerConfig(ModConfig.Type.COMMON, CursedConfig.CONFIG, "cursed-common.toml");
	}
}