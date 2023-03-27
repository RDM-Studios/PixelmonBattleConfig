package com.rdm.pixelmonbattleconfig.common.util;

public enum PokeBattleLossType {
	DEATH("The player is killed upon losing a battle."),
	CRITICAL("The player is sent back to their spawnpoint with their pokemon in critical condition upon losing a battle."),
	RESTORED("The player is sent back to their spawnpoint with their pokemon's health restored upon losing a battle.");
	
	private final String description;
	
	PokeBattleLossType(String desc) {
		this.description = desc;
	}
	
	public String getDescription() {
		return description;
	}

}
