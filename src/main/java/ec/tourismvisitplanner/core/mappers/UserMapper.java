package ec.tourismvisitplanner.core.mappers;

import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.payload.dto.UserDto;
import ec.tourismvisitplanner.core.payload.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto toUserDto(User user);

    LoginResponse toLoginResponse(User user);
}
