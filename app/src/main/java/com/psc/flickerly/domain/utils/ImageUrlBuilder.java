package com.psc.flickerly.domain.utils;

import android.annotation.SuppressLint;

import com.psc.flickerly.Config;
import com.psc.flickerly.domain.model.PhotoInfo;
public class ImageUrlBuilder {

    /**
     * follows pattern http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
     */
    @SuppressLint("DefaultLocale")
    public static String buildUrl(PhotoInfo photoInfo) {
        return String.format(Config.PHOTO_URL_FORMAT,
                             photoInfo.getFarm(),
                             photoInfo.getServer(),
                             photoInfo.getId(),
                             photoInfo.getSecret());
    }
}
