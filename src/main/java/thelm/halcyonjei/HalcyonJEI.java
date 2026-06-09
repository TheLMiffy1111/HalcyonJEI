package thelm.halcyonjei;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import EsetKalenko.Halcyon.DataNEssence;
import EsetKalenko.Halcyon.recipe.DryingRecipe;
import EsetKalenko.Halcyon.recipe.EntropicProcessingRecipe;
import EsetKalenko.Halcyon.recipe.FluidMixingRecipe;
import EsetKalenko.Halcyon.recipe.IFabricationRecipe;
import EsetKalenko.Halcyon.recipe.InfusionRecipe;
import EsetKalenko.Halcyon.recipe.MeltingRecipe;
import EsetKalenko.Halcyon.recipe.MetalShaperRecipe;
import EsetKalenko.Halcyon.recipe.MineralPurificationRecipe;
import EsetKalenko.Halcyon.recipe.SynthesisRecipe;
import EsetKalenko.Halcyon.registry.BlockRegistry;
import EsetKalenko.Halcyon.registry.MenuRegistry;
import EsetKalenko.Halcyon.registry.RecipeRegistry;
import EsetKalenko.Halcyon.screen.AutoFabricatorMenu;
import EsetKalenko.Halcyon.screen.AutoFabricatorScreen;
import EsetKalenko.Halcyon.screen.DryingTableScreen;
import EsetKalenko.Halcyon.screen.EntropicProcessorMenu;
import EsetKalenko.Halcyon.screen.EntropicProcessorScreen;
import EsetKalenko.Halcyon.screen.FabricatorMenu;
import EsetKalenko.Halcyon.screen.FabricatorScreen;
import EsetKalenko.Halcyon.screen.FluidMixerScreen;
import EsetKalenko.Halcyon.screen.InfuserMenu;
import EsetKalenko.Halcyon.screen.InfuserScreen;
import EsetKalenko.Halcyon.screen.LunariumMenu;
import EsetKalenko.Halcyon.screen.LunariumScreen;
import EsetKalenko.Halcyon.screen.MelterMenu;
import EsetKalenko.Halcyon.screen.MelterScreen;
import EsetKalenko.Halcyon.screen.MetalShaperMenu;
import EsetKalenko.Halcyon.screen.MetalShaperScreen;
import EsetKalenko.Halcyon.screen.MineralPurificationChamberMenu;
import EsetKalenko.Halcyon.screen.MineralPurificationChamberScreen;
import EsetKalenko.Halcyon.screen.SynthesisChamberMenu;
import EsetKalenko.Halcyon.screen.SynthesisChamberScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;
import thelm.halcyonjei.recipe.category.DryingRecipeCategory;
import thelm.halcyonjei.recipe.category.EntropicProcessingRecipeCategory;
import thelm.halcyonjei.recipe.category.FabricationRecipeCategory;
import thelm.halcyonjei.recipe.category.FluidMixingRecipeCategory;
import thelm.halcyonjei.recipe.category.InfusionRecipeCategory;
import thelm.halcyonjei.recipe.category.MeltingRecipeCategory;
import thelm.halcyonjei.recipe.category.MetalShapingRecipeCategory;
import thelm.halcyonjei.recipe.category.MineralPurificationRecipeCategory;
import thelm.halcyonjei.recipe.category.SynthesisRecipeCategory;

@JeiPlugin
public class HalcyonJEI implements IModPlugin {

	public static final ResourceLocation UID = ResourceLocation.parse("halcyonjei:halcyon");
	public static final Logger LOGGER = LogManager.getLogger();

	public static IJeiHelpers jeiHelpers;
	public static IJeiRuntime jeiRuntime;

	//public static final List<RecipeType<?>> RECIPE_TYPES = new ArrayList<>();
	public static final RecipeType<RecipeHolder<IFabricationRecipe>> FABRICATION = RecipeType.createRecipeHolderType(DataNEssence.locate("fabrication"));
	public static final RecipeType<RecipeHolder<InfusionRecipe>> INFUSION = RecipeType.createRecipeHolderType(DataNEssence.locate("infusion"));
	public static final RecipeType<RecipeHolder<EntropicProcessingRecipe>> ENTROPIC_PROCESSING = RecipeType.createRecipeHolderType(DataNEssence.locate("entropic_processing"));
	public static final RecipeType<RecipeHolder<FluidMixingRecipe>> FLUID_MIXING = RecipeType.createRecipeHolderType(DataNEssence.locate("fluid_mixing"));
	public static final RecipeType<RecipeHolder<SynthesisRecipe>> SYNTHESIS = RecipeType.createRecipeHolderType(DataNEssence.locate("synthesis"));
	public static final RecipeType<RecipeHolder<MetalShaperRecipe>> METAL_SHAPING = RecipeType.createRecipeHolderType(DataNEssence.locate("metal_shaping"));
	public static final RecipeType<RecipeHolder<MeltingRecipe>> MELTING = RecipeType.createRecipeHolderType(DataNEssence.locate("melting"));
	public static final RecipeType<RecipeHolder<DryingRecipe>> DRYING = RecipeType.createRecipeHolderType(DataNEssence.locate("drying"));
	public static final RecipeType<RecipeHolder<MineralPurificationRecipe>> MINERAL_PURIFICATION = RecipeType.createRecipeHolderType(DataNEssence.locate("mineral_purification"));

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelpers = registration.getJeiHelpers();

		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCategories(new FabricationRecipeCategory());
		registration.addRecipeCategories(new InfusionRecipeCategory());
		registration.addRecipeCategories(new EntropicProcessingRecipeCategory());
		registration.addRecipeCategories(new FluidMixingRecipeCategory());
		registration.addRecipeCategories(new SynthesisRecipeCategory());
		registration.addRecipeCategories(new MetalShapingRecipeCategory());
		registration.addRecipeCategories(new MeltingRecipeCategory());
		registration.addRecipeCategories(new DryingRecipeCategory());
		registration.addRecipeCategories(new MineralPurificationRecipeCategory());
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(FABRICATION, recipeManager.getAllRecipesFor(RecipeRegistry.FABRICATIONCRAFTING.get()));
		registration.addRecipes(INFUSION, recipeManager.getAllRecipesFor(RecipeRegistry.INFUSION_TYPE.get()));
		registration.addRecipes(ENTROPIC_PROCESSING, recipeManager.getAllRecipesFor(RecipeRegistry.ENTROPIC_PROCESSING_TYPE.get()));
		registration.addRecipes(FLUID_MIXING, recipeManager.getAllRecipesFor(RecipeRegistry.FLUID_MIXING_TYPE.get()));
		registration.addRecipes(SYNTHESIS, recipeManager.getAllRecipesFor(RecipeRegistry.SYNTHESIS_TYPE.get()));
		registration.addRecipes(METAL_SHAPING, recipeManager.getAllRecipesFor(RecipeRegistry.METAL_SHAPING_TYPE.get()));
		registration.addRecipes(MELTING, recipeManager.getAllRecipesFor(RecipeRegistry.MELTING_TYPE.get()));
		registration.addRecipes(DRYING, recipeManager.getAllRecipesFor(RecipeRegistry.DRYING_TYPE.get()));
		registration.addRecipes(MINERAL_PURIFICATION, recipeManager.getAllRecipesFor(RecipeRegistry.MINERAL_PURIFICATION_TYPE.get()));
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeTransferHandler(FabricatorMenu.class, MenuRegistry.FABRICATOR_MENU.get(), FABRICATION, 36, 9, 0, 36);
		registration.addRecipeTransferHandler(FabricatorMenu.class, MenuRegistry.FABRICATOR_MENU.get(), RecipeTypes.CRAFTING, 36, 9, 0, 36);
		registration.addRecipeTransferHandler(LunariumMenu.class, MenuRegistry.LUNARIUM_MENU.get(), FABRICATION, 36, 9, 0, 36);
		registration.addRecipeTransferHandler(LunariumMenu.class, MenuRegistry.LUNARIUM_MENU.get(), RecipeTypes.CRAFTING, 36, 9, 0, 36);
		registration.addRecipeTransferHandler(AutoFabricatorMenu.class, MenuRegistry.AUTO_FABRICATOR_MENU.get(), FABRICATION, 36, 9, 0, 36);
		registration.addRecipeTransferHandler(AutoFabricatorMenu.class, MenuRegistry.AUTO_FABRICATOR_MENU.get(), RecipeTypes.CRAFTING, 36, 9, 0, 36);
		registration.addRecipeTransferHandler(InfuserMenu.class, MenuRegistry.INFUSER_MENU.get(), INFUSION, 37, 1, 0, 36);
		registration.addRecipeTransferHandler(EntropicProcessorMenu.class, MenuRegistry.ENTROPIC_PROCESSOR_MENU.get(), ENTROPIC_PROCESSING, 36, 1, 0, 36);
		registration.addRecipeTransferHandler(SynthesisChamberMenu.class, MenuRegistry.SYNTHESIS_CHAMBER_MENU.get(), SYNTHESIS, 36, 2, 0, 36);
		registration.addRecipeTransferHandler(MetalShaperMenu.class, MenuRegistry.METAL_SHAPER_MENU.get(), METAL_SHAPING, 37, 1, 0, 36);
		registration.addRecipeTransferHandler(MelterMenu.class, MenuRegistry.MELTER_MENU.get(), MELTING, 36, 1, 0, 36);
		registration.addRecipeTransferHandler(MineralPurificationChamberMenu.class, MenuRegistry.MINERAL_PURIFICATION_CHAMBER_MENU.get(), MINERAL_PURIFICATION, 36, 1, 0, 36);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCatalysts(FABRICATION, BlockRegistry.FABRICATOR.get(), BlockRegistry.LUNARIUM.get(), BlockRegistry.AUTO_FABRICATOR.get());
		registration.addRecipeCatalysts(INFUSION, BlockRegistry.INFUSER.get());
		registration.addRecipeCatalysts(ENTROPIC_PROCESSING, BlockRegistry.ENTROPIC_PROCESSOR.get());
		registration.addRecipeCatalysts(FLUID_MIXING, BlockRegistry.FLUID_MIXER.get());
		registration.addRecipeCatalysts(SYNTHESIS, BlockRegistry.SYNTHESIS_CHAMBER.get());
		registration.addRecipeCatalysts(METAL_SHAPING, BlockRegistry.METAL_SHAPER.get());
		registration.addRecipeCatalysts(MELTING, BlockRegistry.MELTER.get());
		registration.addRecipeCatalysts(DRYING, BlockRegistry.DRYING_TABLE.get());
		registration.addRecipeCatalysts(MINERAL_PURIFICATION, BlockRegistry.MINERAL_PURIFICATION_CHAMBER.get());

		registration.addRecipeCatalysts(RecipeTypes.CRAFTING, BlockRegistry.FABRICATOR.get(), BlockRegistry.LUNARIUM.get(), BlockRegistry.AUTO_FABRICATOR.get());
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeClickArea(FabricatorScreen.class, 110, 35, 23, 16, FABRICATION, RecipeTypes.CRAFTING);
		registration.addRecipeClickArea(LunariumScreen.class, 110, 35, 23, 16, FABRICATION, RecipeTypes.CRAFTING);
		registration.addRecipeClickArea(AutoFabricatorScreen.class, 110, 35, 23, 16, FABRICATION, RecipeTypes.CRAFTING);
		registration.addRecipeClickArea(InfuserScreen.class, 82, 34, 22, 16, INFUSION);
		registration.addRecipeClickArea(EntropicProcessorScreen.class, 82, 34, 22, 16, ENTROPIC_PROCESSING);
		registration.addRecipeClickArea(FluidMixerScreen.class, 82, 33, 22, 16, FLUID_MIXING);
		registration.addRecipeClickArea(SynthesisChamberScreen.class, 82, 34, 22, 16, SYNTHESIS);
		registration.addRecipeClickArea(MetalShaperScreen.class, 80, 27, 16, 15, METAL_SHAPING);
		registration.addRecipeClickArea(MelterScreen.class, 101, 13, 8, 8, MELTING);
		registration.addRecipeClickArea(MelterScreen.class, 109, 13, 8, 49, MELTING);
		registration.addRecipeClickArea(DryingTableScreen.class, 80, 27, 16, 15, DRYING);
		registration.addRecipeClickArea(MineralPurificationChamberScreen.class, 82, 34, 22, 16, MINERAL_PURIFICATION);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		HalcyonJEI.jeiRuntime = jeiRuntime;
	}

	public boolean checkDisabled() {
		if(ModList.get().isLoaded("emi")) {
			LOGGER.warn("PastelJEI is disabled with EMI as Halcyon has native EMI support");
			return true;
		}
		return false;
	}
}
