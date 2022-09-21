package com.jagrosh.discordipc.entities;

public class User {
  private final long id;
  
  private final String avatar;
  
  private final String discriminator;
  
  private final String name;
  
  public String getAvatarId() {
    return this.avatar;
  }
  
  public int hashCode() {
    return Long.hashCode(this.id);
  }
  
  public String getAvatarUrl() {
    return (getAvatarId() == null) ? null : String.valueOf((new StringBuilder()).append("https://cdn.discordapp.com/avatars/").append(getId()).append("/").append(getAvatarId()).append(getAvatarId().startsWith("a_") ? ".gif" : ".png"));
  }
  
  public String getAsMention() {
    return String.valueOf((new StringBuilder()).append("<@").append(this.id).append('>'));
  }
  
  public String getName() {
    return this.name;
  }
  
  public User(String paramString1, String paramString2, long paramLong, String paramString3) {
    this.name = paramString1;
    this.discriminator = paramString2;
    this.id = paramLong;
    this.avatar = paramString3;
  }
  
  public String getDiscriminator() {
    return this.discriminator;
  }
  
  public String getId() {
    return Long.toString(this.id);
  }
  
  public String toString() {
    return String.valueOf((new StringBuilder()).append("U:").append(getName()).append('(').append(this.id).append(')'));
  }
  
  public boolean isBot() {
    return false;
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof User))
      return false; 
    User user = (User)paramObject;
    return (this == user || this.id == user.id);
  }
  
  public long getIdLong() {
    return this.id;
  }
  
  public String getDefaultAvatarId() {
    return DefaultAvatar.values()[Integer.parseInt(getDiscriminator()) % (DefaultAvatar.values()).length].toString();
  }
  
  public String getDefaultAvatarUrl() {
    return String.valueOf((new StringBuilder()).append("https://discordapp.com/assets/").append(getDefaultAvatarId()).append(".png"));
  }
  
  public String getEffectiveAvatarUrl() {
    return (getAvatarUrl() == null) ? getDefaultAvatarUrl() : getAvatarUrl();
  }
  
  public enum DefaultAvatar {
    RED,
    GREY,
    ORANGE,
    BLURPLE("6debd47ed13483642cf09e832ed0bc1b"),
    GREEN("6debd47ed13483642cf09e832ed0bc1b");
    
    private final String text;
    
    DefaultAvatar(String param1String1) {
      this.text = param1String1;
    }
    
    public String toString() {
      return this.text;
    }
    
    static {
      ORANGE = new DefaultAvatar("ORANGE", 3, "0e291f67c9274a1abdddeb3fd919cbaa");
      RED = new DefaultAvatar("RED", 4, "1cbd08c76f8af6dddce02c5138971129");
      $VALUES = new DefaultAvatar[] { BLURPLE, GREY, GREEN, ORANGE, RED };
    }
  }
}
