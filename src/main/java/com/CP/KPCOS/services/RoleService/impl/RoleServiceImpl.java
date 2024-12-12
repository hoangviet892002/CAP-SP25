package com.CP.KPCOS.services.RoleService.impl;


import com.CP.KPCOS.entity.RoleEntity;
import com.CP.KPCOS.services.RoleService.RoleService;
import com.CP.KPCOS.services.RoleService.impl.operations.RoleReadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleReadServiceImpl roleReadServiceImpl;
    @Autowired
    public RoleServiceImpl(RoleReadServiceImpl roleReadServiceImpl) {
        this.roleReadServiceImpl = roleReadServiceImpl;
    }

    @Override
    public RoleEntity getRoleByName(String roleName) {
        return roleReadServiceImpl.getRoleByName(roleName);
    }

    @Override
    public RoleEntity getCustomerRole() {
        return roleReadServiceImpl.getCustomerRole();
    }
}
