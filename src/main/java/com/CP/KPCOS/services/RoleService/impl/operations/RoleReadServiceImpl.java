package com.CP.KPCOS.services.RoleService.impl.operations;


import com.CP.KPCOS.entity.RoleEntity;
import com.CP.KPCOS.repository.RoleRepository.RoleRepository;
import com.CP.KPCOS.services.RoleService.operations.RoleReadService;
import org.springframework.stereotype.Service;

@Service
public class RoleReadServiceImpl implements RoleReadService {

    private final RoleRepository roleRepository;

    public RoleReadServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public RoleEntity getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public RoleEntity getCustomerRole() {
        return roleRepository.findByName("Customer");
    }
}
