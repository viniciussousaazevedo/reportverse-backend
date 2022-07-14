package com.es.reportverse.service;

import com.es.reportverse.model.Media;
import java.util.List;

public interface MediaService {

    void registerMedias(List<String> mediasPathList, Long publicationId);

    void saveMedia(Media media);

    List<Media> getMediasByPublicationId(Long publicationId);

    String encodeMedia(String mediaPath) throws Exception;

}
