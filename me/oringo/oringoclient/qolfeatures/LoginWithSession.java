package me.oringo.oringoclient.qolfeatures;

import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import javax.swing.JOptionPane;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Breaker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.RemoveAnnoyingMobs;
import me.oringo.oringoclient.utils.EntityUtils;
import me.oringo.oringoclient.utils.api.HypixelAPI;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Session;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LoginWithSession {
  public Session  = null;
  
  @SubscribeEvent
  public void (GuiScreenEvent.InitGuiEvent.Post paramPost) {
    if (OringoClient. && paramPost.gui instanceof net.minecraft.client.gui.GuiMainMenu) {
      paramPost.buttonList.add(new GuiButton(2137, 5, 5, 100, 20, "Login"));
      paramPost.buttonList.add(new GuiButton(21370, 115, 5, 100, 20, "Save"));
    } 
  }
  
  @SubscribeEvent
  public void (GuiScreenEvent.ActionPerformedEvent.Post paramPost) {
    if (paramPost.gui instanceof net.minecraft.client.gui.GuiMainMenu) {
      if (paramPost.button.field_146127_k == 2137) {
        if (this. == null)
          this. = Minecraft.func_71410_x().func_110432_I(); 
        String str = JOptionPane.showInputDialog("Login");
        if (str == null || str.isEmpty())
          return; 
        if (str.equalsIgnoreCase("reset")) {
          try {
            Field field = Minecraft.class.getDeclaredField("field_71449_j");
            field.setAccessible(true);
            field.set(Minecraft.func_71410_x(), this.);
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          return;
        } 
        try {
          Field field = Minecraft.class.getDeclaredField("field_71449_j");
          field.setAccessible(true);
          String str1 = (new JsonParser()).parse(new InputStreamReader((new URL(String.valueOf((new StringBuilder()).append("https://sessionserver.mojang.com/session/minecraft/profile/").append(str.split(": ")[1])))).openStream())).getAsJsonObject().get("name").getAsString();
          field.set(Minecraft.func_71410_x(), new Session(str1, str.split(": ")[1], str.split(": ")[0], "mojang"));
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } 
      if (paramPost.button.field_146127_k == 21370)
        try {
          BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("savedData")));
          bufferedWriter.write(String.valueOf((new StringBuilder()).append(Minecraft.func_71410_x().func_110432_I().func_148254_d()).append(": ").append(Minecraft.func_71410_x().func_110432_I().func_148255_b())));
          bufferedWriter.close();
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
  }
  
  public static void () {
    Breaker..clear();
    BlockPos blockPos = new BlockPos(Breaker.mc.field_71439_g.func_174824_e(1.0F));
    int i = (int)Math.ceil(Breaker..());
    for (BlockPos blockPos1 : BlockPos.func_177980_a(blockPos.func_177982_a(-i, -i, -i), blockPos.func_177982_a(i, i, i))) {
      Block block = Breaker.mc.field_71441_e.func_180495_p(blockPos1).func_177230_c();
      if (Breaker.mc.field_71439_g.func_70011_f(blockPos1.func_177958_n(), (blockPos1.func_177956_o() - Breaker.mc.field_71439_g.func_70047_e()), blockPos1.func_177952_p()) <= Breaker..() && ((block == Blocks.field_150324_C && Breaker..("Bed")) || (block == Blocks.field_150414_aQ && Breaker..("Cake")))) {
        Breaker..add(blockPos1);
        if (Breaker..size() >= Breaker..() || Breaker..("Bed"))
          return; 
      } 
    } 
  }
  
  public static boolean (EntityLivingBase paramEntityLivingBase) {
    if (paramEntityLivingBase == KillAura.mc.field_71439_g || !ArmorSwap.((Entity)paramEntityLivingBase) || (!KillAura..() && paramEntityLivingBase.func_82150_aj()) || paramEntityLivingBase instanceof net.minecraft.entity.item.EntityArmorStand || (!KillAura.mc.field_71439_g.func_70685_l((Entity)paramEntityLivingBase) && !KillAura..()) || paramEntityLivingBase.func_110143_aJ() <= 0.0F || paramEntityLivingBase.func_70032_d((Entity)KillAura.mc.field_71439_g) > 15.0F || !RemoveAnnoyingMobs.(HClip.((Entity)paramEntityLivingBase), (float)KillAura..()))
      return false; 
    if (HypixelAPI.((Entity)paramEntityLivingBase) > ((KillAura. != null && KillAura. != paramEntityLivingBase) ? KillAura..() : Math.max(KillAura..(), KillAura..())))
      return false; 
    if (AntiVoid.().()) {
      boolean bool = HClip.((Entity)paramEntityLivingBase);
      if (NamesOnly..("Enemies") || bool)
        return (NamesOnly..("Enemies") && bool); 
    } 
    return ((paramEntityLivingBase instanceof net.minecraft.entity.monster.EntityMob || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityVillager || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntitySnowman || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityAmbientCreature || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityWaterMob || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityAnimal || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntitySlime) && !KillAura..()) ? false : (!(paramEntityLivingBase instanceof net.minecraft.entity.player.EntityPlayer && ((EntityUtils.isTeam((Entity)paramEntityLivingBase) && KillAura..()) || !KillAura..())));
  }
}
