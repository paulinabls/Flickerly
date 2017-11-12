package com.psc.flickerly.presentation.presenter.base;


public interface PresenterFactory<T extends Presenter> {
    T create();
}
