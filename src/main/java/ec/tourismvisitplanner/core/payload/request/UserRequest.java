package ec.tourismvisitplanner.core.payload.request;

import ec.tourismvisitplanner.core.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserRequest extends SignupRequest{

    private Set<ERole> roles;
    private String idOrganization;
}
