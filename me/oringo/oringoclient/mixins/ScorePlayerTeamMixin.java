package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ScorePlayerTeam.class})
public class ScorePlayerTeamMixin {
  @Inject(method = {"formatPlayerName"}, at = {@At("RETURN")}, cancellable = true)
  private static void formatPlayerName(Team paramTeam, String paramString, CallbackInfoReturnable<String> paramCallbackInfoReturnable) {
    PVPInfo pVPInfo = OringoClient.;
    if (pVPInfo.()) {
      PVPInfo.PlayerInfo playerInfo = pVPInfo.(paramString);
      if (SkyblockUtils.) {
        if (playerInfo != null) {
          if (pVPInfo..())
            paramCallbackInfoReturnable.setReturnValue((String)paramCallbackInfoReturnable.getReturnValue() + " " + (pVPInfo. ? playerInfo..getHead() : pVPInfo.(playerInfo..levelFormatted))); 
          if (pVPInfo..())
            paramCallbackInfoReturnable.setReturnValue((String)paramCallbackInfoReturnable.getReturnValue() + " §7WLR: §f" + playerInfo..getWLR()); 
        } 
      } else if (SkyblockUtils.) {
        if (pVPInfo..containsKey(paramString))
          paramCallbackInfoReturnable.setReturnValue(pVPInfo..get(paramString)); 
      } else if (SkyblockUtils. && 
        playerInfo != null) {
        if (pVPInfo..())
          paramCallbackInfoReturnable.setReturnValue((String)paramCallbackInfoReturnable.getReturnValue() + " " + pVPInfo.(playerInfo..)); 
        if (pVPInfo..())
          paramCallbackInfoReturnable.setReturnValue((String)paramCallbackInfoReturnable.getReturnValue() + " §7WLR: §f" + playerInfo..); 
        if (pVPInfo..())
          paramCallbackInfoReturnable.setReturnValue((String)paramCallbackInfoReturnable.getReturnValue() + " §7FKDR: §f" + playerInfo..); 
        if (pVPInfo..())
          paramCallbackInfoReturnable.setReturnValue((String)paramCallbackInfoReturnable.getReturnValue() + " §7Streak: §f" + playerInfo..); 
      } 
    } 
  }
}
