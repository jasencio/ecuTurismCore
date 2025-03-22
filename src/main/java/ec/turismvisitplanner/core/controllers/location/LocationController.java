package ec.turismvisitplanner.core.controllers.location;

import ec.turismvisitplanner.core.models.Location;
import ec.turismvisitplanner.core.payload.request.LocationRequest;
import ec.turismvisitplanner.core.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping()
    public List<Location> getLocations() {
        return locationService.getLocations();
    }

    @PostMapping()
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationRequest locationRequest) {
            return locationService.createLocation(locationRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable("id") String id, @Valid @RequestBody LocationRequest locationRequest) {
        return locationService.updateLocation(id, locationRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable("id") String id) {
       return locationService.deleteLocation(id);
    }
}
