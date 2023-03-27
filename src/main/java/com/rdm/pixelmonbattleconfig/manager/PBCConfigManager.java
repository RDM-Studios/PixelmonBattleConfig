package com.rdm.pixelmonbattleconfig.manager;

import java.io.IOException;

import com.pixelmonmod.pixelmon.api.config.api.yaml.YamlConfigFactory;
import com.rdm.pixelmonbattleconfig.common.config.PBCBattleLossConfig;

public class PBCConfigManager {
	private static PBCBattleLossConfig battleLossConfig;
	
	public static void registerConfigs() {
		registerPBCBattleLossConfig();
	}
	
	private static void registerPBCBattleLossConfig() {
		try {
			battleLossConfig = YamlConfigFactory.getInstance(PBCBattleLossConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PBCBattleLossConfig getBattleLossConfig() {
		return battleLossConfig;
	}

}
