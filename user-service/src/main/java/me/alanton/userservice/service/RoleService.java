package me.alanton.userservice.service;

import me.alanton.userservice.dto.request.RoleRequest;
import me.alanton.userservice.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse getRoleByName(String name);
    List<RoleResponse> getAllRoles();
    RoleResponse createRole(RoleRequest roleRequest);
    RoleResponse updateRole(String name, RoleRequest roleRequest);
    void deleteRole(String name);
    boolean isExistByName(String name);
}