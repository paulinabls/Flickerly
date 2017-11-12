package com.psc.flickerly.presentation.presenter;

import com.psc.flickerly.domain.model.PhotoInfo;

import java.util.List;

public interface GalleryView {
    void setGalleryData(List<PhotoInfo> list);

    void resetGallery();

    void addGalleryData(List<PhotoInfo> listToAppend);

    void showLoadingSpinner();

    void hideLoadingSpinner();

    void displayErrorMessage(String message);
}
