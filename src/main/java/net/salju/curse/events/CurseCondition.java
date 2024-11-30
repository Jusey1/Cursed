package net.salju.curse.events;

import net.salju.curse.init.CursedLoot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.util.context.ContextKey;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import java.util.Set;

public record CurseCondition() implements LootItemCondition {
	public static final MapCodec<CurseCondition> CODEC = MapCodec.unit(CurseCondition::new);

	@Override
	public boolean test(LootContext context) {
		Entity target = this.getTarget(context);
		if (target instanceof Player player) {
			return CurseManager.isCursed(player);
		}
		return false;
	}

	@Override
	public Set<ContextKey<?>> getReferencedContextParams() {
		return Set.of(LootContextParams.ATTACKING_ENTITY);
	}

	@Override
	public LootItemConditionType getType() {
		return CursedLoot.CURSED.get();
	}

	@Nullable
	public Entity getTarget(LootContext context) {
		if (context.hasParameter(LootContextParams.ATTACKING_ENTITY)) {
			return context.getParameter(LootContextParams.ATTACKING_ENTITY);
		} else if (context.hasParameter(LootContextParams.THIS_ENTITY)) {
			return context.getParameter(LootContextParams.THIS_ENTITY);
		}
		return null;
	}
}