package net.salju.curse.events;

import net.salju.curse.network.CurseModVariables;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;

public class CurseHelpers {
	public static boolean isCursed(LivingEntity target) {
		return ((target.getCapability(CurseModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CurseModVariables.PlayerVariables())).Cursed);
	}

	public static void setCursed(Player target, boolean curse) {
		target.getCapability(CurseModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
			capability.Cursed = curse;
			capability.syncPlayerVariables(target);
		});
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