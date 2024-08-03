package net.salju.curse.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class CursedConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CONFIG;

	public static final ForgeConfigSpec.IntValue DEATH;
	public static final ForgeConfigSpec.IntValue KNOCK;
	public static final ForgeConfigSpec.BooleanValue FIRE;
	public static final ForgeConfigSpec.BooleanValue ANGRY;
	public static final ForgeConfigSpec.BooleanValue SLEEP;

	public static final ForgeConfigSpec.IntValue EXP;
	public static final ForgeConfigSpec.BooleanValue DROPS;
	
	static {
		BUILDER.push("Curses");
		DEATH = BUILDER.comment("How much damage the player takes as a percent.").defineInRange("Damage", 200, 100, Integer.MAX_VALUE);
		KNOCK = BUILDER.comment("How much knockback the player takes as a percent.").defineInRange("Knockback", 200, 100, Integer.MAX_VALUE);
		FIRE = BUILDER.comment("Should fire last forever on the player until doused manually?").define("Burns", true);
		ANGRY = BUILDER.comment("Should neutral mobs be hostile to the player?").define("Angry", true);
		SLEEP = BUILDER.comment("Should the player be unable go to sleep?").define("Insomnia", true);
		BUILDER.pop();
		BUILDER.push("Benefits");
		EXP = BUILDER.comment("How much experience the player gains from slain mobs as a percent").defineInRange("Experience", 300, 100, Integer.MAX_VALUE);
		DROPS = BUILDER.comment("Should the player get unique drops from slain mobs?").define("Unique Drops", true);
		BUILDER.pop();
		CONFIG = BUILDER.build();
	}
}