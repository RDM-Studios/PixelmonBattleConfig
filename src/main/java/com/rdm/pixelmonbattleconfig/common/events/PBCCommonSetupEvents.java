package com.rdm.pixelmonbattleconfig.common.events;

import com.rdm.pixelmonbattleconfig.PixelmonBattleConfig;
import com.rdm.pixelmonbattleconfig.manager.PBCConfigManager;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class PBCCommonSetupEvents {
	
	public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
		PixelmonBattleConfig.LOGGER.debug("Loading PixelmonBattleConfig...");
		PBCConfigManager.registerConfigs();
	}

}
