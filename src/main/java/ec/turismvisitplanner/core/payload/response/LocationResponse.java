package ec.turismvisitplanner.core.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationResponse {
    private String id;
    private String name;
    private String description;
    private String address;
    private String timeOpenWeek;
    private String timeCloseWeek;
    private String timeOpenSaturday;
    private String timeCloseSaturday;
    private String timeOpenSunday;
    private Integer timeCloseSunday;
    private String attentionTime;
    private String daysWeek;
    private String daysWeekDisabled;
}
