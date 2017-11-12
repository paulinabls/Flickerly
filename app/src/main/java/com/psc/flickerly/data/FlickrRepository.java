package com.psc.flickerly.data;

import com.psc.flickerly.data.mapper.PhotosMapper;
import com.psc.flickerly.data.network.FlickrApi;
import com.psc.flickerly.domain.Repository;
import com.psc.flickerly.domain.model.PhotoInfo;

import java.util.List;

import io.reactivex.Observable;

public class FlickrRepository implements Repository {
    private static final String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    private static final String JSON = "json";
    private static final String NO_JSON_CALLBACK = "1";
    private final PhotosMapper photosMapper;
    private final SchedulerProvider schedulerProvider;
    private FlickrApi api;

    public FlickrRepository(final FlickrApi api, final PhotosMapper photosMapper, final SchedulerProvider
            schedulerProvider) {
        this.api = api;
        this.photosMapper = photosMapper;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<List<PhotoInfo>> getRecentPhotos(final int pageSize, final int page) {
        return api.getRecentPhotos(pageSize, page, API_KEY, JSON, NO_JSON_CALLBACK)
                  .map(photosMapper::getPhotosList)
                  .map(photosMapper::map)
                  .subscribeOn(schedulerProvider.getIoScheduler())
                  .observeOn(schedulerProvider.getMainScheduler());
    }

    @Override
    public Observable<List<PhotoInfo>> searchPhotos(final int pageSize, final int page, final String text) {
        return api.searchPhotos(pageSize, page, text, API_KEY, JSON, NO_JSON_CALLBACK)
                  .map(photosMapper::getPhotosList)
                  .map(photosMapper::map)
                  .subscribeOn(schedulerProvider.getIoScheduler())
                  .observeOn(schedulerProvider.getMainScheduler());
    }
}
