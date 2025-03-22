package ec.turismvisitplanner.core.models;

import ec.turismvisitplanner.core.models.enums.Hardness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    @DBRef
    private Location location;

    private String name;

    private String description;

    private String minutes;

    private Hardness hardness;

    private String distance;

    private File mainImage;
}
