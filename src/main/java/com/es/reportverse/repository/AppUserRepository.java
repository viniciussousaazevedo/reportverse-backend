package com.es.reportverse.repository;

import com.es.reportverse.enums.UserRole;
import com.es.reportverse.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByRecoveryPasswordToken(String recoveryPasswordToken);

    Collection<AppUser> findAllByUserRole(UserRole userRole);

    @Query(value = "SELECT * FROM app_user WHERE " +
            "EXTRACT(YEAR FROM creation_date) = ?1 AND " +
            "EXTRACT(MONTH FROM creation_date) = ?2",
            nativeQuery = true)
    List<AppUser> findAllByYearAndMonth(int year, int month);

}
