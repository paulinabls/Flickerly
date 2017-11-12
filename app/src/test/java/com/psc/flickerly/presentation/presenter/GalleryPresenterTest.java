package com.psc.flickerly.presentation.presenter;

import com.psc.flickerly.domain.model.PhotoInfo;
import com.psc.flickerly.domain.usecase.SearchPhotosUseCase;
import com.psc.flickerly.domain.usecase.SearchPhotosUseCase.Param;
import com.psc.flickerly.presentation.model.SearchDataHolder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class GalleryPresenterTest {

    private static final Param SAMPLE_QUERY_PARAM = new Param(9, 1, "query");
    private static final List<PhotoInfo> PHOTO_INFOS = Arrays.asList(getMockedPhotoInfo(), getMockedPhotoInfo());
    private static final Observable<List<PhotoInfo>> OBSERVABLE_LIST = Observable.just(PHOTO_INFOS);
    @Mock
    private SearchPhotosUseCase searchPhotosUseCase;
    @Mock
    private SearchDataHolder searchDataHolder;
    @Mock
    private GalleryView view;
    private GalleryPresenter tested;

    private static PhotoInfo getMockedPhotoInfo() {
        return mock(PhotoInfo.class);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        tested = new GalleryPresenter(searchDataHolder, searchPhotosUseCase);
    }

    @Test
    public void onViewAttached_calledAfterPhotosReceivedAndViewDetached_shouldSetGalleryData() throws Exception {
        tested.onPhotosReceived(PHOTO_INFOS);
        tested.onViewDetached();

        tested.onViewAttached(view);

        verify(view).setGalleryData(PHOTO_INFOS);
    }

    @Test
    public void onViewDetached() throws Exception {
    }

    @Test
    public void onDestroyed_calledRightAfterSearchClicked_should_haveNoInteractionsOnView() throws Exception {
        tested.onViewAttached(view);
        TestObserver<List<PhotoInfo>> testSubscriber = OBSERVABLE_LIST.test();
        final Observable<List<PhotoInfo>> delayedObservable = OBSERVABLE_LIST.delay(300, TimeUnit.MILLISECONDS);
        when(searchPhotosUseCase.execute(nullable(Param.class))).thenReturn(delayedObservable);

        tested.onSearchButtonClicked("whatever");
        clearInvocations(view);

        tested.onDestroyed();

        Thread.sleep(300);
        assertTrue(testSubscriber.isDisposed());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void onSearchButtonClicked_showSpinner_andResetGallery_on_view() throws Exception {
        arrangeMocksForSearch();

        tested.onSearchButtonClicked("whatever");

        verify(view).showLoadingSpinner();
        verify(view).resetGallery();
    }

    @Test
    public void onSearchButtonClicked_setsNewQuery_andRequestsNewQueryParams_on_searchDataHolder() throws Exception {
        arrangeMocksForSearch();
        final String myQuery = "myQuery";

        tested.onSearchButtonClicked(myQuery);

        verify(searchDataHolder).setNewQuery(myQuery);
        verify(searchDataHolder).getNextQueryParam();
    }

    private void arrangeMocksForSearch() {
        tested.onViewAttached(view);
        when(searchPhotosUseCase.execute(nullable(Param.class))).thenReturn(OBSERVABLE_LIST);
    }

    @Test
    public void onSearchButtonClicked_execute_on_searchPhotosUseCase_withCorrectParam() throws Exception {
        tested.onViewAttached(view);
        when(searchDataHolder.getNextQueryParam()).thenReturn(SAMPLE_QUERY_PARAM);
        when(searchPhotosUseCase.execute(SAMPLE_QUERY_PARAM)).thenReturn(OBSERVABLE_LIST);
        final String myQuery = "myQuery";

        tested.onSearchButtonClicked(myQuery);

        verify(searchPhotosUseCase).execute(SAMPLE_QUERY_PARAM);
    }

    @Test
    public void onPhotosReceived_whenNotEmptyList_andWhenViewAttached_informsView() throws Exception {
        tested.onViewAttached(view);

        tested.onPhotosReceived(PHOTO_INFOS);

        verify(view).addGalleryData(PHOTO_INFOS);
        verify(view).hideLoadingSpinner();
    }

    @Test
    public void onPhotosReceived_whenEmptyList_andWhenViewAttached_should_displayErrorMessage_and_hideLoadingSpinner
            () throws Exception {
        tested.onViewAttached(view);

        tested.onPhotosReceived(Collections.emptyList());

        verify(view).hideLoadingSpinner();
        verify(view).displayErrorMessage(anyString());
    }

    @Test
    public void onPhotosReceived_whenEmptyList_should_setNoResultReceived() throws Exception {
        tested.onPhotosReceived(Collections.emptyList());

        verify(searchDataHolder).setNoResultReceived();
    }

    @Test
    public void onPhotosReceived_whenEmptyList_andWhenViewNotAttached_should_notCallViewMethods() throws Exception {
        tested.onPhotosReceived(Collections.emptyList());

        verify(view, never()).hideLoadingSpinner();
        verify(view, never()).displayErrorMessage(anyString());
    }

    @Test
    public void onPhotosReceived_whenNotEmptyList_andWhenViewNotAttached_doesntInformView() throws Exception {
        tested.onPhotosReceived(PHOTO_INFOS);

        verify(view, never()).addGalleryData(PHOTO_INFOS);
        verify(view, never()).hideLoadingSpinner();
    }

    @Test
    public void
    onViewScrolled_whenVisibleLastItem_and_noResultReceived_isFalse_shouldExecuteSearch_andShowLoadingSpinner()
            throws Exception {
        arrangeMocksForSearch();
        when(searchDataHolder.noResultReceived()).thenReturn(false);
        final int itemCount = 6;
        final int visiblePosition = 5;

        tested.onViewScrolled(visiblePosition, itemCount);

        verify(view).showLoadingSpinner();
        verify(searchPhotosUseCase).execute(any());
    }

    @Test
    public void
    onViewScrolled_whenVisibleLastItem_and_noResultReceived_isTrue_shouldNotCallView_norExecuteSearch()
            throws Exception {
        arrangeMocksForSearch();
        when(searchDataHolder.noResultReceived()).thenReturn(true);
        final int itemCount = 6;
        final int visiblePosition = 5;

        tested.onViewScrolled(visiblePosition, itemCount);

        verify(view, never()).showLoadingSpinner();
        verify(searchPhotosUseCase, never()).execute(any());
    }

    @Test
    public void
    onViewScrolled_when_NOT_VisibleLastItem_and_noResultReceived_isFalse_shouldNotCallView_norExecuteSearch()
            throws Exception {
        arrangeMocksForSearch();
        when(searchDataHolder.noResultReceived()).thenReturn(false);
        final int itemCount = 6;
        final int visibleNotLastPosition = 2;

        tested.onViewScrolled(visibleNotLastPosition, itemCount);

        verify(view, never()).showLoadingSpinner();
        verify(searchPhotosUseCase, never()).execute(any());
    }

}