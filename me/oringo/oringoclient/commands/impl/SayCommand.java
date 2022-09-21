package me.oringo.oringoclient.commands.impl;

import java.util.List;
import java.util.stream.Collectors;
import joptsimple.internal.Strings;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoAlign;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class SayCommand extends Command {
  public String () {
    return null;
  }
  
  static {
  
  }
  
  public SayCommand() {
    super("say", new String[0]);
  }
  
  public static void (EntityItemFrame paramEntityItemFrame, int paramInt, EnumFacing paramEnumFacing, List<EntityItemFrame> paramList) {
    BlockPos blockPos = paramEntityItemFrame.func_180425_c();
    AutoAlign..add(blockPos);
    for (EnumFacing enumFacing : new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH }) {
      BlockPos blockPos1 = blockPos.func_177972_a(enumFacing);
      if (!AutoAlign..contains(blockPos1) || paramInt == 8 || paramEnumFacing != null) {
        int i = 1;
        switch (AutoAlign.null.[enumFacing.ordinal()]) {
          case 1:
            i = 3;
            break;
          case 2:
            i = 7;
            break;
          case 3:
            i = 5;
            break;
        } 
        if (AutoAlign.)
          i = (i + 4) % 8; 
        List<EntityItemFrame> list = (List)paramList.stream().filter(blockPos1::).collect(Collectors.toList());
        if (list.size() == 1) {
          if (paramEntityItemFrame.func_82335_i().func_77973_b() == Items.field_151032_g) {
            AutoAlign..removeIf(paramEntityItemFrame::);
            AutoAlign..add(new AutoAlign.MoveData(paramEntityItemFrame, AutoAlign. ? paramInt : i));
          } 
          if (((EntityItemFrame)list.get(0)).func_82335_i().func_77973_b() == Items.field_151032_g && (paramEnumFacing == null || paramEnumFacing == enumFacing))
            (list.get(0), i, (AutoAlign..contains(blockPos1) && paramInt == 8) ? enumFacing : paramEnumFacing, paramList); 
        } 
      } 
    } 
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    mc.func_147114_u().func_147297_a((Packet)new C01PacketChatMessage(Strings.join(paramArrayOfString, " ")));
  }
}
