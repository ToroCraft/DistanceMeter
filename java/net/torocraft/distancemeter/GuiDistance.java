package net.torocraft.distancemeter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiDistance extends Gui {

	public static final int COLOR = 0xFFFFFF;
	private ScaledResolution viewport;

	private Minecraft mc;
	private double distance;

	private boolean enabled = true;

	public GuiDistance() {
		mc = Minecraft.getMinecraft();
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@SubscribeEvent
	public void drawHealthBar(RenderGameOverlayEvent.Pre event) {
		if (isRunEvent(event)) {
			findDistance();
		}
	}

	@SubscribeEvent
	public void drawHealthBar(RenderGameOverlayEvent.Post event) {
		if (isRunEvent(event)) {
			drawNumericDisplayStyle();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(KeyInputEvent event) {
		KeyBinding[] keyBindings = ClientProxy.keyBindings;
		if (keyBindings[0].isPressed()) {
			enabled = !enabled;
		}
	}

	private void findDistance() {
		EntityPlayer player = mc.thePlayer;
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * 1.0F;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * 1.0F;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * 1.0D;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * 1.0D + (double) player.getEyeHeight();
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * 1.0D;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		Vec3d vec3d1 = vec3d.addVector((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);

		RayTraceResult rt = player.rayTrace(500, 1f);

		if (Type.BLOCK.equals(rt.typeOfHit)) {
			distance = rt.hitVec.distanceTo(player.getPositionVector());
		} else {
			distance = 0;
		}
	}

	protected boolean isRunEvent(RenderGameOverlayEvent event) {
		return enabled && event.getType() == ElementType.EXPERIENCE;
	}

	private void drawNumericDisplayStyle() {
		if (distance < 1) {
			return;
		}
		viewport = new ScaledResolution(mc);
		drawString(mc.fontRendererObj, Math.round(distance) + "", viewport.getScaledWidth() / 2 + 10, viewport.getScaledHeight() / 2 - 3, COLOR);
	}

	private int determineFallDamage() {
		// create mock entity

		// copy user armor

		// invoke Block.onFallenUpon(World worldIn, BlockPos pos, Entity
		// entityIn, float fallDistance);

		return 0;
	}

}
