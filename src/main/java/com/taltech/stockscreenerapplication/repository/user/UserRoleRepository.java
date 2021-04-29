package com.taltech.stockscreenerapplication.repository.user;

import com.taltech.stockscreenerapplication.model.ERole;
import com.taltech.stockscreenerapplication.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(ERole name);
}
