package net.salju.curse.init;

import net.salju.curse.Curse;
import net.salju.curse.item.component.*;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import java.util.function.UnaryOperator;

public class CursedData {
	public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Curse.MODID);
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<CursedItem>> CURSED = register("cursed_item", (stuffs) -> {return stuffs.persistent(CursedItem.CODEC).networkSynchronized(CursedItem.STREAM_CODEC).cacheEncoding();});

	private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> stuffs) {
		return REGISTRY.register(name, () -> stuffs.apply(DataComponentType.builder()).build());
	}
}