package ec.tourismvisitplanner.core.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {

	private String username;

	private String email;

	@NotBlank
	private String password;
}
