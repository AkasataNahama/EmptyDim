package nahama.emptydim.item;

import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.world.TeleporterEmpty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemEmptyPortal extends Item {
	public ItemEmptyPortal() {
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setFull3D();
		this.setMaxStackSize(1);
	}

	/** 右クリックされた時の処理。 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote && (player.dimension == 0 || player.dimension == EmptyDimCore.idDimension)) {
			// サーバー側で、通常世界かEmpty Dimensionなら移動前の座標を記録する。
			NBTTagCompound nbtPos = new NBTTagCompound();
			nbtPos.setDouble("x", player.posX);
			nbtPos.setDouble("y", player.posY);
			nbtPos.setDouble("z", player.posZ);
			if (!itemStack.hasTagCompound())
				itemStack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt = itemStack.getTagCompound();
			nbt.setTag("pos" + player.dimension, nbtPos);
		}
		// Dimensionを移動させる。
		TeleporterEmpty.teleportPlayer(player);
		return itemStack;
	}
}
