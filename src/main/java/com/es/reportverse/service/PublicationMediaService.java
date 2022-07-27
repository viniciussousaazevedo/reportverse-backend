package com.es.reportverse.service;

import com.es.reportverse.model.media.PublicationMedia;
import java.util.List;

public interface PublicationMediaService {

    void registerMedias(List<String> mediasPathList, Long publicationId);

    void saveMedia(PublicationMedia publicationMedia);

    List<PublicationMedia> getMediasByPublicationId(Long publicationId);

}
