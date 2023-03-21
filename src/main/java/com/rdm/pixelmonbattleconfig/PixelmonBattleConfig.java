package com.rdm.pixelmonbattleconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PixelmonBattleConfig.MODID)
public class PixelmonBattleConfig {
	private static PixelmonBattleConfig INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "pixelmonbattleconfig";
    public static final String MODNAME = "Pixelmon Battle Config";

    public PixelmonBattleConfig() {
    	INSTANCE = this;
    	
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public static PixelmonBattleConfig getInstance() {
    	return INSTANCE;
    }
}
