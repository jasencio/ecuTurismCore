package ec.turismvisitplanner.core.controllers.organization;

import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.payload.request.OrganizationRequest;
import ec.turismvisitplanner.core.services.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config/organization")
@AllArgsConstructor
@Tag(name = "Organization", description = "Organization management APIs")
public class OrganizationController {


    private OrganizationService organizationService;
;

    @Operation(summary = "Get all organizations", description = "Retrieves a list of all organizations")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all organizations",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Organization.class)))
    @GetMapping
    public List<Organization> getAllOrganization() {
        return organizationService.getAll();
    }

    @Operation(summary = "Get organization by ID", description = "Retrieves a specific organization by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the organization",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Organization.class))),
        @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganization(
            @Parameter(description = "ID of the organization to retrieve", required = true)
            @PathVariable String id) {
        return new ResponseEntity<>(organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create new organization", description = "Creates a new organization with optional image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Organization successfully created",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Organization.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        try {
            Organization organization = organizationService.createOrganization(organizationRequest);
            return new ResponseEntity<>(organization, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update organization", description = "Updates an existing organization by ID with optional image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Organization successfully updated",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Organization.class))),
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrganization(
            @Parameter(description = "ID of the organization to update", required = true)
            @PathVariable("id") String id,
            @RequestBody OrganizationRequest organizationRequest) {
        try {
            Organization organization = organizationService.updateOrganization(id, organizationRequest);
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
