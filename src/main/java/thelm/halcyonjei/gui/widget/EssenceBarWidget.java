package thelm.halcyonjei.gui.widget;

import EsetKalenko.Halcyon.api.DataNEssenceRegistries;
import EsetKalenko.Halcyon.api.essence.EssenceBarBackgroundType;
import EsetKalenko.Halcyon.api.essence.EssenceType;
import EsetKalenko.Halcyon.api.util.client.ClientEssenceBarUtil;
import EsetKalenko.Halcyon.moddata.ClientPlayerData;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;

public class EssenceBarWidget implements IRecipeWidget {

	public static final int WIDTH = 3;
	public static final int HEIGHT = 22;

	private final ScreenPosition position;
	private final EssenceType type;
	private final float cost;
	private final float max;
	private final EssenceBarBackgroundType backgroundType;

	public EssenceBarWidget(int x, int y, EssenceType type, float cost, float max, EssenceBarBackgroundType backgroundType) {
		this.position = new ScreenPosition(x, y);
		this.type = type;
		this.cost = cost;
		this.max = max;
		this.backgroundType = backgroundType;
	}

	@Override
	public ScreenPosition getPosition() {
		return position;
	}

	@Override
	public void drawWidget(GuiGraphics guiGraphics, double mouseX, double mouseY) {
		ClientEssenceBarUtil.drawEssenceBarTiny(guiGraphics, 0, 0, type, cost, max, backgroundType);
	}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, double mouseX, double mouseY) {
		if(cost > 0 && mouseX >= -1 && mouseX < 4 && mouseY >= -1 && mouseY < 23) {
			String tooltipKey = ClientPlayerData.getUnlockedEssences().getOrDefault(DataNEssenceRegistries.ESSENCE_TYPE_REGISTRY.getKey(type), false) ? type.tooltipKey : "gui.essence_bar.unknown"; 
			tooltip.add(Component.translatable(tooltipKey, cost));
		}
	}
}
