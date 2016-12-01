package nahama.emptydim.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class EmptyDimRecipeCore {
	public static final EmptyDimBlockCore BLOCK = new EmptyDimBlockCore();
	public static final EmptyDimItemCore ITEM = new EmptyDimItemCore();

	/** レシピを登録する。 */
	public static void registerRecipe() {
		GameRegistry.addRecipe(new ShapedOreRecipe(BLOCK.portal, "OIO", "EDE", "OGO", 'D', "gemDiamond", 'G', "ingotGold", 'I', "ingotIron", 'E', Items.ender_pearl, 'O', Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(ITEM.canePortal, "  P", " S ", "S  ", 'P', BLOCK.portal, 'S', "stickWood"));
	}
}
