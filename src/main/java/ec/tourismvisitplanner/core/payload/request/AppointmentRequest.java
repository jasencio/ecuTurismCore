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
    private String routeId;
    private Date eventDate;
    private String eventTimeInit;
    private String eventTimeEnd;
    private Integer groupSize;
}
