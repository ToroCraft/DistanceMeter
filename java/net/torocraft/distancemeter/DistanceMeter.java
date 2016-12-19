package net.torocraft.distancemeter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.torocraft.distancemeter.config.ConfigurationHandler;
import net.torocraft.distancemeter.events.Events;

@Mod(modid = DistanceMeter.MODID, name = DistanceMeter.MODNAME, version = DistanceMeter.VERSION, clientSideOnly = true, guiFactory = "net.torocraft."
		+ DistanceMeter.MODID + ".gui.GuiFactoryToroHealth")
public class DistanceMeter {

	public static final String MODID = "distancemetermod";
	public static final String VERSION = "1.11.2-1";
	public static final String MODNAME = "Distance Meter";

	@SidedProxy(clientSide = "net.torocraft.distancemeter.ClientProxy", serverSide = "net.torocraft.distancemeter.ServerProxy")
	public static CommonProxy proxy;

	@Instance(value = DistanceMeter.MODID)
	public static DistanceMeter instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);

		ConfigurationHandler.init(e.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		MinecraftForge.EVENT_BUS.register(new Events());
	}

}
