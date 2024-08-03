package net.salju.curse.gui;

import net.salju.curse.network.Cursed;
import net.salju.curse.init.CursedConfig;
import net.salju.curse.events.CurseManager;
import net.salju.curse.CurseMod;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

public class CurseGuiScreen extends AbstractContainerScreen<CurseGuiMenu> {
	private static final ResourceLocation texture = new ResourceLocation("curse:textures/screens/curse_gui.png");
	private static final Style RUNES = Style.EMPTY.withFont(new ResourceLocation("minecraft", "alt"));
	private Button yes;
	private Button no;

	public CurseGuiScreen(CurseGuiMenu menu, Inventory inv, Component text) {
		super(menu, inv, text);
		this.imageWidth = 195;
		this.imageHeight = 165;
	}

	@Override
	public void render(GuiGraphics ms, int x, int y, float ticks) {
		this.renderBackground(ms);
		super.render(ms, x, y, ticks);
		this.renderTooltip(ms, x, y);
	}

	@Override
	protected void renderBg(GuiGraphics ms, float ticks, int x, int y) {
		ms.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(int a, int b, int c) {
		if (a == 256) {
			this.onClose();
			return true;
		}
		return super.keyPressed(a, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics ms, int x, int y) {
		ms.drawString(this.font, Component.translatable("The Cursed Urge of Determination").withStyle(RUNES), 15, 6, 4210752, false);
		ms.drawString(this.font, Component.translatable("Everlasting With Sacred Hatred").withStyle(RUNES), 24, 34, 4210752, false);
		ms.drawString(this.font, Component.translatable("You Can Do Anything No Matter The").withStyle(RUNES), 15, 147, 4210752, false);
		ms.drawString(this.font, Component.translatable("Difficulty With The Cursed Urge").withStyle(RUNES), 24, 156, 4210752, false);
		ms.drawString(this.font, Component.translatable("gui.curse.curse_gui.label_title"), 31, 20, 4210752, false);
		if (CursedConfig.DEATH.get() > 100) {
			int i = Math.min(999, CursedConfig.DEATH.get() - 100);
			ms.drawString(this.font, CurseManager.createComp("gui.curse.curse_gui.label_player_damage", i, "%"), 22, 48, 4210752, false);
		}
		if (CursedConfig.FIRE.get()) {
			ms.drawString(this.font, Component.translatable("gui.curse.curse_gui.label_fire"), 22, 65, 4210752, false);
		}
		if (CursedConfig.ANGRY.get()) {
			ms.drawString(this.font, Component.translatable("gui.curse.curse_gui.label_neutrals"), 22, 82, 4210752, false);
		}
		if (CursedConfig.SLEEP.get()) {
			ms.drawString(this.font, Component.translatable("gui.curse.curse_gui.label_sleep"), 22, 99, 4210752, false);
		}
		if (CursedConfig.EXP.get() > 100) {
			int i = Math.min(999, CursedConfig.EXP.get());
			ms.drawString(this.font, CurseManager.createComp("gui.curse.curse_gui.label_xp", i, "%"), 22, 116, 4210752, false);
		}
		if (CursedConfig.DROPS.get()) {
			ms.drawString(this.font, Component.translatable("gui.curse.curse_gui.label_drops"), 22, 133, 4210752, false);
		}
	}

	@Override
	public void init() {
		super.init();
		yes = Button.builder(CommonComponents.GUI_YES, e -> {
			if (true) {
				CurseMod.PACKET.sendToServer(new Cursed());
				this.onClose();
			}
		}).bounds(this.leftPos + 90, this.topPos + 166, 50, 20).build();
		this.addRenderableWidget(yes);
		no = Button.builder(CommonComponents.GUI_NO, e -> {
			if (true) {
				this.onClose();
			}
		}).bounds(this.leftPos + 140, this.topPos + 166, 50, 20).build();
		this.addRenderableWidget(no);
	}
}