package ec.tourismvisitplanner.core.payload.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UpdateUserRequest {
    private String name;
    @Email
    private String email;
    private String phone;
    private String password;
}
