package com.jagrosh.discordipc.entities;

public enum DiscordBuild {
  PTB,
  CANARY("//canary.discordapp.com/api"),
  STABLE("//canary.discordapp.com/api"),
  ANY("//canary.discordapp.com/api");
  
  private final String endpoint;
  
  DiscordBuild(String paramString1) {
    this.endpoint = paramString1;
  }
  
  static {
    PTB = new DiscordBuild("PTB", 1, "//ptb.discordapp.com/api");
    STABLE = new DiscordBuild("STABLE", 2, "//discordapp.com/api");
    ANY = new DiscordBuild("ANY", 3);
    $VALUES = new DiscordBuild[] { CANARY, PTB, STABLE, ANY };
  }
  
  public static DiscordBuild from(String paramString) {
    for (DiscordBuild discordBuild : values()) {
      if (discordBuild.endpoint != null && discordBuild.endpoint.equals(paramString))
        return discordBuild; 
    } 
    return ANY;
  }
}
