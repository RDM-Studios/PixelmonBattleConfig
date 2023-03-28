package com.rdm.pixelmonbattleconfig;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.rdm.pixelmonbattleconfig.common.events.PBCCommonMiscEvents;
import com.rdm.pixelmonbattleconfig.common.events.PBCCommonSetupEvents;
import com.rdm.pixelmonbattleconfig.server.events.PBCServerMiscEvents;
import com.rdm.pixelmonbattleconfig.server.events.PBCServerSetupEvents;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;

@Mod(PixelmonBattleConfig.MODID)
public class PixelmonBattleConfig {
	public static PixelmonBattleConfig INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "pixelmonbattleconfig";
    public static final String MODNAME = "Pixelmon Battle Config";
    public static ArtifactVersion MOD_VERSION = null;

    public PixelmonBattleConfig() {
    	INSTANCE = this;
    	
    	IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	IEventBus pixelmonBus = Pixelmon.EVENT_BUS;
		    	
		Optional<? extends ModContainer> opt = ModList.get().getModContainerById(MODID);
		if (opt.isPresent()) {
			IModInfo modInfo = opt.get().getModInfo();
			MOD_VERSION = modInfo.getVersion();
			LOGGER.debug("Playing on {} version {}!", MODNAME, MOD_VERSION); // Should probably look into uhh.. proper version display or smth
		} else {
			LOGGER.warn("Cannot get version from mod info");
		}
				
		modBus.addListener(PBCCommonSetupEvents::onFMLCommonSetup);
		forgeBus.addListener(PBCServerMiscEvents::onServerTickUpdate);
		forgeBus.addListener(PBCServerSetupEvents::onServerStartup);
		forgeBus.addListener(PBCServerSetupEvents::onServerShutdown);
		pixelmonBus.addListener(PBCCommonMiscEvents::onBattleStart);
		pixelmonBus.addListener(PBCCommonMiscEvents::onTrainerBattleLoss);
		pixelmonBus.addListener(PBCCommonMiscEvents::onWildPokemonLoss);
        
		forgeBus.register(this);
    }
    
	public static boolean isLoaded() {
		return INSTANCE != null;
	}
}
