package me.oringo.oringoclient.commands.impl;

import java.util.Comparator;
import java.util.Objects;
import javax.swing.JTextArea;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.mixins.packet.C02Accessor;
import me.oringo.oringoclient.utils.OringoPacketLog;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class ArmorStandsCommand extends Command {
  public static float (double paramDouble1, double paramDouble2) {
    return (float)Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
  }
  
  public String () {
    return "Shows you a list of loaded armor stands.";
  }
  
  public ArmorStandsCommand() {
    super("armorstands", new String[0]);
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 1) {
      C02PacketUseEntity c02PacketUseEntity = new C02PacketUseEntity();
      ((C02Accessor)c02PacketUseEntity).setEntityId(Integer.parseInt(paramArrayOfString[0]));
      ((C02Accessor)c02PacketUseEntity).setAction(C02PacketUseEntity.Action.INTERACT);
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)c02PacketUseEntity);
      return;
    } 
    Objects.requireNonNull(mc.field_71439_g);
    mc.field_71441_e.field_72996_f.stream().filter(ArmorStandsCommand::).sorted(Comparator.comparingDouble(mc.field_71439_g::func_70032_d).reversed()).forEach(JerryBoxCommand::);
  }
  
  static {
  
  }
  
  public static JTextArea () {
    return OringoPacketLog.;
  }
}
