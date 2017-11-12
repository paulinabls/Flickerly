package com.psc.flickerly.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.psc.flickerly.domain.model.PhotoInfo;
import com.psc.flickerly.domain.usecase.SearchPhotosUseCase;
import com.psc.flickerly.presentation.model.SearchDataHolder;
import com.psc.flickerly.presentation.presenter.base.Presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class GalleryPresenter implements Presenter<GalleryView> {
    private final SearchPhotosUseCase searchPhotosUseCase;
    private GalleryView view;
    private List<PhotoInfo> list = new ArrayList<>();
    private Disposable subscription;
    private SearchDataHolder searchDataHolder;

    public GalleryPresenter(final SearchDataHolder searchDataHolder, SearchPhotosUseCase searchPhotosUseCase) {
        this.searchPhotosUseCase = searchPhotosUseCase;
        this.searchDataHolder = searchDataHolder;
    }

    @Override
    public void onViewAttached(final GalleryView view) {
        this.view = view;
        if (!list.isEmpty()) {
            view.setGalleryData(list);
        }
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroyed() {
        disposeSubscription();
    }

    public void onSearchButtonClicked(final String text) {
        view.showLoadingSpinner();
        view.resetGallery();
        fetchPhotos(text);
    }

    private void fetchPhotos(String text) {
        list.clear();
        searchDataHolder.setNewQuery(text);
        disposeSubscription();
        subscription = searchPhotos();
    }

    private void disposeSubscription() {
        if (subscription != null) {
            subscription.dispose();
            subscription = null;
        }
    }

    @VisibleForTesting
    protected void onPhotosReceived(final List<PhotoInfo> receivedPhotos) {
        disposeSubscription();
        if (receivedPhotos.isEmpty()) {
            searchDataHolder.setNoResultReceived();
            onError("No more photos");
            return;
        }
        this.list.addAll(receivedPhotos);

        if (view == null) {
            return;
        }
        view.addGalleryData(receivedPhotos);
        view.hideLoadingSpinner();
    }

    private void loadMoreItems() {
        if (subscription != null) {
            return;
        }
        view.showLoadingSpinner();

        subscription = searchPhotos();
    }

    @NonNull
    private Disposable searchPhotos() {
        return searchPhotosUseCase
                .execute(searchDataHolder.getNextQueryParam())
                .subscribe(this::onPhotosReceived, error -> onError(error.getMessage()));
    }

    private void onError(final String message) {
        if (view == null) {
            return;
        }
        view.hideLoadingSpinner();
        view.displayErrorMessage(message);
    }

    public void onViewScrolled(final int lastVisibleItemPosition, final int itemCount) {
        if (lastVisibleItemPosition == itemCount - 1 && !searchDataHolder.noResultReceived()) {
            loadMoreItems();
        }
    }
}
