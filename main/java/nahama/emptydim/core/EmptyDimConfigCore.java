package nahama.emptydim.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.util.EmptyDimLog;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

public class EmptyDimConfigCore {
	public static Configuration cfg;
	public static boolean canTeleportFromEnd = true;
	// カテゴリー
	private static final String GENERAL = "General";
	public static final String CHANGEABLE = GENERAL + ".Changeable";

	/** Configを読み込む。 */
	public static void loadConfig(FMLPreInitializationEvent event) {
		cfg = new Configuration(event.getSuggestedConfigurationFile(), true);
		cfg.load();
		syncConfig();
		try {
			EmptyDimCore.idDimension = (byte) cfg.getInt("dimensionID", GENERAL, 4, 2, 127, "ID of Empty Dimension");
			EmptyDimCore.idBiome = (short) cfg.getInt("biomeID", GENERAL, 64, 40, 255, "ID of Empty Biome");
		} catch (Exception e) {
			EmptyDimLog.error("Error on loading config.", "EmptyDimConfigCore");
		} finally {
			cfg.save();
		}
		if (DimensionManager.isDimensionRegistered(EmptyDimCore.idDimension)) {
			EmptyDimLog.error("Dimension ID you selected is already used. Please select other number. selected:" + EmptyDimCore.idDimension, "EmptyDimConfigCore");
			throw new RuntimeException("Invalid Dimension ID (by Empty Dim.)");
		}
		if (BiomeGenBase.getBiomeGenArray()[EmptyDimCore.idBiome] != null) {
			EmptyDimLog.error("Biome ID you selected is already used. Please select other number. selected:" + EmptyDimCore.idBiome, "EmptyDimConfigCore");
			throw new RuntimeException("Invalid Biome ID (by Empty Dim.)");
		}
	}

	/** Configを同期する。 */
	public static void syncConfig() {
		String separator = System.lineSeparator();
		try {
			canTeleportFromEnd = cfg.getBoolean("canTeleportFromEnd", CHANGEABLE, canTeleportFromEnd, "When true, you can teleport to Empty Dimension from The End." + separator + "This function is dangerous especially when some other mod is loaded.");
		} catch (Exception e) {
			EmptyDimLog.error("Error on loading config.", "EmptyDimConfigCore");
		} finally {
			cfg.save();
		}
	}
}
