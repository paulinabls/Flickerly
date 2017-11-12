package com.psc.flickerly.data.model;

import com.google.gson.annotations.SerializedName;
public class Photo {

    @SerializedName("id")
    private String id;
    @SerializedName("owner")
    private String owner;
    @SerializedName("secret")
    private String secret;
    @SerializedName("server")
    private String server;
    @SerializedName("farm")
    private int farm;
    @SerializedName("title")
    private String title;
    @SerializedName("ispublic")
    private int isPublic;
    @SerializedName("isfriend")
    private int isFriend;
    @SerializedName("isfamily")
    private int isFamily;

    public Photo(final String id, final String secret, final String server, final int farm) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
    }

    public Photo() {
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Photo{" +
               "id='" + id + '\'' +
               ", owner='" + owner + '\'' +
               ", secret='" + secret + '\'' +
               ", server='" + server + '\'' +
               ", farm=" + farm +
               ", title='" + title + '\'' +
               ", isPublic=" + isPublic +
               ", isFriend=" + isFriend +
               ", isFamily=" + isFamily +
               '}';
    }
}
