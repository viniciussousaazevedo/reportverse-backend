package com.es.reportverse.service;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.Media;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.es.reportverse.repository.MediaRepository;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final static String MEDIA_ERROR = "Ocorreu algum erro no upload da mídia.";

    private MediaRepository mediaRepository;

    public void registerMedias(List<String> mediasPathList, Long publicationId) {
        try {

            for (String mediaPath : mediasPathList) {

                String codeMedia = this.encodeMedia(mediaPath);

                Media media = new Media(codeMedia, publicationId);
                this.saveMedia(media);
            }

        } catch (Exception ex) {
            throw new ApiRequestException(MEDIA_ERROR);
        }
    }

    public void saveMedia(Media media) {
        mediaRepository.save(media);
    }

    public List<Media> getMediasByPublicationId(Long publicationId) {
        return mediaRepository.findByPublicationId(publicationId);
    }

    public String encodeMedia(String mediaPath) throws Exception {

        FileInputStream mediaStream = new FileInputStream(mediaPath);
        byte[] data = mediaStream.readAllBytes();
        String mediaString = Base64.getEncoder().encodeToString(data);
        mediaStream.close();

        return mediaString;
    }

}
