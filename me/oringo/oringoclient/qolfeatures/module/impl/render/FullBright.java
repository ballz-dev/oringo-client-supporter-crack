package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.EXTFramebufferObject;

public class FullBright extends Module {
  public FullBright() {
    super("Full Bright", Module.Category.);
  }
  
  public static void (Framebuffer paramFramebuffer) {
    EXTFramebufferObject.glDeleteRenderbuffersEXT(paramFramebuffer.field_147624_h);
    int i = EXTFramebufferObject.glGenRenderbuffersEXT();
    EXTFramebufferObject.glBindRenderbufferEXT(36161, i);
    EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.func_71410_x()).field_71443_c, (Minecraft.func_71410_x()).field_71440_d);
    EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, i);
    EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, i);
  }
  
  public static void (AxisAlignedBB paramAxisAlignedBB) {
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72339_c).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72337_e, paramAxisAlignedBB.field_72334_f).func_181675_d();
    worldRenderer.func_181662_b(paramAxisAlignedBB.field_72336_d, paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72334_f).func_181675_d();
    tessellator.func_78381_a();
  }
  
  static {
  
  }
  
  public static void (String paramString) {
    System.out.println(paramString);
  }
}
