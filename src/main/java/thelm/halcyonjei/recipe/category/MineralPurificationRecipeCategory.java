package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.Halcyon;
import EsetKalenko.Halcyon.recipe.MineralPurificationRecipe;
import EsetKalenko.Halcyon.screen.DataTabletScreen;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.halcyonjei.HalcyonJEI;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class MineralPurificationRecipeCategory extends AbstractRecipeCategory<RecipeHolder<MineralPurificationRecipe>> {

	public static final Component TITLE = Component.translatable("emi.category.halcyon.mineral_purification");
	public static final Component AVERAGE_YIELD = Component.translatable("emi.halcyon.average_yield");
	public static final Component NUGGET_MULT = Component.translatable("emi.halcyon.nugget_mult");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING2, 10, 136, 123, 60);
	public static final ResourceDrawable NUGGET_MULT_ICON = new ResourceDrawable(Halcyon.locate("textures/gui/emi_icons.png"), 128, 16, 8, 8);

	public MineralPurificationRecipeCategory() {
		super(HalcyonJEI.MINERAL_PURIFICATION, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MineralPurificationRecipe> recipeHolder, IFocusGroup focuses) {
		MineralPurificationRecipe recipe = recipeHolder.value();
		addItem(builder, RecipeIngredientRole.INPUT, 30, 22, recipe.getIngredients().get(0), true);
		addItem(builder, RecipeIngredientRole.OUTPUT, 74, 15, recipe.getResultItem(registryAccess()), true);
		addItem(builder, RecipeIngredientRole.OUTPUT, 74, 39, recipe.getNuggetOutput(registryAccess()), true);
	}


	@Override
	public void draw(RecipeHolder<MineralPurificationRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		BACKGROUND.draw(guiGraphics, 0, 0);
		NUGGET_MULT_ICON.draw(guiGraphics, 94, 43);
		MineralPurificationRecipe recipe = recipeHolder.value();
		Font font = font();
		Component yieldComponent = Component.literal(String.valueOf(getYield(recipe)));
		Component timeComponent = getTimeComponent(recipe.getTime());
		guiGraphics.drawString(font, AVERAGE_YIELD, 118 - font.width(AVERAGE_YIELD), 2, 0xFF96B5, false);
		guiGraphics.drawString(font, yieldComponent, 118 - font.width(yieldComponent), 12, 0xFF96B5, false);
		guiGraphics.drawString(font, timeComponent, 38 - font.width(timeComponent) / 2, 50, 0xFFFFFF, false);
	}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<MineralPurificationRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(mouseX >= 94 && mouseX < 102 && mouseY >= 43 && mouseY < 51) {
			tooltip.add(NUGGET_MULT);
		}
	}

	private float getYield(MineralPurificationRecipe recipe) {
		ItemStack primary = recipe.getResultItem(registryAccess());
		ItemStack secondary = recipe.getNuggetOutput(registryAccess());
		if(ItemStack.isSameItem(primary, secondary)) {
			return primary.getCount() + 4.5F * secondary.getCount();
		}
		else {
			return primary.getCount() + 0.45F * secondary.getCount();
		}
	}
}
