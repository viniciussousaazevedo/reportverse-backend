package com.es.reportverse.repository;

import com.es.reportverse.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    Optional<Publication> findById(Long id);

    List<Publication> findByAuthorId(Long id);

    Collection<Publication> findAllByNeedsReview(Boolean needsReview);

    @Query(value = "SELECT * FROM publication WHERE is_available = true ORDER BY id DESC", nativeQuery = true)
    List<Publication> findAllAvailable();
}
