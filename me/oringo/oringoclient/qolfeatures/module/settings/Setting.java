package me.oringo.oringoclient.qolfeatures.module.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mojang.authlib.GameProfile;
import java.util.concurrent.atomic.AtomicReference;
import me.oringo.oringoclient.utils.Returnable;

public class Setting {
  @Expose
  @SerializedName("name")
  public String ;
  
  public Returnable<Boolean> ;
  
  public void (Returnable<Boolean> paramReturnable) {
    this. = paramReturnable;
  }
  
  public static String (GameProfile paramGameProfile) {
    AtomicReference<String> atomicReference = new AtomicReference<>("");
    paramGameProfile.getProperties().entries().forEach(atomicReference::);
    return atomicReference.get();
  }
  
  public boolean () {
    return (this. != null && ((Boolean)this..get()).booleanValue());
  }
  
  public Setting(String paramString, Returnable<Boolean> paramReturnable) {
    this. = paramString;
    this. = paramReturnable;
  }
  
  public Setting(String paramString) {
    this(paramString, null);
  }
  
  public boolean (Object paramObject) {
    return (this == paramObject) ? true : ((paramObject == null || getClass() != paramObject.getClass()) ? false : this..equals(((Setting)paramObject).));
  }
}
