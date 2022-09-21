package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.commands.impl.PlayerListCommand;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.font.CFont;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class Giants extends Module {
  public NumberSetting  = new NumberSetting("Scale", 1.0D, 0.1D, 5.0D, 0.1D);
  
  public BooleanSetting  = new BooleanSetting("ArmorStands", false);
  
  public BooleanSetting  = new BooleanSetting("Mobs", true);
  
  public BooleanSetting  = new BooleanSetting("Players", true);
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, int paramInt1, int paramInt2) {
    PlayerListCommand.();
    NoCarpet.(paramFloat1 + paramFloat5, paramFloat2 + paramFloat5, paramFloat3 - paramFloat5, paramFloat4 - paramFloat5, paramInt1);
    GL11.glPushMatrix();
    NoCarpet.(paramFloat1 + paramFloat5, paramFloat2, paramFloat3 - paramFloat5, paramFloat2 + paramFloat5, paramInt2);
    NoCarpet.(paramFloat1, paramFloat2, paramFloat1 + paramFloat5, paramFloat4, paramInt2);
    NoCarpet.(paramFloat3 - paramFloat5, paramFloat2, paramFloat3, paramFloat4, paramInt2);
    NoCarpet.(paramFloat1 + paramFloat5, paramFloat4 - paramFloat5, paramFloat3 - paramFloat5, paramFloat4, paramInt2);
    GL11.glPopMatrix();
    CFont.();
  }
  
  public static Rotation (Entity paramEntity, float paramFloat) {
    return CommandHandler.(paramEntity.field_70165_t + AutoHeal.(-paramFloat, paramFloat), paramEntity.field_70163_u + paramEntity.func_70047_e() / 2.0D + AutoHeal.(-paramFloat, paramFloat), paramEntity.field_70161_v + AutoHeal.(-paramFloat, paramFloat));
  }
  
  public Giants() {
    super("Giants", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static <T extends Module> T (Class<T> paramClass) {
    for (Module module : OringoClient.) {
      if (module.getClass().equals(paramClass))
        return (T)module; 
    } 
    throw new IllegalStateException("No module found");
  }
}
