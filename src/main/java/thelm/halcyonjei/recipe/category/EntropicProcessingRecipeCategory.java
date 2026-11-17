package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.recipe.EntropicProcessingRecipe;
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

public class EntropicProcessingRecipeCategory extends AbstractRecipeCategory<RecipeHolder<EntropicProcessingRecipe>> {

	public static final Component TITLE = Component.translatable("emi.category.halcyon.entropic_processing");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING, 133, 136, 123, 60);

	public EntropicProcessingRecipeCategory() {
		super(HalcyonJEI.ENTROPIC_PROCESSING, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<EntropicProcessingRecipe> recipeHolder, IFocusGroup focuses) {
		EntropicProcessingRecipe recipe = recipeHolder.value();
		addItem(builder, RecipeIngredientRole.INPUT, 30, 22, recipe.getIngredients().get(0), true);
		addItem(builder, RecipeIngredientRole.OUTPUT, 74, 22, recipe.getResultItem(registryAccess()), true);
	}

	@Override
	public void draw(RecipeHolder<EntropicProcessingRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		BACKGROUND.draw(guiGraphics, 0, 0);
		EntropicProcessingRecipe recipe = recipeHolder.value();
		Font font = font();
		Component timeComponent = getTimeComponent(recipe.getTime());
		guiGraphics.drawString(font, timeComponent, 82 - font.width(timeComponent) / 2, 44, 0xFFFFFF, false);
	}
}
