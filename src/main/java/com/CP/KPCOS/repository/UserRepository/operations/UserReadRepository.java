package com.CP.KPCOS.repository.UserRepository.operations;

import com.CP.KPCOS.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
