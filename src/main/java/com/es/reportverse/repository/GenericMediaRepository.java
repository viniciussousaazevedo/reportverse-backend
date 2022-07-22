package com.es.reportverse.repository;

import com.es.reportverse.model.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericMediaRepository<T extends Media> extends JpaRepository<T, Long> {}
