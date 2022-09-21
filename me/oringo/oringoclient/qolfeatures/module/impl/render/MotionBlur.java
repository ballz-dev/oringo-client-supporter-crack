package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.oringo.oringoclient.mixins.ResourceManagerAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MotionBlur extends Module {
  public Map<String, FallbackResourceManager>  = null;
  
  public static NumberSetting  = new NumberSetting("Blur amount", 0.2D, 0.1D, 0.9D, 0.01D);
  
  public static double  = -1.0D;
  
  public MotionBlur() {
    super("Motion Blur", Module.Category.);
    (new Setting[] { (Setting) });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (() &&  != .())
      (); 
    if (this. == null)
      this. = ((ResourceManagerAccessor)Minecraft.func_71410_x().func_110442_L()).getDomainResourceManagers(); 
    if (!this..containsKey("motionblur"))
      this..put("motionblur", new FallbackResourceManager(this, new IMetadataSerializer()) {
            public List<IResource> func_135056_b(ResourceLocation param1ResourceLocation) throws IOException {
              return null;
            }
            
            public Set<String> func_135055_a() {
              return null;
            }
            
            public IResource func_110536_a(ResourceLocation param1ResourceLocation) throws IOException {
              return new MotionBlur.MotionBlurShader();
            }
          }); 
  }
  
  public void () {
    ();
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    ();
  }
  
  public void () {
    if (mc.field_71460_t == null)
      return; 
    if (()) {
      Scaffold.();
    } else {
      mc.field_71460_t.func_181022_b();
       = -1.0D;
    } 
  }
  
  public static class MotionBlurShader implements IResource {
    public boolean func_110528_c() {
      return false;
    }
    
    public ResourceLocation func_177241_a() {
      return null;
    }
    
    public String func_177240_d() {
      return null;
    }
    
    public InputStream func_110527_b() {
      double d = 1.0D - MotionBlur.().();
      return new ByteArrayInputStream("{\n  \"targets\": [\n    \"swap\",\n    \"previous\"\n  ],\n  \"passes\": [\n    {\n      \"name\": \"motionblur\",\n      \"intarget\": \"minecraft:main\",\n      \"outtarget\": \"swap\",\n      \"auxtargets\": [\n        {\n          \"name\": \"PrevSampler\",\n          \"id\": \"previous\"\n        }\n      ],\n      \"uniforms\": [\n        {\n          \"name\": \"Phosphor\",\n          \"values\": [ blur_amount, blur_amount, blur_amount ]\n        }\n      ]\n    },\n    {\n      \"name\": \"blit\",\n      \"intarget\": \"swap\",\n      \"outtarget\": \"previous\"\n    },\n    {\n      \"name\": \"blit\",\n      \"intarget\": \"swap\",\n      \"outtarget\": \"minecraft:main\"\n    }\n  ]\n}\n".replaceAll("blur_amount", String.valueOf((d == 1.0D) ? 0.99D : d)).getBytes(StandardCharsets.UTF_8));
    }
    
    public <T extends net.minecraft.client.resources.data.IMetadataSection> T func_110526_a(String param1String) {
      return null;
    }
    
    static {
    
    }
  }
}
