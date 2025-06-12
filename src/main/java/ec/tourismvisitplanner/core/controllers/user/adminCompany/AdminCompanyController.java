package ec.tourismvisitplanner.core.controllers.user.adminCompany;

import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.payload.request.OrganizationRequest;
import ec.tourismvisitplanner.core.services.AdminCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-company")
@AllArgsConstructor
public class AdminCompanyController {

    private final AdminCompanyService adminCompanyService;

    @GetMapping("/organization")
    public ResponseEntity<?> getOrganization() {
        return new ResponseEntity<>(adminCompanyService.getOrganization(), HttpStatus.OK);
    }

    @PutMapping("/organization")
    public ResponseEntity<?> updateOrganization(@RequestBody OrganizationRequest organizationRequest) {
        Organization organization = adminCompanyService.updateOrganization(organizationRequest);
        if (organization != null) {
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
