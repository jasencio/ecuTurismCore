package ec.turismvisitplanner.core.controllers.location;

import ec.turismvisitplanner.core.models.Location;
import ec.turismvisitplanner.core.payload.request.LocationRequest;
import ec.turismvisitplanner.core.services.LocationService;
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
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("")
    public List<Location> getLocations() {
        return locationService.getAll();
    }

    @PostMapping("")
    public ResponseEntity<?> createLocation(@RequestBody LocationRequest locationRequest) {
        try {
            Location location = locationService.createLocation(locationRequest);
            return new ResponseEntity<>(location, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateTutorial(@PathVariable("id") String id, @RequestBody LocationRequest locationRequest) {
        Location location = locationService.updateLocation(id, locationRequest);
        if (location != null) {
            return new ResponseEntity<>(location, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
