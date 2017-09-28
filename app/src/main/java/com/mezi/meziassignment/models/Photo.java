
package com.mezi.meziassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private long farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private long ispublic;
    @SerializedName("isfriend")
    @Expose
    private long isfriend;
    @SerializedName("isfamily")
    @Expose
    private long isfamily;
    @SerializedName("url_n")
    @Expose
    private String urlN;
    @SerializedName("height_n")
    @Expose
    private long heightN;
    @SerializedName("width_n")
    @Expose
    private long widthN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public long getFarm() {
        return farm;
    }

    public void setFarm(long farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIspublic() {
        return ispublic;
    }

    public void setIspublic(long ispublic) {
        this.ispublic = ispublic;
    }

    public long getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(long isfriend) {
        this.isfriend = isfriend;
    }

    public long getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(long isfamily) {
        this.isfamily = isfamily;
    }

    public String getUrlN() {
        return urlN;
    }

    public void setUrlN(String urlN) {
        this.urlN = urlN;
    }

    public long getHeightN() {
        return heightN;
    }

    public void setHeightN(long heightN) {
        this.heightN = heightN;
    }

    public long getWidthN() {
        return widthN;
    }

    public void setWidthN(long widthN) {
        this.widthN = widthN;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Photo) {
            Photo newPhoto = (Photo) obj;
            return this.getId().equals(newPhoto.getId());
        }
        return false;
    }
}
