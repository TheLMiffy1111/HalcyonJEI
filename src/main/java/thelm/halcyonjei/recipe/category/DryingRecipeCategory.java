package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.recipe.DryingRecipe;
import EsetKalenko.Halcyon.screen.DataTabletScreen;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.halcyonjei.HalcyonJEI;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class DryingRecipeCategory extends AbstractRecipeCategory<RecipeHolder<DryingRecipe>> {

	public static final Component TITLE = Component.translatable("emi.category.datanessence.drying");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING2, 133, 196, 123, 60);

	public DryingRecipeCategory() {
		super(HalcyonJEI.DRYING, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<DryingRecipe> recipeHolder, IFocusGroup focuses) {
		DryingRecipe recipe = recipeHolder.value();
		addFluid(builder, RecipeIngredientRole.INPUT, 30, 10, recipe.getInput(), true);
		if(!recipe.getIngredients().isEmpty()) {
			addItem(builder, RecipeIngredientRole.INPUT, 30, 32, recipe.getIngredients().get(0), true);
		}
		else {
			addSlot(builder, RecipeIngredientRole.INPUT, 30, 32, true);
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 74, 22, recipe.getResultItem(registryAccess()), true);
	}

	@Override
	public void draw(RecipeHolder<DryingRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		BACKGROUND.draw(guiGraphics, 0, 0);
		DryingRecipe recipe = recipeHolder.value();
		Font font = font();
		Component timeComponent = getTimeComponent(recipe.getTime());
		guiGraphics.drawString(font, timeComponent, 82 - font.width(timeComponent) / 2, 44, 0xFFFFFF, false);
	}
}
