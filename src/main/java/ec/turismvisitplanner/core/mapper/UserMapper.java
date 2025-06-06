package ec.turismvisitplanner.core.mapper;

import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.dto.UserDto;
import ec.turismvisitplanner.core.payload.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto toUserDto(User user);

    LoginResponse toLoginResponse(User user);
}
