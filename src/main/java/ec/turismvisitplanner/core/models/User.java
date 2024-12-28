package ec.turismvisitplanner.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "users")
public class User {
  @Id
  private String id;
  @NotBlank
  private String username;
  @NotBlank
  @Size(max = 120)
  private String name;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  @NotBlank
  @Size(max = 120)
  private String phone;
  @NotBlank
  @Size(max = 120)
  @JsonIgnore
  private String password;
  @DBRef
  private Set<Role> roles = new HashSet<>();
  @DBRef
  private Organization organization;
}
