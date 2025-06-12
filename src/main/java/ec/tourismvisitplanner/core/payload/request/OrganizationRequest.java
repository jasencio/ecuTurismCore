package ec.tourismvisitplanner.core.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String imageBase64;
    private Boolean isActive;
}
