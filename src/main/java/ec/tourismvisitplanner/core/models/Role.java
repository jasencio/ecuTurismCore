package ec.tourismvisitplanner.core.models;

import ec.tourismvisitplanner.core.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "roles")
public class Role {
  @Id
  private String id;
  private ERole name;

}
