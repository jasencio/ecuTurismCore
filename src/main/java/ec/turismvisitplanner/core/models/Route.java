package ec.turismvisitplanner.core.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "routes")
public class Route {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    //@NotBlank
    private String address;
    private Float latitude;
    private Float longitude;
    //@NotBlank
    private String timeOpenWeek;
    //@NotBlank
    private String timeCloseWeek;
    //@NotBlank
    private String timeOpenSaturday;
    //@NotBlank
    private String timeCloseSaturday;
    //@NotBlank
    private String timeOpenSunday;
    //@NotBlank
    private String timeCloseSunday;
    //@NotBlank
    private String daysWeek;
    //@NotBlank
    private String daysWeekDisabled;
}
