package ec.tourismvisitplanner.core.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordVerifyRequest {
    @NotNull(message = "{passwordVerify.email.notnull}")
    @Email(message="{passwordVerify.email.notValid}")
    private String email;
    @NotNull(message = "{passwordVerify.code.notnull}")
    private String code;
    @NotNull(message = "{passwordVerify.password.notnull}")
    private String password;
}
