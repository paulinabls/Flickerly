package com.psc.flickerly.domain.utils;

import android.annotation.SuppressLint;

import com.psc.flickerly.domain.model.PhotoInfo;
public class ImageUrlBuilder {

    private static final String PHOTO_URL_FORMAT = "http://farm%d.static.flickr.com/%s/%s_%s.jpg";

    /**
     * follows pattern http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
     */
    @SuppressLint("DefaultLocale")
    public static String buildUrl(PhotoInfo photoInfo) {
        return String.format(PHOTO_URL_FORMAT,
                             photoInfo.getFarm(),
                             photoInfo.getServer(),
                             photoInfo.getId(),
                             photoInfo.getSecret());
    }
}
