package com.psc.flickerly.data.mapper;

import com.psc.flickerly.data.model.Photo;
import com.psc.flickerly.data.model.SearchResponse;
import com.psc.flickerly.domain.model.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
public class PhotosMapper {

    public List<Photo> getPhotosList(final SearchResponse response) {
        if (!response.isOk() || response.photos == null || response.photos.photoList == null) {
            return emptyList();
        }

        return response.photos.photoList;
    }

    private PhotoInfo mapSinglePhoto(final Photo photos) {
        return new PhotoInfo(photos.getId(), photos.getSecret(), photos.getServer(), photos.getFarm());
    }

    public List<PhotoInfo> map(final List<Photo> photos) {
        List<PhotoInfo> list = new ArrayList<>(photos.size());
        for (Photo photo : photos) {
            list.add(mapSinglePhoto(photo));
        }
        return list;
    }
}
