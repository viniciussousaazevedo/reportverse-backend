package com.es.reportverse.service;

import com.es.reportverse.model.media.PublicationMedia;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PublicationMediaService {

    void registerMedias(List<MultipartFile> mediasBytesList, Long publicationId);

    void saveMedia(PublicationMedia publicationMedia);

    List<PublicationMedia> getMediasByPublicationId(Long publicationId);

}
