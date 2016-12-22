package net.torocraft.distancemeter;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DistanceMeter.MODID, name = DistanceMeter.MODNAME, version = DistanceMeter.VERSION, clientSideOnly = true)
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
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
