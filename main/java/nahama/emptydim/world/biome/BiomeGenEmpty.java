package nahama.emptydim.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenEmpty extends BiomeGenBase {

	public BiomeGenEmpty(int id) {
		super(id);
		// モンスター・動物がわかないようにする。
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();

		// 地表は空気に、地下は岩盤になるよう設定。
		topBlock = Blocks.air;
		fillerBlock = Blocks.bedrock;

		// 基準の高さが0で起伏のない地形に設定。
		rootHeight = 0.0F;
		heightVariation = 0.0F;

		// 雨が降らないよう設定。
		enableRain = false;
		enableSnow = false;
		rainfall = 0.0F;
		temperature = 0.8F;

		// 草原バイオームと同じ色になるよう設定。
		color = 0x8DB360;
		field_150609_ah = 0x8DB360;
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random random) {
		return null;
	}

	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ) {}

	/** 草の色を返す。 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int x, int y, int z) {
		// 草原バイオームと同じ値を返す。
		double d0 = MathHelper.clamp_float(BiomeGenBase.plains.getFloatTemperature(x, y, z), 0.0F, 1.0F);
		double d1 = MathHelper.clamp_float(BiomeGenBase.plains.getFloatRainfall(), 0.0F, 1.0F);
		return this.getModdedBiomeGrassColor(ColorizerGrass.getGrassColor(d0, d1));
	}

	/** 葉の色を返す。 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int x, int y, int z) {
		// 草原バイオームと同じ値を返す。
		double d0 = MathHelper.clamp_float(BiomeGenBase.plains.getFloatTemperature(x, y, z), 0.0F, 1.0F);
		double d1 = MathHelper.clamp_float(BiomeGenBase.plains.getFloatRainfall(), 0.0F, 1.0F);
		return this.getModdedBiomeFoliageColor(ColorizerFoliage.getFoliageColor(d0, d1));
	}

}
