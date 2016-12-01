package nahama.emptydim.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nahama.emptydim.world.TeleporterEmpty;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockEmptyPortal extends Block {
	private IIcon[] icons;

	public BlockEmptyPortal() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(25.0F);
		this.setResistance(6000000.0F);
		this.setStepSound(soundTypePiston);
		this.setHarvestLevel("pickaxe", 2);
		this.setLightLevel(1.0F);
	}

	/** 右クリックされたときの処理。 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		// Dimensionを移動させる。
		TeleporterEmpty.teleportPlayer(player);
		return true;
	}

	/** 硬さを返す。 */
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		// 岩盤ポータルなら破壊不可。
		if (meta == 1)
			return -1.0F;
		return 25.0F;
	}

	/** Mobがスポーンできるか。 */
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		// 岩盤ポータルなら不可。
		return world.getBlockMetadata(x, y, z) != 1;
	}

	/** ドロップアイテムを返す。 */
	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		// 岩盤ポータルならドロップアイテムは無し。
		if (meta == 1)
			return null;
		return Item.getItemFromBlock(this);
	}

	/** ドロップする際のメタデータを返す。 */
	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	/** クリエイティブタブに登録する処理。 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	/** アイコンを登録する処理。 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons = new IIcon[2];
		for (int i = 0; i < icons.length; i++) {
			icons[i] = register.registerIcon(this.getTextureName() + "-" + i);
		}
	}

	/** アイコンを返す。 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}
}
