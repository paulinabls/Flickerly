package com.psc.flickerly.domain.usecase;

import com.psc.flickerly.domain.Repository;
import com.psc.flickerly.domain.model.PhotoInfo;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;

public class SearchPhotosUseCase implements UseCase<SearchPhotosUseCase.Param, Observable<List<PhotoInfo>>> {
    private final Repository repository;

    public SearchPhotosUseCase(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<PhotoInfo>> execute(final Param param) {
        return repository.searchPhotos(param.pageSize,
                                       param.pageNumber,
                                       param.text);

    }

    public static class Param {
        int pageSize;
        int pageNumber;
        String text;

        public Param(final int pageSize, final int pageNumber, final String text) {
            this.pageSize = pageSize;
            this.pageNumber = pageNumber;
            this.text = text;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Param param = (Param) o;
            return pageSize == param.pageSize &&
                   pageNumber == param.pageNumber &&
                   Objects.equals(text, param.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pageSize, pageNumber, text);
        }
    }

}
