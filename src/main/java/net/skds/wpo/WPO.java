package net.skds.wpo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.skds.core.util.SKDSUtils.Side;
import net.skds.core.util.data.ChunkSectionAdditionalData;
import net.skds.wpo.client.ClientEvents;
import net.skds.wpo.data.FluidStateContainer;
import net.skds.wpo.data.WPOChunkData;
import net.skds.wpo.network.PacketHandler;
import net.skds.wpo.registry.Entities;
import net.skds.wpo.registry.FBlocks;
import net.skds.wpo.registry.Items;

@Mod(WPO.MOD_ID)
public class WPO {
	public static final String MOD_ID = "wpo";
	public static final String MOD_NAME = "Water Physics Overhaul";
	public static final Logger LOGGER = LogManager.getLogger();

	public static Events EVENTS = new Events();

	public WPO() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupFinal);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(EVENTS);
		MinecraftForge.EVENT_BUS.register(this);

		WPOConfig.init();
		Items.register();
		FBlocks.register();
		Entities.register();
		PacketHandler.init();
	}

	private void setup(final FMLCommonSetupEvent event) {
		//ChunkSectionAdditionalData.register(WPOChunkData::new, Side.BOTH);
	}

	private void setupFinal(final FMLLoadCompleteEvent event) {
		FluidStateContainer.setIdentityMap();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		ClientEvents.setup(event);
	}
}
