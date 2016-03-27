package nahama.emptydim;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nahama.emptydim.core.EmptyDimBiomeCore;
import nahama.emptydim.core.EmptyDimBlockCore;
import nahama.emptydim.core.EmptyDimConfigCore;
import nahama.emptydim.core.EmptyDimInfoCore;
import nahama.emptydim.core.EmptyDimItemCore;
import nahama.emptydim.core.EmptyDimRecipeCore;
import nahama.emptydim.world.WorldProviderEmpty;
import net.minecraftforge.common.DimensionManager;

/** @author Akasata Nahama */
@Mod(modid = EmptyDimCore.MODID, name = EmptyDimCore.MODNAME, version = EmptyDimCore.VERSION)
public class EmptyDimCore {

	public static final String MODID = "EmptyDim";
	public static final String MODNAME = "Empty Dim.";
	public static final String VERSION = "[1.7.10]Beta 1.0.1";

	@Instance(MODID)
	public static EmptyDimCore instance;

	@Metadata(MODID)
	public static ModMetadata meta;

	public static int idDimension = 4;
	public static int idBiome = 64;

	/** 初期化前処理。 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EmptyDimInfoCore.registerInfo(meta);
		EmptyDimConfigCore.loadConfig(event);
		EmptyDimBlockCore.registerBlock();
		EmptyDimItemCore.registerItem();
		EmptyDimBiomeCore.registerBiome();
		// WorldProviderを登録する。
		DimensionManager.registerProviderType(idDimension, WorldProviderEmpty.class, false);
		// Dimensionを登録する。
		DimensionManager.registerDimension(idDimension, idDimension);
	}

	/** 初期化処理。 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		EmptyDimRecipeCore.registerRecipe();
	}

	/** 初期化後処理。 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}

}
