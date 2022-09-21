package me.oringo.oringoclient.commands.impl;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.MoveHeadingEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class TestCommand extends Command {
  public TestCommand() {
    super("test", new String[0]);
  }
  
  public String () {
    return null;
  }
  
  static {
  
  }
  
  public static void () {
    if (XRay.mc.field_71438_f != null) {
      XRay.mc.field_71438_f.func_72712_a();
      XRay. = XRay..();
    } 
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    Objects.requireNonNull(mc.field_71439_g);
    Optional<EntityItemFrame> optional = mc.field_71441_e.field_72996_f.stream().filter(TestCommand::).min(Comparator.comparingDouble(mc.field_71439_g::func_70068_e));
    if (optional.isPresent()) {
      EntityItemFrame entityItemFrame = optional.get();
      mc.func_147114_u().func_147297_a((Packet)new C02PacketUseEntity((Entity)entityItemFrame, C02PacketUseEntity.Action.INTERACT));
      MoveHeadingEvent.(String.valueOf((new StringBuilder()).append("Dist: ").append(mc.field_71439_g.func_70032_d((Entity)entityItemFrame)).append(" Block Pos: ").append(mc.field_71439_g.func_70011_f(entityItemFrame.func_180425_c().func_177958_n(), entityItemFrame.func_180425_c().func_177956_o(), entityItemFrame.func_180425_c().func_177952_p())).append(" To center: ").append(Math.sqrt(mc.field_71439_g.func_174831_c(entityItemFrame.func_180425_c()))).append(" To head: ").append(mc.field_71439_g.func_70011_f(entityItemFrame.field_70165_t, entityItemFrame.field_70163_u - mc.field_71439_g.func_70047_e(), entityItemFrame.field_70161_v))));
    } 
  }
}
