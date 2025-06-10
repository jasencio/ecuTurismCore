package ec.turismvisitplanner.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

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
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private File image;
}
