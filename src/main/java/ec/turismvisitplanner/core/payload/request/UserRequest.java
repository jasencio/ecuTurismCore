package ec.turismvisitplanner.core.payload.request;

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

    private Set<String> idRoles;
    private String idOrganization;
}
