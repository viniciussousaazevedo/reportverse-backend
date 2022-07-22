package com.es.reportverse.repository;

import com.es.reportverse.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByAuthorId(Long id);

    Collection<Publication> findAllByNeedsReview(Boolean needsReview);

    @Query(value = "SELECT * FROM publication WHERE is_available = true ORDER BY id DESC", nativeQuery = true)
    List<Publication> findAllAvailable();

    @Query(value = "SELECT p FROM publication p, publication_likes pl " +
            "WHERE creation_date LIKE ?1 AND " +
            "p.needs_review = false AND " +
            "pl.publication_id = p.id " +
            "GROUP BY p.id", nativeQuery = true)
    List<Publication> findAllByYearMonth(YearMonth yearMonth);
}
