package net.salju.curse.mixins;

import net.salju.curse.init.CursedConfig;
import net.salju.curse.init.CursedData;
import net.salju.curse.item.component.CursedItem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import java.util.function.Consumer;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(method = "appendHoverText", at = @At("TAIL"))
	private void applyTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> list, TooltipFlag flag, CallbackInfo ci) {
		CursedItem data = stack.get(CursedData.CURSED);
		if (data != null) {
			list.accept(Component.translatable("item.curse.tooltip." + data.getCursedStatus()).withStyle(data.getCursedStatus() ? ChatFormatting.DARK_RED : ChatFormatting.GOLD));
			if (data.getCursedStatus()) {
				list.accept(Component.literal(""));
				if (flag.hasShiftDown()) {
					if (CursedConfig.DEATH.get() > 100) {
						list.accept(Component.translatable("item.curse.tooltip.damage", Component.literal(Integer.toString(CursedConfig.DEATH.get() - 100)).withStyle(ChatFormatting.GOLD), Component.literal("%").withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.DARK_PURPLE));
					}
					if (CursedConfig.ANGRY.get()) {
						list.accept(Component.translatable("item.curse.tooltip.neutrals").withStyle(ChatFormatting.DARK_PURPLE));
					}
					if (CursedConfig.FIRE.get()) {
						list.accept(Component.translatable("item.curse.tooltip.fire").withStyle(ChatFormatting.DARK_PURPLE));
					}
					if (CursedConfig.SLEEP.get()) {
						list.accept(Component.translatable("item.curse.tooltip.sleep").withStyle(ChatFormatting.DARK_PURPLE));
					}
					if (CursedConfig.EXP.get() > 100) {
						list.accept(Component.translatable("item.curse.tooltip.xp", Component.literal(Integer.toString(CursedConfig.EXP.get() - 100)).withStyle(ChatFormatting.GOLD), Component.literal("%").withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.DARK_PURPLE));
					}
				} else {
					list.accept(Component.translatable("item.curse.tooltip.shift").withStyle(ChatFormatting.DARK_PURPLE));
				}
			}
		}
	}
}