package net.salju.curse.network;

import net.salju.curse.events.CurseHelpers;
import net.salju.curse.CurseMod;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Cursed {
	public Cursed(FriendlyByteBuf buffer) {
		//
	}

	public Cursed() {
		//
	}

	public static void buffer(Cursed message, FriendlyByteBuf buffer) {
		//
	}

	public static void handler(Cursed message, Supplier<NetworkEvent.Context> supply) {
		NetworkEvent.Context context = supply.get();
		context.enqueueWork(() -> {
			Player player = context.getSender();
			handleCurse(player);
		});
		context.setPacketHandled(true);
	}

	public static void handleCurse(Player player) {
		CurseHelpers.setCursed(player, true);
		if (!player.level().isClientSide()) {
			player.displayClientMessage(Component.translatable("gui.curse.curse_message"), (true));
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		CurseMod.addNetworkMessage(Cursed.class, Cursed::buffer, Cursed::new, Cursed::handler);
	}
}