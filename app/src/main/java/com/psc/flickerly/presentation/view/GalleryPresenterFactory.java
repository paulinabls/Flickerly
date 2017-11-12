package com.psc.flickerly.presentation.view;

import com.psc.flickerly.data.FlickrRepository;
import com.psc.flickerly.data.SchedulerProvider;
import com.psc.flickerly.data.mapper.PhotosMapper;
import com.psc.flickerly.data.network.FlickrApi;
import com.psc.flickerly.data.network.HttpClientProvider;
import com.psc.flickerly.data.network.ServiceProvider;
import com.psc.flickerly.domain.Repository;
import com.psc.flickerly.domain.usecase.SearchPhotosUseCase;
import com.psc.flickerly.presentation.model.SearchDataHolder;
import com.psc.flickerly.presentation.presenter.GalleryPresenter;
import com.psc.flickerly.presentation.presenter.base.PresenterFactory;
class GalleryPresenterFactory implements PresenterFactory<GalleryPresenter> {

    @Override
    public GalleryPresenter create() {
        final FlickrApi flickrService = ServiceProvider.createRestService(new HttpClientProvider().create());
        final Repository repository = new FlickrRepository(flickrService, new PhotosMapper(), new SchedulerProvider());

        return new GalleryPresenter(new SearchDataHolder(), new SearchPhotosUseCase(repository));
    }
}
