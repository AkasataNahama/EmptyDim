package nahama.emptydim.core;

import cpw.mods.fml.common.registry.GameRegistry;
import nahama.emptydim.block.BlockEmptyPortal;
import nahama.emptydim.item.ItemBlockEmptyPortal;
import net.minecraft.block.Block;

public class EmptyDimBlockCore {
	public static Block portal;

	/** ブロックを登録する。 */
	public static void registerBlock() {
		portal = new BlockEmptyPortal().setBlockName("emptydim.portal").setBlockTextureName("emptydim:portal");
		GameRegistry.registerBlock(portal, ItemBlockEmptyPortal.class, "portal");
	}
}
