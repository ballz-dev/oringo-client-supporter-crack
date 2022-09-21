package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.SumoFences;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomESP extends Module {
  public ModeSetting  = new ModeSetting("Mode", "2D", new String[] { "2D", "Box", "Tracers" });
  
  public static Map<String, Color>  = new HashMap<>();
  
  public static void (int paramInt, Rotation paramRotation) {
    MovingObjectPosition movingObjectPosition = CommandHandler.(paramRotation.(), paramRotation.());
    if (movingObjectPosition != null) {
      Vec3 vec3 = movingObjectPosition.field_72307_f;
      BlockPos blockPos = movingObjectPosition.func_178782_a();
      float f1 = (float)(vec3.field_72450_a - blockPos.func_177958_n());
      float f2 = (float)(vec3.field_72448_b - blockPos.func_177956_o());
      float f3 = (float)(vec3.field_72449_c - blockPos.func_177952_p());
      Scaffold.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(movingObjectPosition.func_178782_a(), movingObjectPosition.field_178784_b.func_176745_a(), Scaffold.mc.field_71439_g.func_70694_bm(), f1, f2, f3));
      Scaffold.mc.field_71439_g.func_71038_i();
      if (Scaffold.mc.field_71439_g.field_71071_by.func_70301_a(paramInt) != null)
        Scaffold.mc.field_71439_g.field_71071_by.func_70301_a(paramInt).func_179546_a((EntityPlayer)Scaffold.mc.field_71439_g, (World)Scaffold.mc.field_71441_e, blockPos, movingObjectPosition.field_178784_b, f1, f2, f3); 
    } 
  }
  
  public static int () {
    NetworkPlayerInfo networkPlayerInfo = SkyblockUtils.mc.func_147114_u().func_175102_a((Minecraft.func_71410_x()).field_71439_g.func_110124_au());
    return (networkPlayerInfo == null) ? 0 : networkPlayerInfo.func_178853_c();
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (!())
      return; 
    for (Entity entity : mc.field_71441_e.func_175644_a(EntityArmorStand.class, CustomESP::)) {
      for (Map.Entry<String, Color> entry : .entrySet()) {
        if (entity.func_145748_c_().func_150260_c().toLowerCase().contains((CharSequence)entry.getKey())) {
          List<Entity> list = mc.field_71441_e.func_72839_b(entity, entity.func_174813_aQ().func_72314_b(0.0D, 2.0D, 0.0D));
          if (!list.isEmpty()) {
            Color color = (Color)entry.getValue();
            Entity entity1 = list.get(0);
            switch (this..()) {
              case "2D":
                SumoFences.(entity1, paramRenderWorldLastEvent.partialTicks, 1.0F, color);
              case "Box":
                CommandHandler.(entity1, paramRenderWorldLastEvent.partialTicks, color);
              case "Tracers":
                SimulatorAura.(entity1, paramRenderWorldLastEvent.partialTicks, 1.0F, color);
            } 
          } 
        } 
      } 
    } 
  }
  
  public CustomESP() {
    super("Custom ESP", Module.Category.);
    (new Setting[] { (Setting)this. });
  }
  
  public static String (Packet<?> paramPacket) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Field field : paramPacket.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (stringBuilder.length() != 0)
          stringBuilder.append(", "); 
        stringBuilder.append(field.getName()).append(":").append(field.get(paramPacket));
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      } 
    } 
    return String.valueOf((new StringBuilder()).append(paramPacket.getClass().getSimpleName()).append(String.format("{%s}", new Object[] { stringBuilder })));
  }
}
