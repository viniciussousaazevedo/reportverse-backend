package com.es.reportverse.repository;

import com.es.reportverse.enums.UserRole;
import com.es.reportverse.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByRecoveryPasswordToken(String recoveryPasswordToken);

    Collection<AppUser> findAllByUserRole(UserRole userRole);

}
