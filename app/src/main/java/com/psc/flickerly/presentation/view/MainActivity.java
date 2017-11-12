package com.psc.flickerly.presentation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.psc.flickerly.Config;
import com.psc.flickerly.R;
import com.psc.flickerly.domain.model.PhotoInfo;
import com.psc.flickerly.presentation.presenter.GalleryPresenter;
import com.psc.flickerly.presentation.presenter.GalleryView;
import com.psc.flickerly.presentation.presenter.base.PresenterFactory;
import com.psc.flickerly.presentation.view.adapter.ImageAdapter;

import java.util.List;

public class MainActivity extends BaseActivity<GalleryPresenter, GalleryView> implements GalleryView {

    private static final int LOADER_ID = 0x007;
    private EditText searchEditText;
    private ImageAdapter adapter;
    private GalleryPresenter presenter;
    private View loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        setupTopControls();
    }

    private void setupTopControls() {
        loadingSpinner = findViewById(R.id.loading_spinner);
        searchEditText = findViewById(R.id.search_query_edit_text);
        final Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(view -> {
            clearFocusOnEditText();
            final Editable editable = searchEditText.getText();
            presenter.onSearchButtonClicked(editable.toString());
        });
    }

    private void clearFocusOnEditText() {
        searchEditText.setFocusableInTouchMode(false);
        searchEditText.setFocusable(false);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.setFocusable(true);
    }

    private void setupRecyclerView() {
        adapter = new ImageAdapter();
        final GridLayoutManager layoutManager = new GridLayoutManager(this, Config.GRID_COLUMN_NUMBER);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        final RecyclerView.OnScrollListener onScrollChangeListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                final int itemCount = adapter.getItemCount();
                presenter.onViewScrolled(lastVisibleItemPosition, itemCount);
            }
        };
        recyclerView.addOnScrollListener(onScrollChangeListener);
    }

    @NonNull
    @Override
    protected PresenterFactory<GalleryPresenter> getPresenterFactory() {
        return new GalleryPresenterFactory();
    }

    @Override
    protected void onPresenterLoaded(@NonNull final GalleryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID;
    }

    public void setGalleryData(final List<PhotoInfo> list) {
        adapter.setData(list);
    }

    public void addGalleryData(final List<PhotoInfo> listToAppend) {
        adapter.addData(listToAppend);
    }

    @Override
    public void showLoadingSpinner() {
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingSpinner() {
        loadingSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayErrorMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
             .show();
    }

    @Override
    public void resetGallery() {
        adapter.resetData();
    }

}
