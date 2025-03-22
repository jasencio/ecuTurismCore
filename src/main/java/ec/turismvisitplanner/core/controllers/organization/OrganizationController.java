package ec.turismvisitplanner.core.controllers.organization;

import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.payload.request.OrganizationRequest;
import ec.turismvisitplanner.core.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping(                      )
    public List<Organization> getAllOrganization() {
        return organizationService.getAll();
    }

    @PostMapping()
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        try {
            Organization organization = organizationService.createOrganization(organizationRequest);
            return new ResponseEntity<>(organization, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(@PathVariable("id") String id, @RequestBody OrganizationRequest organizationRequest) {
        Organization organization = organizationService.updateOrganization(id, organizationRequest);
        if (organization != null) {
            return new ResponseEntity<>(organization, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
