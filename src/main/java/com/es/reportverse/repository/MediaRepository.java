package com.es.reportverse.repository;

import com.es.reportverse.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByPublicationId(Long id);

}
