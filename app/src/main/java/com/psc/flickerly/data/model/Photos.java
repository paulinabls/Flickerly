package com.psc.flickerly.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Photos {

    @SerializedName("page")
    public int page;
    @SerializedName("pages")
    private int pages;
    @SerializedName("perpage")
    private int perpage;
    @SerializedName("total")
    public String total;
    @SerializedName("photo")
    public List<Photo> photoList = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Photos() {
    }

    /**
     *
     * @param total
     * @param page
     * @param pages
     * @param photoList
     * @param perpage
     */
    public Photos(int page, int pages, int perpage, String total, List<Photo> photoList) {
        super();
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photoList = photoList;
    }

}