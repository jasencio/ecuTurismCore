package ec.turismvisitplanner.core.payload.response;

import ec.turismvisitplanner.core.payload.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends UserDto {

    private String token;
    private long expiresIn;
}
