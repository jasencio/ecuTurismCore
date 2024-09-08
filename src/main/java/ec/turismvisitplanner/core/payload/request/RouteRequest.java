package ec.turismvisitplanner.core.payload.request;

import ec.turismvisitplanner.core.models.enums.Hardness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteRequest {

    private String id;

    private String idLocation;

    private String name;

    private String description;

    private String minutes;

    private Hardness hardness;
}
