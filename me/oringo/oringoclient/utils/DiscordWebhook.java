package me.oringo.oringoclient.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

public class DiscordWebhook {
  public String username;
  
  public List<EmbedObject> embeds = new ArrayList<>();
  
  public String url;
  
  public boolean tts;
  
  public String avatarUrl;
  
  public String content;
  
  public void setContent(String paramString) {
    this.content = paramString;
  }
  
  public void execute() throws IOException {
    if (this.content == null && this.embeds.isEmpty())
      throw new IllegalArgumentException("Set content or add at least one EmbedObject"); 
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("content", this.content);
    jSONObject.put("username", this.username);
    jSONObject.put("avatar_url", this.avatarUrl);
    jSONObject.put("tts", Boolean.valueOf(this.tts));
    if (!this.embeds.isEmpty()) {
      ArrayList<JSONObject> arrayList = new ArrayList();
      for (EmbedObject embedObject : this.embeds) {
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("title", embedObject.getTitle());
        jSONObject1.put("description", embedObject.getDescription());
        jSONObject1.put("url", embedObject.getUrl());
        if (embedObject.getColor() != null) {
          Color color = embedObject.getColor();
          int i = color.getRed();
          i = (i << 8) + color.getGreen();
          i = (i << 8) + color.getBlue();
          jSONObject1.put("color", Integer.valueOf(i));
        } 
        EmbedObject.Footer footer = embedObject.getFooter();
        EmbedObject.Image image = embedObject.getImage();
        EmbedObject.Thumbnail thumbnail = embedObject.getThumbnail();
        EmbedObject.Author author = embedObject.getAuthor();
        List<EmbedObject.Field> list = embedObject.getFields();
        if (footer != null) {
          JSONObject jSONObject2 = new JSONObject();
          jSONObject2.put("text", EmbedObject.Footer.(footer));
          jSONObject2.put("icon_url", EmbedObject.Footer.(footer));
          jSONObject1.put("footer", jSONObject2);
        } 
        if (image != null) {
          JSONObject jSONObject2 = new JSONObject();
          jSONObject2.put("url", EmbedObject.Image.(image));
          jSONObject1.put("image", jSONObject2);
        } 
        if (thumbnail != null) {
          JSONObject jSONObject2 = new JSONObject();
          jSONObject2.put("url", EmbedObject.Thumbnail.(thumbnail));
          jSONObject1.put("thumbnail", jSONObject2);
        } 
        if (author != null) {
          JSONObject jSONObject2 = new JSONObject();
          jSONObject2.put("name", EmbedObject.Author.(author));
          jSONObject2.put("url", EmbedObject.Author.(author));
          jSONObject2.put("icon_url", EmbedObject.Author.(author));
          jSONObject1.put("author", jSONObject2);
        } 
        ArrayList<JSONObject> arrayList1 = new ArrayList();
        for (EmbedObject.Field field : list) {
          JSONObject jSONObject2 = new JSONObject();
          jSONObject2.put("name", EmbedObject.Field.(field));
          jSONObject2.put("value", EmbedObject.Field.(field));
          jSONObject2.put("inline", Boolean.valueOf(EmbedObject.Field.(field)));
          arrayList1.add(jSONObject2);
        } 
        jSONObject1.put("fields", arrayList1.toArray());
        arrayList.add(jSONObject1);
      } 
      jSONObject.put("embeds", arrayList.toArray());
    } 
    URL uRL = new URL(this.url);
    HttpsURLConnection httpsURLConnection = (HttpsURLConnection)uRL.openConnection();
    httpsURLConnection.setConnectTimeout(150000);
    httpsURLConnection.addRequestProperty("Content-Type", "application/json");
    httpsURLConnection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
    httpsURLConnection.setDoOutput(true);
    httpsURLConnection.setRequestMethod("POST");
    OutputStream outputStream = httpsURLConnection.getOutputStream();
    outputStream.write(jSONObject.toString().getBytes());
    outputStream.flush();
    outputStream.close();
    httpsURLConnection.getInputStream().close();
    httpsURLConnection.disconnect();
  }
  
  public void setTts(boolean paramBoolean) {
    this.tts = paramBoolean;
  }
  
  public void setAvatarUrl(String paramString) {
    this.avatarUrl = paramString;
  }
  
  public void addEmbed(EmbedObject paramEmbedObject) {
    this.embeds.add(paramEmbedObject);
  }
  
  public void setUsername(String paramString) {
    this.username = paramString;
  }
  
  public DiscordWebhook(String paramString) {
    this.url = paramString;
  }
  
  public class JSONObject {
    public HashMap<String, Object> map = new HashMap<>();
    
    public JSONObject() {}
    
    public String quote(String param1String) {
      return String.valueOf((new StringBuilder()).append("\"").append(param1String).append("\""));
    }
    
    public void put(String param1String, Object param1Object) {
      if (param1Object != null)
        this.map.put(param1String, param1Object); 
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      Set<Map.Entry<String, Object>> set = this.map.entrySet();
      stringBuilder.append("{");
      byte b = 0;
      for (Map.Entry<String, Object> entry : set) {
        Object object = entry.getValue();
        stringBuilder.append(quote((String)entry.getKey())).append(":");
        if (object instanceof String) {
          stringBuilder.append(quote(String.valueOf(object)));
        } else if (object instanceof Integer) {
          stringBuilder.append(Integer.valueOf(String.valueOf(object)));
        } else if (object instanceof Boolean) {
          stringBuilder.append(object);
        } else if (object instanceof JSONObject) {
          stringBuilder.append(object.toString());
        } else if (object.getClass().isArray()) {
          stringBuilder.append("[");
          int i = Array.getLength(object);
          for (byte b1 = 0; b1 < i; b1++)
            stringBuilder.append(Array.get(object, b1).toString()).append((b1 != i - 1) ? "," : ""); 
          stringBuilder.append("]");
        } 
        stringBuilder.append((++b == set.size()) ? "}" : ",");
      } 
      return String.valueOf(stringBuilder);
    }
  }
  
  public static class EmbedObject {
    public Image image;
    
    public Color color;
    
    public List<Field> fields = new ArrayList<>();
    
    public Footer footer;
    
    public Author author;
    
    public String url;
    
    public String title;
    
    public Thumbnail thumbnail;
    
    public String description;
    
    public EmbedObject setImage(String param1String) {
      this.image = new Image(param1String);
      return this;
    }
    
    public EmbedObject setThumbnail(String param1String) {
      this.thumbnail = new Thumbnail(param1String);
      return this;
    }
    
    public EmbedObject setUrl(String param1String) {
      this.url = param1String;
      return this;
    }
    
    public List<Field> getFields() {
      return this.fields;
    }
    
    public Image getImage() {
      return this.image;
    }
    
    public String getDescription() {
      return this.description;
    }
    
    public String getUrl() {
      return this.url;
    }
    
    public EmbedObject setDescription(String param1String) {
      this.description = param1String;
      return this;
    }
    
    public Color getColor() {
      return this.color;
    }
    
    public Thumbnail getThumbnail() {
      return this.thumbnail;
    }
    
    public Author getAuthor() {
      return this.author;
    }
    
    public Footer getFooter() {
      return this.footer;
    }
    
    public EmbedObject setFooter(String param1String1, String param1String2) {
      this.footer = new Footer(param1String1, param1String2);
      return this;
    }
    
    public EmbedObject setTitle(String param1String) {
      this.title = param1String;
      return this;
    }
    
    public EmbedObject setColor(Color param1Color) {
      this.color = param1Color;
      return this;
    }
    
    public String getTitle() {
      return this.title;
    }
    
    public EmbedObject setAuthor(String param1String1, String param1String2, String param1String3) {
      this.author = new Author(param1String1, param1String2, param1String3);
      return this;
    }
    
    public EmbedObject addField(String param1String1, String param1String2, boolean param1Boolean) {
      this.fields.add(new Field(param1String1, param1String2, param1Boolean));
      return this;
    }
    
    public class Field {
      public String ;
      
      public boolean ;
      
      public String ;
      
      public boolean () {
        return this.;
      }
      
      public String () {
        return this.;
      }
      
      public Field(String param2String1, String param2String2, boolean param2Boolean) {
        this. = param2String1;
        this. = param2String2;
        this. = param2Boolean;
      }
      
      public String () {
        return this.;
      }
    }
    
    public class Author {
      public String ;
      
      public String ;
      
      public String ;
      
      public String () {
        return this.;
      }
      
      public String () {
        return this.;
      }
      
      public String () {
        return this.;
      }
      
      public Author(String param2String1, String param2String2, String param2String3) {
        this. = param2String1;
        this. = param2String2;
        this. = param2String3;
      }
    }
    
    public class Image {
      public String ;
      
      public Image(DiscordWebhook.EmbedObject this$0, String param2String) {
        this. = param2String;
      }
      
      public String () {
        return this.;
      }
    }
    
    public class Thumbnail {
      public String ;
      
      public String () {
        return this.;
      }
      
      public Thumbnail(String param2String) {
        this. = param2String;
      }
    }
    
    public class Footer {
      public String ;
      
      public String ;
      
      public String () {
        return this.;
      }
      
      public String () {
        return this.;
      }
      
      public Footer(String param2String1, String param2String2) {
        this. = param2String1;
        this. = param2String2;
      }
    }
  }
  
  public class Field {
    public String ;
    
    public boolean ;
    
    public String ;
    
    public boolean () {
      return this.;
    }
    
    public String () {
      return this.;
    }
    
    public Field(String param1String1, String param1String2, boolean param1Boolean) {
      this. = param1String1;
      this. = param1String2;
      this. = param1Boolean;
    }
    
    public String () {
      return this.;
    }
  }
  
  public class Footer {
    public String ;
    
    public String ;
    
    public String () {
      return this.;
    }
    
    public String () {
      return this.;
    }
    
    public Footer(String param1String1, String param1String2) {
      this. = param1String1;
      this. = param1String2;
    }
  }
  
  public class Image {
    public String ;
    
    public Image(DiscordWebhook this$0, String param1String) {
      this. = param1String;
    }
    
    public String () {
      return this.;
    }
  }
  
  public class Thumbnail {
    public String ;
    
    public String () {
      return this.;
    }
    
    public Thumbnail(String param1String) {
      this. = param1String;
    }
  }
  
  public class Author {
    public String ;
    
    public String ;
    
    public String ;
    
    public String () {
      return this.;
    }
    
    public String () {
      return this.;
    }
    
    public String () {
      return this.;
    }
    
    public Author(String param1String1, String param1String2, String param1String3) {
      this. = param1String1;
      this. = param1String2;
      this. = param1String3;
    }
  }
}
