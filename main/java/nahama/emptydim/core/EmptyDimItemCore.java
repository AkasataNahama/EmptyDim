package nahama.emptydim.core;

import cpw.mods.fml.common.registry.GameRegistry;
import nahama.emptydim.item.ItemEmptyPortal;
import net.minecraft.item.Item;

public class EmptyDimItemCore {

	public static Item canePortal;

	/** アイテムを登録する処理。 */
	public static void registerItem() {
		canePortal = new ItemEmptyPortal()
				.setUnlocalizedName("emptydim.cane_of_portal")
				.setTextureName("emptydim:cane_of_portal");
		GameRegistry.registerItem(canePortal, "cane_of_portal");
	}

}
