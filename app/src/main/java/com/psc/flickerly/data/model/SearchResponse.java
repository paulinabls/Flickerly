package com.psc.flickerly.data.model;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @SerializedName("stat")
    private String stat;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    @SerializedName("photos")
    public Photos photos;
    /**
     * No args constructor for use in serialization
     */
    public SearchResponse() {
    }

    /**
     * @param message
     * @param code
     * @param stat
     */
    public SearchResponse(String stat, int code, String message) {
        super();
        this.stat = stat;
        this.code = code;
        this.message = message;
    }

    public String getStat() {
        return stat;
    }

    public boolean isOk() {
        return "ok".equalsIgnoreCase(stat);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}