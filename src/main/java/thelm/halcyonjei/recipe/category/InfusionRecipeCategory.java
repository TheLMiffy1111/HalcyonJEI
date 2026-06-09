package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.api.essence.EssenceBarBackgroundTypes;
import EsetKalenko.Halcyon.recipe.InfusionRecipe;
import EsetKalenko.Halcyon.registry.EssenceTypeRegistry;
import EsetKalenko.Halcyon.screen.DataTabletScreen;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.halcyonjei.HalcyonJEI;
import thelm.halcyonjei.gui.widget.EssenceBarWidget;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class InfusionRecipeCategory extends AbstractUnlockableRecipeCategory<InfusionRecipe> {

	public static final Component TITLE = Component.translatable("emi.category.datanessence.infusion");

	public static final ResourceDrawable BACKGROUND = new ResourceDrawable(DataTabletScreen.TEXTURE_CRAFTING, 10, 136, 123, 60);

	public InfusionRecipeCategory() {
		super(HalcyonJEI.INFUSION, TITLE);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<InfusionRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		InfusionRecipe recipe = recipeHolder.value();
		addItem(builder, RecipeIngredientRole.INPUT, 38, 22, recipe.getIngredients().get(0), visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 82, 22, recipe.getResultItem(registryAccess()), visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<InfusionRecipe> recipeHolder, IFocusGroup focuses) {
		if(isUnlocked(recipeHolder)) {
			InfusionRecipe recipe = recipeHolder.value();
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
	public void draw(RecipeHolder<InfusionRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isUnlocked(recipeHolder)) {
			BACKGROUND.draw(guiGraphics, 0, 0);
		}
	}
}
