package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.api.essence.EssenceBarBackgroundTypes;
import EsetKalenko.Halcyon.recipe.FluidMixingRecipe;
import EsetKalenko.Halcyon.registry.EssenceTypeRegistry;
import EsetKalenko.Halcyon.screen.DataTabletScreen;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.halcyonjei.HalcyonJEI;
import thelm.halcyonjei.gui.widget.EssenceBarWidget;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class FluidMixingRecipeCategory extends AbstractUnlockableRecipeCategory<FluidMixingRecipe> {

	public static final Component TITLE = Component.translatable("emi.category.halcyon.fluid_mixing");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING, 10, 76, 123, 60);

	public FluidMixingRecipeCategory() {
		super(HalcyonJEI.FLUID_MIXING, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FluidMixingRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		FluidMixingRecipe recipe = recipeHolder.value();
		addFluid(builder, RecipeIngredientRole.INPUT, 14, 11, recipe.getInput1(), visible);
		addFluid(builder, RecipeIngredientRole.INPUT, 14, 33, recipe.getInput2(), visible);
		if(!recipe.getIngredients().isEmpty()) {
			addItem(builder, RecipeIngredientRole.INPUT, 35, 22, recipe.getIngredients().get(0), visible);
		}
		else {
			addSlot(builder, RecipeIngredientRole.INPUT, 35, 22, visible);
		}
		addFluid(builder, RecipeIngredientRole.OUTPUT, 78, 22, recipe.getOutput(), visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<FluidMixingRecipe> recipeHolder, IFocusGroup focuses) {
		if(isUnlocked(recipeHolder)) {
			FluidMixingRecipe recipe = recipeHolder.value();
			builder.addWidget(new EssenceBarWidget(
					5, 19,
					EssenceTypeRegistry.ESSENCE.get(),
					recipe.getEssenceCost(), 1000F,
					EssenceBarBackgroundTypes.INDUSTRIAL));
		}
	}

	@Override
	public void draw(RecipeHolder<FluidMixingRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isUnlocked(recipeHolder)) {
			BACKGROUND.draw(guiGraphics);
			FluidMixingRecipe recipe = recipeHolder.value();
			Font font = font();
			Component timeComponent = getTimeComponent(recipe.getTime());
			guiGraphics.drawString(font, timeComponent, 86 - font.width(timeComponent) / 2, 44, 0xFFFFFF, false);
		}
	}
}
