package ec.turismvisitplanner.core.payload.dto;

import ec.turismvisitplanner.core.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String id;
    private String name;
    private String email;
    private String phone;
    private Set<ERole> roles;


}
