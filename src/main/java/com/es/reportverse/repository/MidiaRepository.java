package com.es.reportverse.repository;

import com.es.reportverse.model.Midia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MidiaRepository extends JpaRepository<Midia, Long> {

    List<Midia> findByPublicationId(Long id);

}
