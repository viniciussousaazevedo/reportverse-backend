package com.es.reportverse.repository;

import com.es.reportverse.model.media.PublicationMedia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationMediaRepository extends GenericMediaRepository<PublicationMedia> {

    List<PublicationMedia> findByPublicationId(Long id);

}
