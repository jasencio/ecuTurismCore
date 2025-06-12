package ec.tourismvisitplanner.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "locations")
public class  Location {

    @Id
    private String id;

    @DBRef
    private Organization organization;

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

    private String daysWeek;

    private String daysWeekDisabled;

    private Date deletedAt;
}
