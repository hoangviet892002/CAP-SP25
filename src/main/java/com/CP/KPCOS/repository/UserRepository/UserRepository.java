package com.CP.KPCOS.repository.UserRepository;


import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.repository.UserRepository.operations.UserCreateRepository;
import com.CP.KPCOS.repository.UserRepository.operations.UserDeleteRepository;
import com.CP.KPCOS.repository.UserRepository.operations.UserReadRepository;
import com.CP.KPCOS.repository.UserRepository.operations.UserUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>,
        UserCreateRepository, UserReadRepository, UserUpdateRepository, UserDeleteRepository {
}
