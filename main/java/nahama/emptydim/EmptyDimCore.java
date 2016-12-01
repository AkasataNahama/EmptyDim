package nahama.emptydim;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nahama.emptydim.core.*;
import nahama.emptydim.handler.EmptyDimEventHandler;
import nahama.emptydim.world.WorldProviderEmpty;
import net.minecraftforge.common.DimensionManager;

/** @author Akasata Nahama */
@Mod(modid = EmptyDimCore.MODID, name = EmptyDimCore.MODNAME, version = EmptyDimCore.VERSION, guiFactory = "nahama.emptydim.gui.EmptyDimGuiFactory")
public class EmptyDimCore {
	// MODの基本情報。
	public static final String MODID = "EmptyDim";
	public static final String MODNAME = "Empty Dim.";
	public static final String MCVERSION = "1.7.10";
	public static final String MODVERSION = "Beta 1.0.2";
	public static final String VERSION = "[" + MCVERSION + "]" + MODVERSION;
	/** coreクラスのインスタンス。 */
	@Instance(MODID)
	public static EmptyDimCore instance;
	/** modの情報を登録。 */
	@Metadata(MODID)
	public static ModMetadata meta;
	/** Empty DimensionのID。 */
	public static byte idDimension = 4;
	/** Empty BiomeのID。 */
	public static short idBiome = 64;

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
		FMLCommonHandler.instance().bus().register(new EmptyDimEventHandler());
	}

	/** 初期化後処理。 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
