package com.rdm.pixelmonbattleconfig.server.events;

import com.rdm.pixelmonbattleconfig.common.util.ScheduleUtil;

import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class PBCServerSetupEvents {
	
	public static void onServerStartup(FMLServerStartingEvent event) {
		ScheduleUtil.handleStartup();
	}
	
	public static void onServerShutdown(FMLServerStoppingEvent event) {
		if (event.getServer().isDedicatedServer()) ScheduleUtil.handleShutdown();
	}

}
