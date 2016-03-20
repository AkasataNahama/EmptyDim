package nahama.emptydim.world.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nahama.emptydim.core.EmptyDimBiomeCore;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerEmpty extends WorldChunkManager {

	/** 生成が許可されているバイオームのリスト。 */
	public static ArrayList<BiomeGenEmpty> allowedBiomes = new ArrayList<BiomeGenEmpty>(Arrays.asList(EmptyDimBiomeCore.biomeEmpty));
	private BiomeCache biomeCache;
	private List biomesToSpawnIn;

	protected WorldChunkManagerEmpty() {
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList();
		biomesToSpawnIn.addAll(allowedBiomes);
	}

	public WorldChunkManagerEmpty(long seed, WorldType worldType) {
		this();
	}

	public WorldChunkManagerEmpty(World world) {
		this();
	}

	@Override
	public List getBiomesToSpawnIn() {
		return biomesToSpawnIn;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int z) {
		return biomeCache.getBiomeGenAt(x, z);
	}

	/** 降雨量を返す。 */
	@Override
	public float[] getRainfall(float[] array, int x, int z, int width, int length) {
		for (int i = 0; i < width * length; i++) {
			array[i] = 0.0F;
		}
		return array;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] array, int x, int z, int width, int length) {
		IntCache.resetIntCache();
		if (array == null || array.length < width * length) {
			array = new BiomeGenBase[width * length];
		}
		for (int i = 0; i < width * length; i++) {
			array[i] = EmptyDimBiomeCore.biomeEmpty;
		}
		return array;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
		return this.getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.resetIntCache();
		if (listToReuse == null || listToReuse.length < width * length) {
			listToReuse = new BiomeGenBase[width * length];
		}
		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			BiomeGenBase[] aBiomeGenBase1 = biomeCache.getCachedBiomes(x, z);
			System.arraycopy(aBiomeGenBase1, 0, listToReuse, 0, width * length);
			return listToReuse;
		} else {
			for (int i1 = 0; i1 < width * length; ++i1) {
				listToReuse[i1] = EmptyDimBiomeCore.biomeEmpty;
			}
			return listToReuse;
		}
	}

	@Override
	public boolean areBiomesViable(int x, int z, int par3, List list) {
		IntCache.resetIntCache();
		int l = x - par3 >> 2;
		int i1 = z - par3 >> 2;
		int j1 = x + par3 >> 2;
		int k1 = z + par3 >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		for (int j2 = 0; j2 < l1 * i2; ++j2) {
			if (!list.contains(EmptyDimBiomeCore.biomeEmpty)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ChunkPosition findBiomePosition(int x, int z, int par3, List list, Random random) {
		IntCache.resetIntCache();
		int l = x - par3 >> 2;
		int i1 = z - par3 >> 2;
		int j1 = x + par3 >> 2;
		int k1 = z + par3 >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		ChunkPosition chunkposition = null;
		int j2 = 0;
		for (int k2 = 0; k2 < l1 * i2; ++k2) {
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			if (list.contains(EmptyDimBiomeCore.biomeEmpty) && (chunkposition == null || random.nextInt(j2 + 1) == 0)) {
				chunkposition = new ChunkPosition(l2, 0, i3);
				++j2;
			}
		}
		return chunkposition;
	}

	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	@Override
	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		return original;
	}

}
