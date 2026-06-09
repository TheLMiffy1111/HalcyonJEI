package thelm.halcyonjei.recipe.category;

import EsetKalenko.Halcyon.DataNEssence;
import EsetKalenko.Halcyon.moddata.ClientPlayerUnlockedEntries;
import EsetKalenko.Halcyon.recipe.IHasRequiredKnowledge;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public abstract class AbstractUnlockableRecipeCategory<R extends Recipe<?> & IHasRequiredKnowledge> extends AbstractRecipeCategory<RecipeHolder<R>> {

	public static final Component LOCKED_TEXT_1 = Component.translatable("emi.datanessence.recipe_locked_line_1");
	public static final Component LOCKED_TEXT_2 = Component.translatable("emi.datanessence.recipe_locked_line_2");

	public static final ResourceDrawable LOCKED_BACKGROUND = new ResourceDrawable(DataNEssence.locate("textures/gui/emi_recipe_locked.png"), 0, 0, 123, 60);

	public static final ResourceLocation ESSENCE_KEY = DataNEssence.locate("essence");
	public static final ResourceLocation LUNAR_ESSENCE_KEY = DataNEssence.locate("lunar_essence");
	public static final ResourceLocation NATURAL_ESSENCE_KEY = DataNEssence.locate("natural_essence");
	public static final ResourceLocation EXOTIC_ESSENCE_KEY = DataNEssence.locate("exotic_essence");

	public AbstractUnlockableRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	public boolean isUnlocked(RecipeHolder<R> recipeHolder) {
		ResourceLocation unlockEntry = recipeHolder.value().getEntry();
		return unlockEntry == null ||
				ClientPlayerUnlockedEntries.getUnlocked().contains(unlockEntry) ||
				ClientPlayerUnlockedEntries.getIncomplete().getOrDefault(unlockEntry, -1) >= recipeHolder.value().getCompletionStage();
	}

	public boolean isVisible(RecipeHolder<R> recipeHolder) {
		return isUnlocked(recipeHolder);
	}

	@Override
	public void draw(RecipeHolder<R> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		if(!isUnlocked(recipeHolder)) {
			LOCKED_BACKGROUND.draw(guiGraphics, 0, 0);
			Font font = font();
			guiGraphics.drawString(font, LOCKED_TEXT_1, 119 - font.width(LOCKED_TEXT_1), 30, 0xFF96B5, false);
			guiGraphics.drawString(font, LOCKED_TEXT_2, 119 - font.width(LOCKED_TEXT_2), 40, 0xF25EA1, false);
		}
	}
}
