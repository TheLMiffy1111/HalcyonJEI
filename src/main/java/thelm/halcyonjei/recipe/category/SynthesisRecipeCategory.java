package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.api.essence.EssenceBarBackgroundTypes;
import EsetKalenko.Halcyon.recipe.SynthesisRecipe;
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

public class SynthesisRecipeCategory extends AbstractUnlockableRecipeCategory<SynthesisRecipe> {

	public static final Component TITLE = Component.translatable("emi.category.datanessence.synthesis");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING, 133, 76, 123, 60);

	public SynthesisRecipeCategory() {
		super(HalcyonJEI.SYNTHESIS, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<SynthesisRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		SynthesisRecipe recipe = recipeHolder.value();
		addItem(builder, RecipeIngredientRole.INPUT, 38, 11, recipe.getIngredients().get(0), visible);
		addItem(builder, RecipeIngredientRole.INPUT, 38, 33, recipe.getIngredients().get(1), visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 82, 22, recipe.getResultItem(registryAccess()), visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<SynthesisRecipe> recipeHolder, IFocusGroup focuses) {
		if(isVisible(recipeHolder)) {
			SynthesisRecipe recipe = recipeHolder.value();
			builder.addWidget(new EssenceBarWidget(
					5, 6,
					EssenceTypeRegistry.ESSENCE.get(),
					recipe.getEssenceCost().getOrDefault(ESSENCE_KEY, 0F), 1000F,
					EssenceBarBackgroundTypes.INDUSTRIAL));
			builder.addWidget(new EssenceBarWidget(
					13, 6,
					EssenceTypeRegistry.LUNAR_ESSENCE.get(),
					recipe.getEssenceCost().getOrDefault(LUNAR_ESSENCE_KEY, 0F), 1000F,
					EssenceBarBackgroundTypes.INDUSTRIAL));
			builder.addWidget(new EssenceBarWidget(
					5, 32,
					EssenceTypeRegistry.NATURAL_ESSENCE.get(),
					recipe.getEssenceCost().getOrDefault(NATURAL_ESSENCE_KEY, 0F), 1000F,
					EssenceBarBackgroundTypes.INDUSTRIAL));
			builder.addWidget(new EssenceBarWidget(
					13, 32,
					EssenceTypeRegistry.EXOTIC_ESSENCE.get(),
					recipe.getEssenceCost().getOrDefault(EXOTIC_ESSENCE_KEY, 0F), 1000F,
					EssenceBarBackgroundTypes.INDUSTRIAL));
		}
	}

	@Override
	public void draw(RecipeHolder<SynthesisRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipeHolder)) {
			BACKGROUND.draw(guiGraphics, 0, 0);
			SynthesisRecipe recipe = recipeHolder.value();
			Font font = font();
			Component timeComponent = getTimeComponent(recipe.getTime());
			guiGraphics.drawString(font, timeComponent, 90 - font.width(timeComponent) / 2, 44, 0xFFFFFF, false);
		}
	}
}
