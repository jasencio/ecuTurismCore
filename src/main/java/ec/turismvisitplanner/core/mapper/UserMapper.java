package ec.turismvisitplanner.core.mapper;

import ec.turismvisitplanner.core.models.Role;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.dto.UserDto;
import ec.turismvisitplanner.core.payload.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserDto toUserDto(User user);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    LoginResponse toLoginResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().getValue()) // Use ERole's getValue()
                .collect(Collectors.toSet());
    }
}
