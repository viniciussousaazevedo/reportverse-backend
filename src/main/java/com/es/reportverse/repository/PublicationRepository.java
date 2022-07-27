package com.es.reportverse.repository;

import com.es.reportverse.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByAuthorId(Long id);

    Collection<Publication> findAllByNeedsReview(Boolean needsReview);

    @Query(value = "SELECT * FROM publication WHERE is_available = true ORDER BY id DESC", nativeQuery = true)
    List<Publication> findAllAvailable();

    @Query(value = "SELECT p.* " +
            "FROM publication p LEFT JOIN publication_likes pl ON pl.publication_id = p.id " +
            "WHERE EXTRACT(YEAR FROM p.creation_date) = ?1 " +
            "AND EXTRACT(MONTH FROM p.creation_date) = ?2 " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pl.publication_id)", nativeQuery = true)
    List<Publication> findAllByYearAndMonthOrderedByLikes(int year, int month);

    @Query(value = "SELECT * FROM publication " +
            "WHERE EXTRACT(YEAR FROM is_resolved_date) = ?1 " +
            "AND EXTRACT(MONTH FROM is_resolved_date) = ?2", nativeQuery = true)
    List<Publication> findAllByIsResolvedYearAndMonth(int year, int month);
}
