package ec.tourismvisitplanner.core.payload.response;

import ec.tourismvisitplanner.core.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Set<ERole> roles;
    private String token;
    private long expiresIn;
}
