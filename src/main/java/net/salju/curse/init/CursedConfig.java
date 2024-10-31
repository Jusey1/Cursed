package net.salju.curse.init;

import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CursedConfig {
	public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static final IConfigSpec CONFIG;

	public static final ModConfigSpec.BooleanValue ALWAYS;
	public static final ModConfigSpec.BooleanValue APPLE;

	public static final ModConfigSpec.IntValue DEATH;
	public static final ModConfigSpec.IntValue KNOCK;
	public static final ModConfigSpec.BooleanValue FIRE;
	public static final ModConfigSpec.BooleanValue ANGRY;
	public static final ModConfigSpec.BooleanValue SLEEP;

	public static final ModConfigSpec.IntValue EXP;
	public static final ModConfigSpec.BooleanValue DROPS;
	
	static {
		BUILDER.push("Main-Config");
		ALWAYS = BUILDER.comment("Should the player always be cursed?").define("Always Cursed", false);
		APPLE = BUILDER.comment("Should the player spawn into a world with a Cursed Apple?").define("Cursed Apple", true);
		BUILDER.pop();
		BUILDER.push("Curses");
		DEATH = BUILDER.comment("How much damage the player takes as a percent.").defineInRange("Damage Curse", 200, 100, Integer.MAX_VALUE);
		KNOCK = BUILDER.comment("How much knockback the player takes as a percent.").defineInRange("Knockback Curse", 200, 100, Integer.MAX_VALUE);
		FIRE = BUILDER.comment("Should fire last forever on the player until doused manually?").define("Fire Curse", true);
		ANGRY = BUILDER.comment("Should neutral mobs be hostile to the player?").define("Neutral Curse", true);
		SLEEP = BUILDER.comment("Should the player be unable go to sleep?").define("Insomnia Curse", true);
		BUILDER.pop();
		BUILDER.push("Benefits");
		EXP = BUILDER.comment("How much experience the player gains from slain mobs as a percent").defineInRange("Bonus Experience", 300, 100, Integer.MAX_VALUE);
		DROPS = BUILDER.comment("Should the player get unique drops from slain mobs?").define("Unique Drops", true);
		BUILDER.pop();
		CONFIG = BUILDER.build();
	}
}