package com.jmcano.gestorpuestos.mapper;

import com.jmcano.gestorpuestos.dto.user.UserRequest;
import com.jmcano.gestorpuestos.dto.user.UserResponse;
import com.jmcano.gestorpuestos.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> users);
}
