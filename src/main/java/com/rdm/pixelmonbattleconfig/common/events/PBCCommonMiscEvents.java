package com.rdm.pixelmonbattleconfig.common.events;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.LostToTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.LostToWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.rdm.pixelmonbattleconfig.PixelmonBattleConfig;
import com.rdm.pixelmonbattleconfig.common.util.ScheduleUtil;
import com.rdm.pixelmonbattleconfig.manager.PBCConfigManager;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;

public class PBCCommonMiscEvents {
	public static int globalTick;

	public static void onTrainerBattleLoss(LostToTrainerEvent event) {
		BattleController battleController = event.trainer.battleController;

		battleController.getPlayers().forEach((participant) -> {
			if (participant.isDefeated) {
				ScheduleUtil.scheduleSyncedTask(() -> {
					switch (PBCConfigManager.getBattleLossConfig().getLossType()) {
					case DEATH:
						participant.getEntity().kill();
						break;
					case CRITICAL:
						if (participant.getEntity() instanceof ServerPlayerEntity) {
							ServerPlayerEntity playerParticipant = (ServerPlayerEntity) participant.getEntity();
							PlayerPartyStorage party = StorageProxy.getParty(playerParticipant);
							
							party.getTeam().forEach((pokemon) -> pokemon.setHealth(1));
						}
						break;
					case RESTORED:
						if (participant.getEntity() instanceof ServerPlayerEntity) {
							ServerPlayerEntity playerParticipant = (ServerPlayerEntity) participant.getEntity();
							PlayerPartyStorage party = StorageProxy.getParty(playerParticipant);
							final BlockPos spawnPos = playerParticipant.getRespawnPosition();
							ServerWorld participantLevel = playerParticipant.getLevel();
							final BlockPos globalSpawnPos = participantLevel.getSharedSpawnPos();

							if (spawnPos != null) {								
								playerParticipant.teleportTo(participantLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), playerParticipant.yRot, playerParticipant.xRot);
							} else {
								playerParticipant.teleportTo(participantLevel, globalSpawnPos.getX(), globalSpawnPos.getY(), globalSpawnPos.getZ(), playerParticipant.yRot, playerParticipant.xRot);
							}
							party.getTeam().forEach((pokemon) -> pokemon.heal());
						}
						break;
					default:
						participant.getEntity().kill();
						break;
					}
				}, 100);
			}
		});
	}

	public static void onWildPokemonLoss(LostToWildPixelmonEvent event) {
		BattleController battleController = event.wpp.bc;

		battleController.getPlayers().forEach((participant) -> {
			if (participant.isDefeated) {
				ScheduleUtil.scheduleSyncedTask(() -> {
					switch (PBCConfigManager.getBattleLossConfig().getLossType()) {
					case DEATH:
						participant.getEntity().kill();
						break;
					case CRITICAL:
						if (participant.getEntity() instanceof ServerPlayerEntity) {
							ServerPlayerEntity playerParticipant = (ServerPlayerEntity) participant.getEntity();
							PlayerPartyStorage party = StorageProxy.getParty(playerParticipant);
							
							party.getTeam().forEach((pokemon) -> pokemon.setHealth(1));
						}
						break;
					case RESTORED:
						if (participant.getEntity() instanceof ServerPlayerEntity) {
							ServerPlayerEntity playerParticipant = (ServerPlayerEntity) participant.getEntity();
							PlayerPartyStorage party = StorageProxy.getParty(playerParticipant);
							final BlockPos spawnPos = playerParticipant.getRespawnPosition();
							ServerWorld participantLevel = playerParticipant.getLevel();
							final BlockPos globalSpawnPos = participantLevel.getSharedSpawnPos();

							if (spawnPos != null) {								
								playerParticipant.teleportTo(participantLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), playerParticipant.yRot, playerParticipant.xRot);
							} else {
								playerParticipant.teleportTo(participantLevel, globalSpawnPos.getX(), globalSpawnPos.getY(), globalSpawnPos.getZ(), playerParticipant.yRot, playerParticipant.xRot);
							}
							party.getTeam().forEach((pokemon) -> pokemon.heal());
						}
						break;
					default:
						participant.getEntity().kill();
						break;
					}
				}, 100);
			}
		});
	}

	public static void onBattleStart(BattleStartedEvent event) {
		PixelmonBattleConfig.LOGGER.debug("BATTLE STARTED!");
	}

	public static void onServerTickUpdate(ServerTickEvent event) {
		if (event.phase.equals(Phase.END)) {
			globalTick++;
			ScheduleUtil.handleScheduledSyncTasks(globalTick);
		}
	}
}
