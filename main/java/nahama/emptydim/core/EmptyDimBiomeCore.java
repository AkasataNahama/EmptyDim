package nahama.emptydim.core;

import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.world.biome.BiomeGenEmpty;

public class EmptyDimBiomeCore {
	public static BiomeGenEmpty biomeEmpty;

	/** バイオームを登録する。 */
	public static void registerBiome() {
		biomeEmpty = new BiomeGenEmpty(EmptyDimCore.idBiome);
		biomeEmpty.setBiomeName("Empty Biome");
	}
}
