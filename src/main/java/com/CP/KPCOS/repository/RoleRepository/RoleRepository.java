package com.CP.KPCOS.repository.RoleRepository;

import com.CP.KPCOS.entity.RoleEntity;
import com.CP.KPCOS.repository.RoleRepository.operations.RoleCreateRepository;
import com.CP.KPCOS.repository.RoleRepository.operations.RoleDeleteRepository;
import com.CP.KPCOS.repository.RoleRepository.operations.RoleReadRepository;
import com.CP.KPCOS.repository.RoleRepository.operations.RoleUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>,
        RoleCreateRepository,
        RoleReadRepository,
        RoleUpdateRepository,
        RoleDeleteRepository {
}
