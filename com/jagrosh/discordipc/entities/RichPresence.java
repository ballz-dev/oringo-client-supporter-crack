package com.jagrosh.discordipc.entities;

import java.time.OffsetDateTime;
import org.json.JSONArray;
import org.json.JSONObject;

public class RichPresence {
  private final OffsetDateTime startTimestamp;
  
  private final String details;
  
  private final String smallImageKey;
  
  private final boolean instance;
  
  private final String spectateSecret;
  
  private final int partyMax;
  
  private final OffsetDateTime endTimestamp;
  
  private final String joinSecret;
  
  private final String largeImageText;
  
  private final String state;
  
  private final String partyId;
  
  private final String matchSecret;
  
  private final int partySize;
  
  private final String smallImageText;
  
  private final String largeImageKey;
  
  public JSONObject toJson() {
    return (new JSONObject()).put("state", this.state).put("details", this.details).put("timestamps", (new JSONObject()).put("start", (this.startTimestamp == null) ? null : Long.valueOf(this.startTimestamp.toEpochSecond())).put("end", (this.endTimestamp == null) ? null : Long.valueOf(this.endTimestamp.toEpochSecond()))).put("assets", (new JSONObject()).put("large_image", this.largeImageKey).put("large_text", this.largeImageText).put("small_image", this.smallImageKey).put("small_text", this.smallImageText)).put("party", (this.partyId == null) ? null : (new JSONObject()).put("id", this.partyId).put("size", (new JSONArray()).put(this.partySize).put(this.partyMax))).put("secrets", (new JSONObject()).put("join", this.joinSecret).put("spectate", this.spectateSecret).put("match", this.matchSecret)).put("instance", this.instance);
  }
  
  public RichPresence(String paramString1, String paramString2, OffsetDateTime paramOffsetDateTime1, OffsetDateTime paramOffsetDateTime2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt1, int paramInt2, String paramString8, String paramString9, String paramString10, boolean paramBoolean) {
    this.state = paramString1;
    this.details = paramString2;
    this.startTimestamp = paramOffsetDateTime1;
    this.endTimestamp = paramOffsetDateTime2;
    this.largeImageKey = paramString3;
    this.largeImageText = paramString4;
    this.smallImageKey = paramString5;
    this.smallImageText = paramString6;
    this.partyId = paramString7;
    this.partySize = paramInt1;
    this.partyMax = paramInt2;
    this.matchSecret = paramString8;
    this.joinSecret = paramString9;
    this.spectateSecret = paramString10;
    this.instance = paramBoolean;
  }
  
  public static class Builder {
    private String matchSecret;
    
    private String spectateSecret;
    
    private String smallImageText;
    
    private String smallImageKey;
    
    private String joinSecret;
    
    private boolean instance;
    
    private String largeImageKey;
    
    private String partyId;
    
    private int partyMax;
    
    private OffsetDateTime endTimestamp;
    
    private OffsetDateTime startTimestamp;
    
    private int partySize;
    
    private String details;
    
    private String largeImageText;
    
    private String state;
    
    public RichPresence build() {
      return new RichPresence(this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance);
    }
    
    public Builder setSmallImage(String param1String1, String param1String2) {
      this.smallImageKey = param1String1;
      this.smallImageText = param1String2;
      return this;
    }
    
    public Builder setParty(String param1String, int param1Int1, int param1Int2) {
      this.partyId = param1String;
      this.partySize = param1Int1;
      this.partyMax = param1Int2;
      return this;
    }
    
    public Builder setStartTimestamp(OffsetDateTime param1OffsetDateTime) {
      this.startTimestamp = param1OffsetDateTime;
      return this;
    }
    
    public Builder setJoinSecret(String param1String) {
      this.joinSecret = param1String;
      return this;
    }
    
    public Builder setInstance(boolean param1Boolean) {
      this.instance = param1Boolean;
      return this;
    }
    
    public Builder setSpectateSecret(String param1String) {
      this.spectateSecret = param1String;
      return this;
    }
    
    public Builder setLargeImage(String param1String1, String param1String2) {
      this.largeImageKey = param1String1;
      this.largeImageText = param1String2;
      return this;
    }
    
    public Builder setLargeImage(String param1String) {
      return setLargeImage(param1String, null);
    }
    
    public Builder setSmallImage(String param1String) {
      return setSmallImage(param1String, null);
    }
    
    public Builder setState(String param1String) {
      this.state = param1String;
      return this;
    }
    
    public Builder setDetails(String param1String) {
      this.details = param1String;
      return this;
    }
    
    public Builder setMatchSecret(String param1String) {
      this.matchSecret = param1String;
      return this;
    }
    
    public Builder setEndTimestamp(OffsetDateTime param1OffsetDateTime) {
      this.endTimestamp = param1OffsetDateTime;
      return this;
    }
  }
}
