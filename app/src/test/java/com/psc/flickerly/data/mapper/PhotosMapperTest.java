package com.psc.flickerly.data.mapper;

import com.psc.flickerly.data.model.Photo;
import com.psc.flickerly.data.model.Photos;
import com.psc.flickerly.data.model.SearchResponse;
import com.psc.flickerly.domain.model.PhotoInfo;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PhotosMapperTest {

    private PhotosMapper tested = new PhotosMapper();

    @Test
    public void getPhotosList_whenResponseNotOK_returnsEmptyList() throws Exception {
        final List<Photo> photosList = tested.getPhotosList(new SearchResponse("error", 1, "msg"));
        assertTrue(photosList.isEmpty());
    }

    @Test
    public void getPhotosList_whenResponseOK_returnsPhotoList() throws Exception {
        final SearchResponse response = new SearchResponse("ok", 1, "msg");
        final Photo photo1 = mock(Photo.class);
        final Photo photo2 = mock(Photo.class);
        final List<Photo> list = Arrays.asList(photo1, photo2);
        response.photos = new Photos(0, 1, 2, "3", list);

        final List<Photo> photosList = tested.getPhotosList(response);

        assertEquals(2, photosList.size());
        assertEquals(photo1, photosList.get(0));
        assertEquals(photo2, photosList.get(1));
    }

    @Test
    public void getPhotosList_whenResponseOK_andListIsNull_returnsEmptyList() throws Exception {
        final SearchResponse response = new SearchResponse("ok", 1, "msg");
        response.photos = new Photos(0, 1, 2, "3", null);

        final List<Photo> photosList = tested.getPhotosList(response);

        assertTrue(photosList.isEmpty());
    }

    @Test
    public void map() throws Exception {
        final Photo photo1 = new Photo("id1", "secret1", "server1", 1);
        final Photo photo2 = new Photo("id2", "secret2", "server2", 2);
        final List<Photo> list = Arrays.asList(photo1, photo2);

        final List<PhotoInfo> mapped = tested.map(list);

        assertEquals(2, mapped.size());
        verify(photo1, mapped.get(0));
        verify(photo2, mapped.get(1));
    }

    private void verify(final Photo photo, final PhotoInfo photoInfo) {
        assertEquals(photo.getFarm(), photoInfo.getFarm());
        assertEquals(photo.getId(), photoInfo.getId());
        assertEquals(photo.getSecret(), photoInfo.getSecret());
        assertEquals(photo.getServer(), photoInfo.getServer());
    }

}