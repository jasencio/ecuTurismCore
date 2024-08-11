package ec.turismvisitplanner.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointments {
    @Id
    private String id;
    @DBRef
    private User tourist;
    @DBRef
    private User touristGuide;

    private Date eventDateTime;
}
