package nahama.emptydim.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockEmptyPortal extends ItemBlockWithMetadata {
	public ItemBlockEmptyPortal(Block block) {
		super(block, block);
	}

	/** 翻訳前の名前を返す。 */
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return this.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}
}
