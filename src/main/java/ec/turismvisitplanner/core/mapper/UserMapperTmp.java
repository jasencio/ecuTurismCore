package ec.turismvisitplanner.core.mapper;

import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.dto.UserDto;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapperTmp {

    public static UserDto toDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        // Map roles to a set of role names
        if (user.getRoles() != null) {
            Set<String> rolesAssigned = user.getRoles()
                    .stream()
                    .map(role -> role.getName().getValue())
                    .collect(Collectors.toSet());

            userDto.setRoles(rolesAssigned);
        }

        return userDto;
    }
}
