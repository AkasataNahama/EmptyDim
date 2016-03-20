package nahama.emptydim.block;

import java.util.List;
import java.util.Random;

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

public class BlockEmptyPortal extends Block {

	private IIcon[] iicon = new IIcon[2];

	public BlockEmptyPortal() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(25.0F);
		this.setResistance(6000000.0F);
		this.setStepSound(soundTypePiston);
		this.setHarvestLevel("pickaxe", 2);
		this.setLightLevel(1.0F);
	}

	/** ドロップアイテムを返す。 */
	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		if (meta == 1)
			return null;
		return Item.getItemFromBlock(this);
	}

	/** 硬さを返す。 */
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		// メタデータ1(岩盤ポータル)は破壊不可。
		if (meta == 1)
			return -1.0F;
		return 25.0F;
	}

	/** アイコンを登録する処理。 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		for (int i = 0; i < iicon.length; i++) {
			iicon[i] = register.registerIcon(this.getTextureName() + "-" + i);
		}
	}

	/** アイコンを返す。 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return iicon[meta];
	}

	/** クリエイティブタブに登録する処理。 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for (int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	/** ドロップする際のメタデータを返す。 */
	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	/** Mobがスポーンできるか。 */
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		// メタデータ1(岩盤ポータル)では不可。
		return world.getBlockMetadata(x, y, z) != 1;
	}

	/** 右クリックされたときの処理。 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		// Dimensionを移動させる。
		TeleporterEmpty.teleportPlayer(player);
		return true;
	}

}
