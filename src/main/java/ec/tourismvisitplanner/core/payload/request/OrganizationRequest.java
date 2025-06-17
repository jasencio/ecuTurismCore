package ec.tourismvisitplanner.core.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationRequest {
    private String id;
    private String name;
    private String description;
    private String phone;
    private String address;
    private String timeOpenWeek;
    private String timeCloseWeek;
    private String timeOpenSaturday;
    private String timeCloseSaturday;
    private String timeOpenSunday;
    private String timeCloseSunday;
    private List<String> daysWeekEnabled;
    private String imageBase64;
    private Boolean isActive;
}
