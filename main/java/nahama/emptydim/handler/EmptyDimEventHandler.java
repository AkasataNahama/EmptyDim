package nahama.emptydim.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import nahama.emptydim.EmptyDimCore;
import nahama.emptydim.core.EmptyDimConfigCore;

public class EmptyDimEventHandler {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(EmptyDimCore.MODID))
			EmptyDimConfigCore.syncConfig();
	}
}
