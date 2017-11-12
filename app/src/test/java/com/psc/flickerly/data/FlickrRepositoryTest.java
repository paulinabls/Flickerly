package com.psc.flickerly.data;

import com.psc.flickerly.data.mapper.PhotosMapper;
import com.psc.flickerly.data.model.SearchResponse;
import com.psc.flickerly.data.network.FlickrApi;
import com.psc.flickerly.domain.model.PhotoInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlickrRepositoryTest {
    @Mock
    FlickrApi api;
    @Mock
    PhotosMapper photosMapper;
    @Mock
    SchedulerProvider schedulerProvider;

    private FlickrRepository tested;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        tested = new FlickrRepository(api, photosMapper, schedulerProvider);
    }

    @Test
    public void searchPhotos_callsMapper_WhenSubscribed() throws Exception {
        final int pageSize = 6;
        final int page = 5;
        final String query = "query";
        String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
        String JSON = "json";
        String NO_JSON_CALLBACK = "1";

        final SearchResponse response = new SearchResponse("ok", 0, "test");
        final Observable<SearchResponse> observable = Observable.just(response);
        when(api.searchPhotos(pageSize, page, query, API_KEY, JSON, NO_JSON_CALLBACK)).thenReturn(observable);
        when(schedulerProvider.getIoScheduler()).thenReturn(Schedulers.trampoline());
        when(schedulerProvider.getMainScheduler()).thenReturn(Schedulers.trampoline());

        final Observable<List<PhotoInfo>> listObservable = tested.searchPhotos(pageSize, page, query);
        listObservable.subscribe();

        verify(api).searchPhotos(pageSize, page, query, API_KEY, JSON, NO_JSON_CALLBACK);
        verify(photosMapper).getPhotosList(any());
        verify(photosMapper).map(any());
        verify(schedulerProvider).getIoScheduler();
        verify(schedulerProvider).getMainScheduler();

    }

}