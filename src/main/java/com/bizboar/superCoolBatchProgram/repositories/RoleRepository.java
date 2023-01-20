package com.bizboar.superCoolBatchProgram.repositories;

import com.bizboar.superCoolBatchProgram.models.auth.ERole;
import com.bizboar.superCoolBatchProgram.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
