package com.rdm.pixelmonbattleconfig.server.events;

import com.rdm.pixelmonbattleconfig.common.util.ScheduleUtil;

import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;

public class PBCServerMiscEvents {
	public static int globalTick;
	
	public static void onServerTickUpdate(ServerTickEvent event) {
		if (event.phase.equals(Phase.END)) {
			globalTick++;
			ScheduleUtil.handleScheduledSyncTasks(globalTick);
		}
	}

}
