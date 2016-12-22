package net.torocraft.distancemeter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	public static KeyBinding[] keyBindings;

	private GuiDistance gui;
	private Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		gui = new GuiDistance();
		keyBindings = new KeyBinding[1];
		keyBindings[0] = new KeyBinding("key.show_distance", 51, "key.categories.misc");
		ClientRegistry.registerKeyBinding(keyBindings[0]);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		MinecraftForge.EVENT_BUS.register(gui);
	}

	@Override
	public void setCurrentDistance() {
		gui.setDistance(gui.getDistance() + 0.01);
	}

}