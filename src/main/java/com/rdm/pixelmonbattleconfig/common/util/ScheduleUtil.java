package com.rdm.pixelmonbattleconfig.common.util;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.rdm.pixelmonbattleconfig.server.events.PBCServerMiscEvents;

public class ScheduleUtil {
	private static final HashMultimap<Integer, Runnable> SCHEDULED_TASKS = HashMultimap.create();
	private static ScheduledExecutorService executor = null;
	
	public static void scheduleSyncedTask(Runnable task, int tickDelay) {
		SCHEDULED_TASKS.put(PBCServerMiscEvents.globalTick + tickDelay, task);
	}
	
	public static void handleStartup() {
		if (executor != null) executor.shutdownNow();
		
		executor = Executors.newScheduledThreadPool(1);
		handleScheduledSyncTasks(null);
	}
	
	public static void handleShutdown() {
		handleScheduledSyncTasks(null);
		
		executor.shutdownNow();
		executor = null;
	}
	
	public static void handleScheduledSyncTasks(@Nullable Integer tickCount) {
		if (SCHEDULED_TASKS.containsKey(tickCount)) {
			Iterator<Runnable> execTasks = tickCount != null ? SCHEDULED_TASKS.get(tickCount).iterator() : SCHEDULED_TASKS.values().iterator();
			
			while (execTasks.hasNext()) {
				execTasks.next().run();
				execTasks.remove();
			}
		}
	}
}
