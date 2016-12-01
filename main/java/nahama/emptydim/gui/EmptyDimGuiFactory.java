package nahama.emptydim.gui;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.core.EmptyDimConfigCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.Set;

public class EmptyDimGuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return EmptyDimGuiConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	public static class EmptyDimGuiConfig extends GuiConfig {
		public EmptyDimGuiConfig(GuiScreen parent) {
			super(parent, new ConfigElement(EmptyDimConfigCore.cfg.getCategory(EmptyDimConfigCore.CHANGEABLE)).getChildElements(), EmptyDimCore.MODID, false, false, EmptyDimCore.MODNAME);
		}
	}
}
