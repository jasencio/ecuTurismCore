package ec.tourismvisitplanner.core.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {

    private String id;

    private String idTourist;

    private String idTouristGuide;

    private String idRoute;

    private Date eventDate;

    private int groupSize;
}
