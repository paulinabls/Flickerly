package com.psc.flickerly.domain;

import com.psc.flickerly.domain.model.PhotoInfo;

import java.util.List;

import io.reactivex.Observable;
public interface Repository {
    Observable<List<PhotoInfo>> getRecentPhotos(int pageSize, int page);
    Observable<List<PhotoInfo>> searchPhotos(int pageSize, int page, String text);

}
