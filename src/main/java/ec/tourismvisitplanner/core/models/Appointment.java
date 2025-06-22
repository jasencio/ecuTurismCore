package ec.tourismvisitplanner.core.models;

import ec.tourismvisitplanner.core.models.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    @DBRef
    private User tourist;
    @DBRef
    private User touristGuide;
    @DBRef
    private Route route;
    private int groupSize;
    private Date eventDate;
    private Instant eventTimeInit;
    private Instant eventTimeEnd;
    private AppointmentStatus status;
    private Instant createdAt;
    private Instant updatedAt;

}
