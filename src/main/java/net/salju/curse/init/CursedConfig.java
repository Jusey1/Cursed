package net.salju.curse.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class CursedConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CONFIG;

	public static final ForgeConfigSpec.IntValue WEAK;
	public static final ForgeConfigSpec.IntValue DEATH;
	public static final ForgeConfigSpec.IntValue KNOCK;
	public static final ForgeConfigSpec.BooleanValue FIRE;
	public static final ForgeConfigSpec.BooleanValue ANGRY;
	public static final ForgeConfigSpec.BooleanValue SLEEP;
	public static final ForgeConfigSpec.BooleanValue END;

	public static final ForgeConfigSpec.IntValue EXP;
	public static final ForgeConfigSpec.BooleanValue DROPS;
	public static final ForgeConfigSpec.BooleanValue ORE;
	public static final ForgeConfigSpec.BooleanValue LOOT;
	
	static {
		BUILDER.push("Curses");
		WEAK = BUILDER.comment("How much damage the player does as a percent.").defineInRange("DPS", 50, 0, 100);
		DEATH = BUILDER.comment("How much damage the player takes as a percent.").defineInRange("Damage", 200, 100, Integer.MAX_VALUE);
		KNOCK = BUILDER.comment("How much knockback the player takes as a percent.").defineInRange("Knockback", 200, 100, Integer.MAX_VALUE);
		FIRE = BUILDER.comment("Should fire last forever on the player until doused manually?").define("Burns", true);
		ANGRY = BUILDER.comment("Should neutral mobs be hostile to the player?").define("Angry", true);
		END = BUILDER.comment("Should neutral mobs be hostile to the player in the end as well?").define("AngryEnd", true);
		SLEEP = BUILDER.comment("Should the player be unable go to sleep?").define("Insomnia", true);
		BUILDER.pop();
		BUILDER.push("Benefits");
		EXP = BUILDER.comment("How much experience the player gains from slain mobs as a percent").defineInRange("Experience", 300, 100, Integer.MAX_VALUE);
		DROPS = BUILDER.comment("Should the player get unique drops from slain mobs?").define("Unique Drops", true);
		ORE = BUILDER.comment("Should the player have an unique fortune level for specific blocks?").define("Unique Fortune", true);
		LOOT = BUILDER.comment("Should the player have an unique looting level?").define("Unique Looting", true);
		BUILDER.pop();
		CONFIG = BUILDER.build();
	}
}