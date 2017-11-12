package com.psc.flickerly.domain.usecase;

import com.psc.flickerly.domain.Repository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchPhotosUseCaseTest {
    @Test
    public void execute_shouldCallRepositoryWithGivenParams() throws Exception {
        final Repository repository = mock(Repository.class);
        SearchPhotosUseCase tested = new SearchPhotosUseCase(repository);

        final String query = "query";
        final int pageSize = 9;
        final int pageNumber = 1;
        final SearchPhotosUseCase.Param param = new SearchPhotosUseCase.Param(pageSize, pageNumber, query);

        tested.execute(param);

        verify(repository).searchPhotos(pageSize, pageNumber, query);
    }
}