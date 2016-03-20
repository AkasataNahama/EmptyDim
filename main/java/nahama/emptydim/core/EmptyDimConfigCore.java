package nahama.emptydim.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.util.Util;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

public class EmptyDimConfigCore {

	private static final String GENERAL = "General";

	/** Configを読み込む処理。 */
	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile(), true);
		try {
			cfg.load();
			EmptyDimCore.idDimension = cfg.getInt("dimensionID", GENERAL, 4, 0, Integer.MAX_VALUE, "ID of Empty Dimension");
			EmptyDimCore.idBiome = cfg.getInt("biomeID", GENERAL, 64, 0, 255, "ID of Empty Biome");
		} catch (Exception e) {
			Util.error("Error on loading config.", "StarWoodsConfigCore");
		} finally {
			cfg.save();
		}

		if (DimensionManager.isDimensionRegistered(EmptyDimCore.idDimension)) {
			Util.error("Dimension ID you selected is already used! Please select other number. selected:" + EmptyDimCore.idDimension, "StarWoodsConfigCore");
			throw new RuntimeException("Invalid Dimension ID (by Empty Dim.)");
		}
		if (BiomeGenBase.getBiomeGenArray()[EmptyDimCore.idBiome] != null) {
			Util.error("Biome ID you selected is already used! Please select other number. selected:" + EmptyDimCore.idBiome, "StarWoodsConfigCore");
			throw new RuntimeException("Invalid Biome ID (by Empty Dim.)");
		}
	}

}
