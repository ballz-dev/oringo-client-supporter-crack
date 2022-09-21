package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoUHC;
import me.oringo.oringoclient.qolfeatures.module.impl.render.PopupAnimation;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class AutoMask extends Module {
  public List<Predicate<String>>  = Lists.newArrayList((Object[])new Predicate[] { AutoMask::, AutoMask::, AutoMask:: });
  
  public static boolean () {
    return ((OringoClient..() && Scaffold..()) || !Speed..(2000L));
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (!() || !SkyblockUtils.inDungeon || !Disabler. || paramClientChatReceivedEvent.type == 2 || !Module.())
      return; 
    String str = paramClientChatReceivedEvent.message.func_150260_c().trim();
    if ((str.startsWith("Your") || str.startsWith("Second Wind Activated!")) && str.endsWith("saved your life!")) {
      ItemStack itemStack = mc.field_71439_g.func_82169_q(3);
      if (itemStack != null) {
        int i = -1;
        for (byte b = 0; b < this..size(); b++) {
          if (((Predicate<String>)this..get(b)).test(itemStack.func_82833_r())) {
            Predicate predicate = this..get((b + 1) % this..size());
            i = AutoTool.(predicate::);
            if (i != -1)
              break; 
            for (Predicate<String> predicate1 : this.) {
              if (predicate1 == this..get(b))
                continue; 
              i = AutoTool.(predicate::);
              if (i != -1)
                break; 
            } 
            break;
          } 
        } 
        if (i != -1 && i != 5) {
          AutoUHC.(i, 0);
          AutoUHC.(5, 0);
          AutoUHC.(i, 0);
        } 
      } 
    } 
  }
  
  public AutoMask() {
    super("Auto Mask", Module.Category.);
  }
  
  public static float (float paramFloat1, float paramFloat2) {
    if (OringoClient.mc.field_71439_g.func_70644_a(Potion.field_76430_j))
      paramFloat1 += (OringoClient.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F; 
    return paramFloat1;
  }
  
  public static Module (Predicate<Module> paramPredicate) {
    for (Module module : OringoClient.) {
      if (paramPredicate.test(module))
        return module; 
    } 
    return null;
  }
  
  public static void () {
    float f = ArmorSwap.();
    ScaledResolution scaledResolution = new ScaledResolution(PopupAnimation.mc);
    GL11.glTranslated(scaledResolution.func_78326_a() / 2.0D, scaledResolution.func_78328_b() / 2.0D, 0.0D);
    GL11.glScaled(f, f, 1.0D);
    GL11.glTranslated(-scaledResolution.func_78326_a() / 2.0D, -scaledResolution.func_78328_b() / 2.0D, 0.0D);
  }
}
