package me.alanton.userservice.mapper;

import me.alanton.userservice.dto.response.RoleResponse;
import me.alanton.userservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
}