package com.CP.KPCOS.repository.UserRepository.operations;


import com.CP.KPCOS.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeleteRepository extends JpaRepository<UserEntity, Long> {
}
