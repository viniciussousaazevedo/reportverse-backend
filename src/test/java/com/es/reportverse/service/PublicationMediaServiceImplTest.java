package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.media.PublicationMedia;
import com.es.reportverse.repository.PublicationMediaRepository;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {PublicationMediaServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PublicationMediaServiceImplTest {
    @MockBean
    private MediaService mediaService;

    @MockBean
    private PublicationMediaRepository publicationMediaRepository;

    @Autowired
    private PublicationMediaServiceImpl publicationMediaServiceImpl;

    /**
     * Method under test: {@link PublicationMediaServiceImpl#registerMedias(List, Long)}
     */
    @Test
    void testRegisterMedias() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by registerMedias(List, Long)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        publicationMediaServiceImpl.registerMedias(new ArrayList<>(), 123L);
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#registerMedias(List, Long)}
     */
    @Test
    void testRegisterMedias2() throws Exception {
        PublicationMedia publicationMedia = new PublicationMedia();
        publicationMedia.setCode("Code");
        publicationMedia.setId(123L);
        publicationMedia.setPublicationId(123L);
        when(publicationMediaRepository.save((PublicationMedia) any())).thenReturn(publicationMedia);
        when(mediaService.encodeMedia((byte[]) any())).thenReturn("secret");

        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        publicationMediaServiceImpl.registerMedias(multipartFileList, 123L);
        verify(publicationMediaRepository).save((PublicationMedia) any());
        verify(mediaService).encodeMedia((byte[]) any());
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#registerMedias(List, Long)}
     */
    @Test
    void testRegisterMedias3() throws Exception {
        PublicationMedia publicationMedia = new PublicationMedia();
        publicationMedia.setCode("Code");
        publicationMedia.setId(123L);
        publicationMedia.setPublicationId(123L);
        when(publicationMediaRepository.save((PublicationMedia) any())).thenReturn(publicationMedia);
        when(mediaService.encodeMedia((byte[]) any())).thenThrow(new Exception("An error occurred"));

        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        assertThrows(ApiRequestException.class,
                () -> publicationMediaServiceImpl.registerMedias(multipartFileList, 123L));
        verify(mediaService).encodeMedia((byte[]) any());
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#registerMedias(List, Long)}
     */
    @Test
    void testRegisterMedias4() throws Exception {
        PublicationMedia publicationMedia = new PublicationMedia();
        publicationMedia.setCode("Code");
        publicationMedia.setId(123L);
        publicationMedia.setPublicationId(123L);
        when(publicationMediaRepository.save((PublicationMedia) any())).thenReturn(publicationMedia);
        when(mediaService.encodeMedia((byte[]) any())).thenReturn("secret");

        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(null);
        assertThrows(ApiRequestException.class,
                () -> publicationMediaServiceImpl.registerMedias(multipartFileList, 123L));
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#saveMedia(PublicationMedia)}
     */
    @Test
    void testSaveMedia() {
        PublicationMedia publicationMedia = new PublicationMedia();
        publicationMedia.setCode("Code");
        publicationMedia.setId(123L);
        publicationMedia.setPublicationId(123L);
        when(publicationMediaRepository.save((PublicationMedia) any())).thenReturn(publicationMedia);

        PublicationMedia publicationMedia1 = new PublicationMedia();
        publicationMedia1.setCode("Code");
        publicationMedia1.setId(123L);
        publicationMedia1.setPublicationId(123L);
        publicationMediaServiceImpl.saveMedia(publicationMedia1);
        verify(publicationMediaRepository).save((PublicationMedia) any());
        assertEquals("Code", publicationMedia1.getCode());
        assertEquals(123L, publicationMedia1.getPublicationId().longValue());
        assertEquals(123L, publicationMedia1.getId().longValue());
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#saveMedia(PublicationMedia)}
     */
    @Test
    void testSaveMedia2() {
        when(publicationMediaRepository.save((PublicationMedia) any()))
                .thenThrow(new ApiRequestException("An error occurred"));

        PublicationMedia publicationMedia = new PublicationMedia();
        publicationMedia.setCode("Code");
        publicationMedia.setId(123L);
        publicationMedia.setPublicationId(123L);
        assertThrows(ApiRequestException.class, () -> publicationMediaServiceImpl.saveMedia(publicationMedia));
        verify(publicationMediaRepository).save((PublicationMedia) any());
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#getMediasByPublicationId(Long)}
     */
    @Test
    void testGetMediasByPublicationId() {
        ArrayList<PublicationMedia> publicationMediaList = new ArrayList<>();
        when(publicationMediaRepository.findByPublicationId((Long) any())).thenReturn(publicationMediaList);
        List<PublicationMedia> actualMediasByPublicationId = publicationMediaServiceImpl.getMediasByPublicationId(123L);
        assertSame(publicationMediaList, actualMediasByPublicationId);
        assertTrue(actualMediasByPublicationId.isEmpty());
        verify(publicationMediaRepository).findByPublicationId((Long) any());
    }

    /**
     * Method under test: {@link PublicationMediaServiceImpl#getMediasByPublicationId(Long)}
     */
    @Test
    void testGetMediasByPublicationId2() {
        when(publicationMediaRepository.findByPublicationId((Long) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationMediaServiceImpl.getMediasByPublicationId(123L));
        verify(publicationMediaRepository).findByPublicationId((Long) any());
    }
}

