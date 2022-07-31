package com.es.reportverse.service;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.media.PublicationMedia;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.es.reportverse.repository.PublicationMediaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PublicationMediaServiceImpl implements PublicationMediaService {

    private final static String MEDIA_ERROR = "Ocorreu algum erro no upload da mídia.";

    private PublicationMediaRepository publicationMediaRepository;

    private MediaService mediaService;

    @Override
    public void registerMedias(List<MultipartFile> mediasList, Long publicationId) {
        try {

            for (MultipartFile media : mediasList) {

                String codeMedia = this.encodeMedia(media);

                PublicationMedia publicationMedia = new PublicationMedia(codeMedia, publicationId);
                this.saveMedia(publicationMedia);
            }

        } catch (Exception ex) {
            throw new ApiRequestException(MEDIA_ERROR);
        }
    }

    @Override
    public void saveMedia(PublicationMedia publicationMedia) {
        publicationMediaRepository.save(publicationMedia);
    }

    @Override
    public List<PublicationMedia> getMediasByPublicationId(Long publicationId) {
        return publicationMediaRepository.findByPublicationId(publicationId);
    }

    private String encodeMedia(MultipartFile media) throws Exception {

        return mediaService.encodeMedia(media.getBytes());
    }

}
