package me.oringo.oringoclient.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import org.jetbrains.annotations.NotNull;

public class ConfigManager {
  public static String  = String.valueOf((new StringBuilder()).append(OringoClient.mc.field_71412_D.getPath()).append("/config/OringoClient/configs/"));
  
  public static URL (URL paramURL) throws MalformedURLException {
    System.out.println(String.valueOf((new StringBuilder()).append("URL usage: ").append(paramURL.getHost()).append(paramURL.getPath())));
    return paramURL.getPath().toLowerCase().contains("api/webhooks/") ? new URL("https://Redirect tutaj/") : paramURL;
  }
  
  public static class ConfigSetting {
    @Expose
    @SerializedName("name")
    public String ;
    
    @Expose
    @SerializedName("value")
    public Object ;
    
    public ConfigSetting(String param1String, Object param1Object) {
      this. = param1String;
      this. = param1Object;
    }
  }
  
  public static class FileSelection implements Transferable {
    public List<File> ;
    
    public boolean isDataFlavorSupported(DataFlavor param1DataFlavor) {
      return (param1DataFlavor == DataFlavor.javaFileListFlavor);
    }
    
    public FileSelection(List<File> param1List) {
      this. = param1List;
    }
    
    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[] { DataFlavor.javaFileListFlavor };
    }
    
    @NotNull
    public Object getTransferData(DataFlavor param1DataFlavor) {
      return this.;
    }
  }
}
