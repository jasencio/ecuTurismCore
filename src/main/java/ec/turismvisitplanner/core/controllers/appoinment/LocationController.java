package ec.turismvisitplanner.core.controllers.appoinment;

import ec.turismvisitplanner.core.models.Route;
import ec.turismvisitplanner.core.payload.request.LocationRequest;
import ec.turismvisitplanner.core.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/location")
    public List<Route> getLocations() {
        return locationService.getAll();
    }

    @PostMapping("/location")
    public String createLocation(@RequestBody LocationRequest locationRequest) {
         locationService.createLocation(locationRequest);
         return "test";
    }
}
