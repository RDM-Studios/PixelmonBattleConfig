package com.rdm.pixelmonbattleconfig.common.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import com.rdm.pixelmonbattleconfig.common.util.PokeBattleLossType;

import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/pixelmonbattleconfig/battle_loss_config.yml")
public class PBCBattleLossConfig extends AbstractYamlConfig {
	private PokeBattleLossType lossType;
	
	public PBCBattleLossConfig() {
		this.lossType = PokeBattleLossType.DEATH;
	}
	
	public PokeBattleLossType getLossType() {
		return lossType;
	}
}
