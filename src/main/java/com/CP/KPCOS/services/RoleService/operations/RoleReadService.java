package com.CP.KPCOS.services.RoleService.operations;

import com.CP.KPCOS.entity.RoleEntity;

public interface RoleReadService {
     RoleEntity getRoleByName(String roleName);
     RoleEntity getCustomerRole();
}
