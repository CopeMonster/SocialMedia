package me.alanton.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import me.alanton.userservice.dto.request.RoleRequest;
import me.alanton.userservice.dto.response.RoleResponse;
import me.alanton.userservice.entity.Role;
import me.alanton.userservice.exception.impl.BusinessException;
import me.alanton.userservice.exception.impl.BusinessExceptionReason;
import me.alanton.userservice.mapper.RoleMapper;
import me.alanton.userservice.repository.RoleRepository;
import me.alanton.userservice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> {
                    // TODO: add exception logging
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND_EXCEPTION);
                });

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .name(roleRequest.name())
                .build();

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse updateRole(String name, RoleRequest roleRequest) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> {
                    // TODO: add exception logging
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND_EXCEPTION);
                });

        role.setName(roleRequest.name());

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.findByName(name)
                .orElseThrow(() -> {
                    // TODO: add exception logging
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND_EXCEPTION);
                });

        roleRepository.deleteByName(name);
    }

    @Override
    public boolean isExistByName(String name) {
        return roleRepository.existsByName(name);
    }
}