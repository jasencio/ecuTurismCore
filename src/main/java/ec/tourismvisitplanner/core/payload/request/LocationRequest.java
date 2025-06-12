package ec.tourismvisitplanner.core.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationRequest {

    private String id;
    private String idOrganization;
    private String name;
    private String description;
    private String address;
    private Float latitude;
    private Float longitude;
    private String timeOpenWeek;
    private String timeCloseWeek;
    private String timeOpenSaturday;
    private String timeCloseSaturday;
    private String timeOpenSunday;
    private String timeCloseSunday;
    private String daysWeekDisabled;
}
