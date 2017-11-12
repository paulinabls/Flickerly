package com.psc.flickerly.presentation.model;

import com.psc.flickerly.domain.usecase.SearchPhotosUseCase;

public class SearchDataHolder {
    private static final int PAGE_SIZE = 12;

    private String searchText;
    private int currentPageNumber = Integer.MIN_VALUE;
    private boolean noResultReceived;

    public void setNewQuery(final String text) {
        currentPageNumber = 0;
        noResultReceived = false;
        searchText = text;
    }

    public SearchPhotosUseCase.Param getNextQueryParam() {
        if (currentPageNumber == Integer.MIN_VALUE) {
            throw new IllegalStateException("setNewQuery() not called.");
        }
        currentPageNumber++;
        return new SearchPhotosUseCase.Param(PAGE_SIZE, currentPageNumber, searchText);
    }

    public boolean noResultReceived() {
        return noResultReceived;
    }

    public void setNoResultReceived() {
        this.noResultReceived = true;
    }
}
