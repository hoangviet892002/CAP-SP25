package com.CP.KPCOS.repository.RoleRepository.operations;

import com.CP.KPCOS.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReadRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
