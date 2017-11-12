package com.psc.flickerly.data.network;

import com.psc.flickerly.data.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("/services/rest/?method=flickr.photos.getRecent")
    Observable<SearchResponse> getRecentPhotos(@Query("per_page") int perPage,
                                               @Query("page") int page,
                                               @Query("api_key") String apiKey,
                                               @Query("format") String format,
                                               @Query("nojsoncallback") String noJsonCallback);

    @GET("/services/rest/?method=flickr.photos.search")
    Observable<SearchResponse> searchPhotos(@Query("per_page") int perPage,
                                            @Query("page") int page,
                                            @Query("text") String searchText,
                                            @Query("api_key") String apiKey,
                                            @Query("format") String format,
                                            @Query("nojsoncallback") String noJsonCallback);

}
