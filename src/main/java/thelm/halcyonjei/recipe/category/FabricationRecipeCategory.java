package thelm.halcyonjei.recipe.category;

import java.util.List;

import EsetKalenko.Halcyon.api.essence.EssenceBarBackgroundTypes;
import EsetKalenko.Halcyon.recipe.IFabricationRecipe;
import EsetKalenko.Halcyon.recipe.ShapedFabricationRecipe;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.halcyonjei.HalcyonJEI;
import thelm.halcyonjei.gui.widget.EssenceBarWidget;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class FabricationRecipeCategory extends AbstractUnlockableRecipeCategory<IFabricationRecipe> {

	public static final Component TITLE = Component.translatable("emi.category.datanessence.fabrication");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING, 10, 196, 123, 60);

	public FabricationRecipeCategory() {
		super(HalcyonJEI.FABRICATION, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<IFabricationRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		IFabricationRecipe recipe = recipeHolder.value();
		int sOff = 0;
		if(recipe instanceof ShapedFabricationRecipe shaped) {
			if(shaped.getWidth() == 1) sOff -= 1;
			if(shaped.getHeight() == 1) sOff -= 3;
		}
		List<Ingredient> ingredients = recipe.getIngredients();
		for(int i = 0; i < 9; ++i) {
			int s = i + sOff;
			if(s >= 0 && s < ingredients.size()) {
				addItem(builder, RecipeIngredientRole.INPUT, i % 3 * 17 + 21, i / 3 * 17 + 5, ingredients.get(s), visible);
			}
			else {
				addSlot(builder, RecipeIngredientRole.INPUT, i % 3 * 17 + 21, i / 3 * 17 + 5, visible);
			}
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 98, 22, recipe.getResultItem(registryAccess()), visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<IFabricationRecipe> recipeHolder, IFocusGroup focuses) {
		if(isUnlocked(recipeHolder)) {
			IFabricationRecipe recipe = recipeHolder.value();
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
	public void draw(RecipeHolder<IFabricationRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isUnlocked(recipeHolder)) {
			BACKGROUND.draw(guiGraphics, 0, 0);
			IFabricationRecipe recipe = recipeHolder.value();
			Font font = font();
			Component timeComponent = getTimeComponent(recipe.getTime());
			guiGraphics.drawString(font, timeComponent, 97 - font.width(timeComponent) / 2, 44, 0xFFFFFF, false);
		}
	}
}
