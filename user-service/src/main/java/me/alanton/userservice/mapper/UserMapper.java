package me.alanton.userservice.mapper;

import me.alanton.userservice.dto.response.UserResponse;
import me.alanton.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
