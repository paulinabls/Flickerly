package com.psc.flickerly.presentation.presenter.base;

public interface Presenter<V> {
    void onViewAttached(V view);

    void onViewDetached();

    void onDestroyed();
}
