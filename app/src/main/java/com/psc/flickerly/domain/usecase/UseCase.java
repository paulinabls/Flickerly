package com.psc.flickerly.domain.usecase;

import com.psc.flickerly.domain.model.PhotoInfo;

import java.util.List;

import io.reactivex.Observable;
public interface UseCase<P, T> {

    //not needed?
    interface Callback<T> {
        void onSuccess(T data);
        void onError(Throwable throwable);
    }

    T execute(P parameter);
}
