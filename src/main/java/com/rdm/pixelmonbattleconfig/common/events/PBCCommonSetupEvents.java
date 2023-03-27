package com.rdm.pixelmonbattleconfig.common.events;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.rdm.pixelmonbattleconfig.PixelmonBattleConfig;
import com.rdm.pixelmonbattleconfig.manager.PBCConfigManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class PBCCommonSetupEvents {
	
	public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
		PixelmonBattleConfig.LOGGER.debug("Loading PixelmonBattleConfig...");
		PBCCommonMiscEvents miscEvents = new PBCCommonMiscEvents();
		MinecraftForge.EVENT_BUS.register(miscEvents);
		Pixelmon.EVENT_BUS.register(miscEvents);
		PBCConfigManager.registerConfigs();
	}

}
