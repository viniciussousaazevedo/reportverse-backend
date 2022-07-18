package com.es.reportverse.repository;

import com.es.reportverse.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    Optional<Publication> findById(Long id);

    List<Publication> findByAuthorId(Long id);

    // @Query("SELECT * FROM publications WHERE len(reports) >= 5")
    // List<Publication> findByQttReportsGreaterThanFive();

}