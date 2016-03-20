package nahama.emptydim.world.chunk;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkProviderEmpty implements IChunkProvider {

	private final World worldObj;
	private WorldType worldType;
	private BiomeGenBase[] biomesForGeneration;

	public ChunkProviderEmpty(World world) {
		worldObj = world;
		worldType = world.getWorldInfo().getTerrainType();
	}

	@Override
	public Chunk loadChunk(int chunkX, int chunkZ) {
		return this.provideChunk(chunkX, chunkZ);
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		// チャンクを生成して返す？
		// スーパーフラットを参考にした。
		Chunk chunk = new Chunk(worldObj, chunkX, chunkZ);
		Block block = Blocks.bedrock;
		ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[0];
		if (extendedblockstorage == null) {
			extendedblockstorage = new ExtendedBlockStorage(0, !worldObj.provider.hasNoSky);
			chunk.getBlockStorageArray()[0] = extendedblockstorage;
		}
		for (int ix = 0; ix < 16; ++ix) {
			for (int iz = 0; iz < 16; ++iz) {
				extendedblockstorage.func_150818_a(ix, 0 & 15, iz, block);
				extendedblockstorage.setExtBlockMetadata(ix, 0 & 15, iz, 0);
			}
		}
		chunk.generateSkylightMap();
		BiomeGenBase[] abiomegenbase = worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, chunkX * 16, chunkZ * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();
		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte) abiomegenbase[i].biomeID;
		}
		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ) {
		return true;
	}

	@Override
	public void populate(IChunkProvider iChunkProvider, int chunkX, int chunkZ) {}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
		return true;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "ChunkProviderEmpty";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType enumCreatureType, int x, int y, int z) {
		return null;
	}

	@Override
	public ChunkPosition func_147416_a(World world, String s, int x, int y, int z) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int chunkX, int chunkZ) {}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public void saveExtraData() {}

}
