package me.oringo.oringoclient.qolfeatures.module.impl.render;

import com.google.common.net.InetAddresses;
import com.google.gson.JsonObject;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import java.time.OffsetDateTime;
import java.util.Random;
import me.oringo.oringoclient.qolfeatures.BackgroundProcess;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class RichPresenceModule extends Module {
  public static RichPresence ;
  
  public static IPCClient  = new IPCClient(929291236450377778L);
  
  public static boolean ;
  
  public RichPresenceModule() {
    super("Discord RPC", 0, Module.Category.);
    (true);
  }
  
  public void () {
    try {
      .sendRichPresence(null);
    } catch (Exception exception) {}
  }
  
  public void () {
    if (!) {
      BackgroundProcess.();
    } else {
      try {
        .sendRichPresence();
      } catch (Exception exception) {}
    } 
    super.();
  }
}
