package thelm.halcyonjei.recipe.category;

import java.util.List;

import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import thelm.halcyonjei.HalcyonJEI;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public final RecipeType<R> recipeType;
	public final Component title;

	public AbstractRecipeCategory(RecipeType<R> recipeType, Component title) {
		this.recipeType = recipeType;
		this.title = title;
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public int getWidth() {
		return 123;
	}

	@Override
	public int getHeight() {
		return 60;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	public RegistryAccess registryAccess() {
		return Minecraft.getInstance().level.registryAccess();
	}

	public Font font() {
		return Minecraft.getInstance().font;
	}

	public IJeiHelpers jeiHelpers() {
		return HalcyonJEI.jeiHelpers;
	}

	public IPlatformFluidHelper<?> fluidHelper() {
		return jeiHelpers().getPlatformFluidHelper();
	}

	public IGuiHelper guiHelper() {
		return jeiHelpers().getGuiHelper();
	}

	public IIngredientAcceptor<?> addSlot(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, boolean visible) {
		return visible ? builder.addSlot(ingredientRole, x, y) : builder.addInvisibleIngredients(ingredientRole);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, List<ItemStack> itemStacks, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, visible);
		if(itemStacks != null && !itemStacks.isEmpty()) {
			acceptor.addItemStacks(itemStacks);
		}
		return acceptor;
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, Ingredient ingredient, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, visible);
		if(ingredient != null && !ingredient.hasNoItems()) {
			acceptor.addIngredients(ingredient);
		}
		return acceptor;
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, ItemStack itemStack, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, visible);
		if(itemStack != null && !itemStack.isEmpty()) {
			acceptor.addItemStack(itemStack);
		}
		return acceptor;
	}

	public IIngredientAcceptor<?> addFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, FluidStack fluidStack, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, visible);
		if(fluidStack != null && !fluidStack.isEmpty()) {
			acceptor.addFluidStack(fluidStack.getFluid(), fluidStack.getAmount(), fluidStack.getComponentsPatch());
			if(acceptor instanceof IRecipeSlotBuilder slot) {
				slot.setFluidRenderer(Math.max(fluidStack.getAmount(), 1), false, 16, 16);
			}
		}
		return acceptor;
	}

	public IIngredientAcceptor<?> addFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, FluidIngredient ingredient, long amount, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, visible);
		if(ingredient != null && !ingredient.hasNoFluids()) {
			for(FluidStack stack : ingredient.getStacks()) {
				acceptor.addFluidStack(stack.getFluid(), amount, stack.getComponentsPatch());
			}
			if(acceptor instanceof IRecipeSlotBuilder slot) {
				slot.setFluidRenderer(Math.max(amount, 1), false, 16, 16);
			}
		}
		return acceptor;
	}

	public Component getTimeComponent(int time) {
		return Component.translatable("emi.datanessence.time_seconds", time / 20D);
	}
}
