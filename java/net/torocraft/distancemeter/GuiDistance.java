package net.torocraft.distancemeter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
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

			determineFallDamage(rt.getBlockPos());

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

	private int determineFallDamage(BlockPos toPos) {

		EntityPlayer player = mc.thePlayer;
		World world = player.worldObj;
		int fallDistance = player.getPosition().getY() - toPos.getY();

		if (fallDistance < 1) {
			System.out.println("fall damage < 1 :: " + fallDistance);
			return 0;
		}

		// 1, hay = 0.2, slime = 0
		float damageMultiplier = 1f;
		float distance = fallDistance;

		PotionEffect potioneffect = player.getActivePotionEffect(MobEffects.JUMP_BOOST);
		float f = potioneffect == null ? 0.0F : (float) (potioneffect.getAmplifier() + 1);
		int damageAmount = MathHelper.ceiling_float_int((distance - 3.0F - f) * damageMultiplier);
		
		
		String[] methods = { "applyArmorCalculations", "func_70655_b" };

		System.out.println("raw fall damage [" + damageAmount + "]");

		float damage = damageAmount;
		try {
			damage = (Float) ReflectionHelper.findMethod(EntityLivingBase.class, player, methods, DamageSource.class, Float.TYPE).invoke(player, DamageSource.fall, (float) damageAmount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("fall damage [" + damage + "]");

		// damageAmount = player.applyArmorCalculations(DamageSource.fall,
		// damageAmount);
		// damageAmount = this.applyPotionDamageCalculations(damageSrc,
		// damageAmount);
		


		System.out.println("health before[" + player.getHealth() + "] after[" + (player.getHealth() - damage) + "]");

		// create mock entity

		// copy user armor

		// invoke Block.onFallenUpon(World worldIn, BlockPos pos, Entity
		// entityIn, float fallDistance);

		return 0;
	}

}
