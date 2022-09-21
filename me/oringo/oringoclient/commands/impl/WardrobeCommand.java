package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class WardrobeCommand extends Command {
  public int  = -1;
  
  public MilliTimer  = new MilliTimer();
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt) {
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    double d1 = (paramFloat1 + paramFloat3);
    double d2 = (paramFloat2 + paramFloat4);
    float f1 = (paramInt >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt & 0xFF) / 255.0F;
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramFloat1 *= 2.0F;
    paramFloat2 *= 2.0F;
    d1 *= 2.0D;
    d2 *= 2.0D;
    GL11.glLineWidth(paramFloat6);
    GL11.glDisable(3553);
    GL11.glColor4f(f2, f3, f4, f1);
    GL11.glEnable(2848);
    GL11.glBegin(2);
    byte b;
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d((paramFloat1 + paramFloat5) + Math.sin(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F), (paramFloat2 + paramFloat5) + Math.cos(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F)); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d((paramFloat1 + paramFloat5) + Math.sin(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F), d2 - paramFloat5 + Math.cos(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F)); 
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(d1 - paramFloat5 + Math.sin(b * Math.PI / 180.0D) * paramFloat5, d2 - paramFloat5 + Math.cos(b * Math.PI / 180.0D) * paramFloat5); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(d1 - paramFloat5 + Math.sin(b * Math.PI / 180.0D) * paramFloat5, (paramFloat2 + paramFloat5) + Math.cos(b * Math.PI / 180.0D) * paramFloat5); 
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  public WardrobeCommand() {
    super("wd", new String[] { "wardrobe" });
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (this. != -1 && paramPacketReceivedEvent. instanceof S2DPacketOpenWindow) {
      if (this..(2500L)) {
        this. = -1;
        return;
      } 
      if (((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_179840_c().func_150254_d().startsWith("Pets")) {
        int i = ((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_148901_c();
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(i, 48, 0, 3, null, (short)0));
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(i + 1, 32, 0, 3, null, (short)0));
        if (this. <= 9) {
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(i + 2, 35 + this., 0, 0, null, (short)0));
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0DPacketCloseWindow(i + 2));
        } else {
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(i + 2, 53, 0, 3, null, (short)0));
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(i + 3, 35 + this., 0, 0, null, (short)0));
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0DPacketCloseWindow(i + 3));
        } 
        paramPacketReceivedEvent.setCanceled(true);
      } else if (((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_179840_c().func_150254_d().startsWith("SkyBlock Menu")) {
        paramPacketReceivedEvent.setCanceled(true);
      } else if (((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_179840_c().func_150254_d().startsWith("Wardrobe")) {
        paramPacketReceivedEvent.setCanceled(true);
        this. = -1;
      } 
    } 
  }
  
  public String () {
    return "Instant wardrobe";
  }
  
  public static boolean (int paramInt) {
    while (paramInt > 0) {
      BlockPos blockPos = new BlockPos(ClipCommand.mc.field_71439_g.field_70165_t, paramInt, ClipCommand.mc.field_71439_g.field_70161_v);
      IBlockState iBlockState = ClipCommand.mc.field_71441_e.func_180495_p(blockPos);
      if (!iBlockState.func_177230_c().func_176205_b((IBlockAccess)ClipCommand.mc.field_71441_e, blockPos))
        return false; 
      paramInt--;
    } 
    return true;
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length > 0) {
      this. = Math.min(Math.max(Integer.parseInt(paramArrayOfString[0]), 1), 18);
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C01PacketChatMessage("/pets"));
      this..();
    } else {
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C01PacketChatMessage("/wd"));
    } 
  }
}
