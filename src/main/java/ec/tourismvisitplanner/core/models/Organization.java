package ec.tourismvisitplanner.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization {
    @Id
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
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private File image;
}
