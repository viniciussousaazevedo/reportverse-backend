package com.es.reportverse.service;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.media.PublicationMedia;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.es.reportverse.repository.PublicationMediaRepository;

import java.io.FileInputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationMediaServiceImpl implements PublicationMediaService {

    private final static String MEDIA_ERROR = "Ocorreu algum erro no upload da mídia.";

    private PublicationMediaRepository publicationMediaRepository;

    private MediaService mediaService;

    public void registerMedias(List<String> mediasPathList, Long publicationId) {
        try {

            for (String mediaPath : mediasPathList) {

                String codeMedia = this.encodeMedia(mediaPath);

                PublicationMedia publicationMedia = new PublicationMedia(codeMedia, publicationId);
                this.saveMedia(publicationMedia);
            }

        } catch (Exception ex) {
            throw new ApiRequestException(MEDIA_ERROR);
        }
    }

    public void saveMedia(PublicationMedia publicationMedia) {
        publicationMediaRepository.save(publicationMedia);
    }

    public List<PublicationMedia> getMediasByPublicationId(Long publicationId) {
        return publicationMediaRepository.findByPublicationId(publicationId);
    }

    private String encodeMedia(String mediaPath) throws Exception {

        FileInputStream mediaStream = new FileInputStream(mediaPath);
        String codedMedia = mediaService.encodeMedia(mediaStream.readAllBytes());
        mediaStream.close();
        return codedMedia;
    }

}
