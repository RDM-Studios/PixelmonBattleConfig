package com.rdm.pixelmonbattleconfig.common.events;

import com.pixelmonmod.pixelmon.api.battles.BattleResults;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.rdm.pixelmonbattleconfig.PixelmonBattleConfig;
import com.rdm.pixelmonbattleconfig.manager.PBCConfigManager;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PBCCommonMiscEvents {
	
	@SubscribeEvent
	public static void onBattleEnd(BattleEndEvent event) {
		PixelmonBattleConfig.LOGGER.debug("BATTLE ENDED!");
		BattleController battleController = event.getBattleController();		
		
		battleController.getPlayers().forEach((participant) -> {
			if (event.getResult(participant.getEntity()).isPresent()) {
				if (event.getResult(participant.getEntity()).get().equals(BattleResults.DEFEAT)) { // PlayerParticipant#isDefeated doesn't work :(
					switch (PBCConfigManager.getBattleLossConfig().getLossType()) {
					case DEATH:
						participant.getEntity().kill();
						break;
					case CRITICAL:
						participant.getTeamPokemon().forEach((pokemon) -> pokemon.setHealth(1));
						break;
					case RESTORED:
						if (participant.getEntity() instanceof ServerPlayerEntity) {
							ServerPlayerEntity playerParticipant = (ServerPlayerEntity) participant.getEntity();			
							final BlockPos spawnPos = playerParticipant.getRespawnPosition();
							ServerWorld participantLevel = playerParticipant.getLevel();
							final BlockPos globalSpawnPos = participantLevel.getSharedSpawnPos();
							
							if (spawnPos != null) {								
								playerParticipant.teleportTo(participantLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), playerParticipant.yRot, playerParticipant.xRot);
							} else {
								playerParticipant.teleportTo(participantLevel, globalSpawnPos.getX(), globalSpawnPos.getY(), globalSpawnPos.getZ(), playerParticipant.yRot, playerParticipant.xRot);
							}
						}
						participant.getTeamPokemon().forEach((pokemon) -> pokemon.setHealth(pokemon.getMaxHealth()));
						break;
					default:
						participant.getEntity().kill();
						break;
					}
				}
			}
		});
	}
	
	@SubscribeEvent
	public static void onBattleStart(BattleStartedEvent event) {
		PixelmonBattleConfig.LOGGER.debug("BATTLE STARTED!");
	}
	
	@SubscribeEvent
	public static void onTest(PlayerEvent.PlayerLoggedInEvent event) {
		event.getPlayer().displayClientMessage(new StringTextComponent("Test"), true);
	}
	
}
