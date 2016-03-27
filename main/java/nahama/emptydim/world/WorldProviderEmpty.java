package nahama.emptydim.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.world.biome.WorldChunkManagerEmpty;
import nahama.emptydim.world.chunk.ChunkProviderEmpty;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderEmpty extends WorldProvider {

	/** WorldChunkManagerを登録する。 */
	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new WorldChunkManagerEmpty(worldObj);
		dimensionId = EmptyDimCore.idDimension;
		hasNoSky = false;
	}

	/** ChunkProviderを返す。 */
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderEmpty(worldObj);
	}

	/** リスポーンできるか。
	 * @return false */
	@Override
	public boolean canRespawnHere() {
		return false;
	}

	/** 通常世界か。
	 * @return false */
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	/** データを保存するフォルダの名前を返す。 */
	@Override
	public String getSaveFolder() {
		return "EmptyDimension";
	}

	/** 名前を返す。 */
	@Override
	public String getDimensionName() {
		return "Empty Dimension";
	}

	/** 入ってきたときのメッセージを返す。 */
	@Override
	public String getWelcomeMessage() {
		return "Entering the Empty Dimension";
	}

	/** 出て行ったときのメッセージを返す。 */
	@Override
	public String getDepartMessage() {
		return "Leaving the Empty Dimension";
	}

	/** 霧の色を返す。 */
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float par1, float par2) {
		return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
	}

	/** 太陽と月の角度を返す。
	 * @return 0.0F */
	@Override
	public float calculateCelestialAngle(long par1, float par2) {
		return 0.0F;
	}

	/** 朝焼け・夕焼けの色を返す。
	 * @return null */
	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		return null;
	}

	/** 空に色が付いているかを返す。
	 * @return false */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored() {
		return false;
	}

	/** 岩盤のパーティクルを表示するか。
	 * @return false */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean getWorldHasVoidParticles() {
		return false;
	}

	/** 岩盤の霧の濃さを返す。
	 * @return 1.0D */
	@Override
	public double getVoidFogYFactor() {
		return 1.0D;
	}

}
