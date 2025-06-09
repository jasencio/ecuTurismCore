package ec.turismvisitplanner.core.controllers.organization;

import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.payload.request.OrganizationRequest;
import ec.turismvisitplanner.core.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{id}")
    public ResponseEntity<?>  getOrganization(@PathVariable String id) {
        return new ResponseEntity<>(organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createOrganization(
            @RequestPart("organization") OrganizationRequest organizationRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            Organization organization = organizationService.createOrganization(organizationRequest, image);
            return new ResponseEntity<>(organization, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateOrganization(
            @PathVariable("id") String id,
            @RequestPart("organization") OrganizationRequest organizationRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            Organization organization = organizationService.updateOrganization(id, organizationRequest, image);
            if (organization != null) {
                return new ResponseEntity<>(organization, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
