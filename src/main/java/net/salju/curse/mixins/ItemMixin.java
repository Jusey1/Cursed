package net.salju.curse.mixins;

import net.salju.curse.init.CursedData;
import net.salju.curse.item.component.CursedItem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(method = "appendHoverText", at = @At("TAIL"))
	private void applyTooltip(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag, CallbackInfo ci) {
		CursedItem data = stack.get(CursedData.CURSED);
		if (data != null) {
			list.add(Component.translatable("item.curse.tooltip." + data.getCursedStatus()).withStyle(data.getCursedStatus() ? ChatFormatting.DARK_RED : ChatFormatting.GOLD));
		}
	}
}