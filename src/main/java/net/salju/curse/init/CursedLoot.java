package net.salju.curse.init;

import net.salju.curse.Curse;
import net.salju.curse.events.CurseCondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import java.util.function.Supplier;

public class CursedLoot {
	public static final DeferredRegister<LootItemConditionType> REGISTRY = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Curse.MODID);
	public static final Supplier<LootItemConditionType> CURSED = REGISTRY.register("is_cursed", () -> new LootItemConditionType(CurseCondition.CODEC));
}