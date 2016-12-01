package nahama.emptydim.world;

import cpw.mods.fml.common.FMLCommonHandler;
import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.block.BlockEmptyPortal;
import nahama.emptydim.core.EmptyDimBlockCore;
import nahama.emptydim.core.EmptyDimConfigCore;
import nahama.emptydim.item.ItemEmptyPortal;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterEmpty extends Teleporter {
	private final WorldServer toWorld;

	/** プレイヤーをテレポートさせる。 */
	public static void teleportPlayer(EntityPlayer player) {
		// クライアント側か、乗っている・乗られているエンティティがいるなら終了。
		if (player.worldObj.isRemote || !(player instanceof EntityPlayerMP))
			return;
		if (player.ridingEntity != null || player.riddenByEntity != null)
			return;
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		// エンドからの移動で、許可されていないなら終了。
		if (playerMP.dimension == 1 && !EmptyDimConfigCore.canTeleportFromEnd)
			return;
		// 移動先のディメンションIDを取得する。
		int toId = EmptyDimCore.idDimension;
		// Empty Dimensionからの移動なら通常世界へ。
		if (playerMP.dimension == toId)
			toId = 0;
		if (playerMP.dimension != 1) {
			// エンドからの移動でないなら、通常の処理でプレイヤーを移動させる。
			playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, toId, new TeleporterEmpty(playerMP.mcServer.worldServerForDimension(toId)));
		} else {
			// エンドからの移動なら、通常の処理でTeleporter.placeInPortalが呼ばれないため、こちらで処理をする。
			teleportPlayerFromEndDangerously(playerMP);
		}
		// 経験値をクライアント側に反映させるため、空の更新を行う。
		playerMP.addExperienceLevel(0);
	}

	/** プレイヤーをエンドからEmpty Dimensionにテレポートさせる。 */
	private static void teleportPlayerFromEndDangerously(EntityPlayerMP player) {
		// ServerConfigurationManager.transferPlayerToDimensionのコピー。一部改変。
		int toId = EmptyDimCore.idDimension;
		// 基本的には1。
		int fromId = player.dimension;
		player.dimension = toId;
		WorldServer worldServerEnd = player.mcServer.worldServerForDimension(fromId);
		WorldServer worldServerEmpty = player.mcServer.worldServerForDimension(toId);
		player.playerNetServerHandler.sendPacket(new S07PacketRespawn(toId, worldServerEmpty.difficultySetting, worldServerEmpty.getWorldInfo().getTerrainType(), player.theItemInWorldManager.getGameType()));
		worldServerEnd.removePlayerEntityDangerously(player);
		player.isDead = false;
		// ここからServerConfigurationManager.transferEntityToWorld。
		// この前後にあったネザー用座標変換処理は省略。
		// ここにあったエンド（及びコメントアウトされていた通常世界・ネザー）からの移動時処理は省略。
		// ここからエンドからの移動時に実行されていなかった部分。
		worldServerEnd.theProfiler.startSection("placing");
		if (player.isEntityAlive()) {
			// ここにあった座標変換適用処理は省略。
			new TeleporterEmpty(worldServerEmpty).placeInPortal(player, player.posX, player.posY, player.posZ, player.rotationYaw);
			worldServerEmpty.spawnEntityInWorld(player);
			worldServerEmpty.updateEntityWithOptionalForce(player, false);
		}
		worldServerEnd.theProfiler.endSection();
		// ここまでエンドからの移動時に実行されていなかった部分。
		player.setWorld(worldServerEmpty);
		// ここまでServerConfigurationManager.transferEntityToWorld。
		player.mcServer.getConfigurationManager().func_72375_a(player, worldServerEnd);
		player.playerNetServerHandler.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		player.theItemInWorldManager.setWorld(worldServerEmpty);
		player.mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(player, worldServerEmpty);
		player.mcServer.getConfigurationManager().syncPlayerInventory(player);
		for (Object o : player.getActivePotionEffects()) {
			PotionEffect potioneffect = (PotionEffect) o;
			player.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), potioneffect));
		}
		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, fromId, EmptyDimCore.idDimension);
	}

	public TeleporterEmpty(WorldServer world) {
		super(world);
		this.toWorld = world;
	}

	/** Entityをポータルの中に移動する。 */
	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float rotationYaw) {
		this.placeInExistingPortal(entity, x, y, z, rotationYaw);
	}

	/** Entityを有効なポータルの中に移動する。 */
	@Override
	public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float rotationYaw) {
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
		int toId = toWorld.provider.dimensionId;
		// テレポート先のディメンションの座標が記録されていないならデフォルトの位置にテレポート。
		if (!nbt.hasKey("pos" + toId))
			return this.placeInDefaultLocation(entity);
		// ポータルの杖に記録された座標に移動させる。
		NBTTagCompound nbtPos = (NBTTagCompound) nbt.getTag("pos" + toId);
		entity.setLocationAndAngles(nbtPos.getDouble("x"), nbtPos.getDouble("y"), nbtPos.getDouble("z"), entity.rotationYaw, entity.rotationPitch);
		return true;
	}

	/** Entityをデフォルトの位置に移動する。 */
	private boolean placeInDefaultLocation(Entity entity) {
		int toId = toWorld.provider.dimensionId;
		if (toId != EmptyDimCore.idDimension) {
			// 通常世界への移動なら、基本的にはリスポーン地点へ移動。
			ChunkCoordinates spawnPoint = toWorld.provider.getRandomizedSpawnPoint();
			if (entity instanceof EntityPlayer) {
				// プレイヤーなら、前に寝たベッドの場所へ移動。
				EntityPlayer player = (EntityPlayer) entity;
				ChunkCoordinates bedLocation = player.getBedLocation(toId);
				if (bedLocation != null) {
					ChunkCoordinates respawnCoordinates = EntityPlayer.verifyRespawnCoordinates(toWorld, bedLocation, player.isSpawnForced(toId));
					if (respawnCoordinates != null)
						spawnPoint = respawnCoordinates;
				}
			}
			entity.setLocationAndAngles(spawnPoint.posX + 0.5D, spawnPoint.posY + 0.1F, spawnPoint.posZ + 0.5D, entity.rotationYaw, entity.rotationPitch);
			return true;
		}
		// Empty Dimensionへの移動。
		for (int i = 1; i < 3; i++) {
			// (0,1,0)と(0,2,0)に空気以外のブロックがあるならドロップさせる。(窒息防止)
			Block block = toWorld.getBlock(0, i, 0);
			if (!block.equals(Blocks.air)) {
				block.dropBlockAsItem(toWorld, 0, i, 0, toWorld.getBlockMetadata(0, i, 0), 0);
				toWorld.setBlockToAir(0, i, 0);
			}
		}
		if (!(toWorld.getBlock(0, 0, 0) instanceof BlockEmptyPortal)) {
			// (0,0,0)が岩盤ポータルでないなら置き換える。
			toWorld.setBlock(0, 0, 0, EmptyDimBlockCore.portal);
			toWorld.setBlockMetadataWithNotify(0, 0, 0, 1, 2);
		}
		entity.setLocationAndAngles(0.5D, 1.0D, 0.5D, entity.rotationYaw, entity.rotationPitch);
		return true;
	}
}
