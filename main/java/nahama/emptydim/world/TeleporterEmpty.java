package nahama.emptydim.world;

import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.block.BlockEmptyPortal;
import nahama.emptydim.core.EmptyDimBlockCore;
import nahama.emptydim.item.ItemEmptyPortal;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterEmpty extends Teleporter {

	private final WorldServer world;

	/** プレイヤーをテレポートさせる。 */
	public static void teleportPlayer(EntityPlayer player) {
		if (player.worldObj.isRemote || !(player instanceof EntityPlayerMP))
			return;
		if (player.ridingEntity != null || player.riddenByEntity != null)
			return;
		byte toID = (byte) EmptyDimCore.idDimension;
		if (player.worldObj.provider.dimensionId == EmptyDimCore.idDimension)
			toID = 0;
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, toID, new TeleporterEmpty(playerMP.mcServer.worldServerForDimension(toID)));
	}

	public TeleporterEmpty(WorldServer world) {
		super(world);
		this.world = world;
	}

	/** Entityをポータルの中に移動する。 */
	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float par5) {
		this.placeInExistingPortal(entity, x, y, z, par5);
	}

	/** Entityを有効なポータルの中に移動する。 */
	@Override
	public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float par5) {
		// Entityの動きを消す。
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;

		// プレイヤーでないならデフォルトの位置にテレポート。
		if (!(entity instanceof EntityPlayer))
			return this.placeInDefaultLocation(entity);
		ItemStack itemStack = ((EntityPlayer) entity).getHeldItem();
		// 持っているのがポータルの杖でないならデフォルトの位置にテレポート。
		if (itemStack == null || itemStack.getItem() == null || !(itemStack.getItem() instanceof ItemEmptyPortal) || !itemStack.hasTagCompound())
			return this.placeInDefaultLocation(entity);
		NBTTagCompound nbt = itemStack.getTagCompound();
		byte toId = (byte) world.provider.dimensionId;
		if (!nbt.hasKey("pos" + toId))
			return this.placeInDefaultLocation(entity);
		// ポータルの杖に記録された座標に移動させる。
		NBTTagCompound nbtPos = (NBTTagCompound) nbt.getTag("pos" + toId);
		entity.setLocationAndAngles(nbtPos.getDouble("x"), nbtPos.getDouble("y"), nbtPos.getDouble("z"), entity.rotationYaw, entity.rotationPitch);
		return true;
	}

	/** Entityをデフォルトの位置に移動する。 */
	private boolean placeInDefaultLocation(Entity entity) {
		byte toId = (byte) world.provider.dimensionId;
		if (toId != EmptyDimCore.idDimension) {
			// 通常世界への移動。
			// 基本的にはリスポーン地点へ移動。
			ChunkCoordinates chunkcoordinates = world.provider.getRandomizedSpawnPoint();
			if (entity instanceof EntityPlayer) {
				// プレイヤーなら、前に寝たベッドの場所へ移動。
				EntityPlayer player = (EntityPlayer) entity;
				ChunkCoordinates chunkcoordinates1 = player.getBedLocation(toId);
				if (chunkcoordinates1 != null) {
					ChunkCoordinates chunkcoordinates2 = EntityPlayer.verifyRespawnCoordinates(world, chunkcoordinates1, player.isSpawnForced(toId));
					if (chunkcoordinates2 != null)
						chunkcoordinates = chunkcoordinates2;
				}
			}
			entity.setLocationAndAngles(chunkcoordinates.posX + 0.5D, chunkcoordinates.posY + 0.1F, chunkcoordinates.posZ + 0.5D, entity.rotationYaw, entity.rotationPitch);
			return true;
		}
		// Empty Dimensionへの移動。
		// (0,1,0)と(0,2,0)に空気以外のブロックがあるならドロップさせる。(窒息防止)
		for (int i = 1; i < 3; i++) {
			Block block = world.getBlock(0, i, 0);
			if (!block.equals(Blocks.air)) {
				block.dropBlockAsItem(world, 0, i, 0, world.getBlockMetadata(0, i, 0), 0);
				world.setBlockToAir(0, i, 0);
			}
		}
		if (!(world.getBlock(0, 0, 0) instanceof BlockEmptyPortal)) {
			// (0,0,0)が岩盤ポータルでないなら置き換える。
			world.setBlock(0, 0, 0, EmptyDimBlockCore.portal);
			world.setBlockMetadataWithNotify(0, 0, 0, 1, 2);
		}
		entity.setLocationAndAngles(0.5D, 1.0D, 0.5D, entity.rotationYaw, entity.rotationPitch);
		return true;
	}

}
