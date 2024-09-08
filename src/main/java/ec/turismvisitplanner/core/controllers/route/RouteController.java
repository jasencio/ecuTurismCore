package ec.turismvisitplanner.core.controllers.route;

import ec.turismvisitplanner.core.services.RouteService;
import ec.turismvisitplanner.core.models.Route;
import ec.turismvisitplanner.core.payload.request.RouteRequest;
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
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
     private RouteService routeService;

    @GetMapping()
    public List<Route> getRoutes() {
        return routeService.getAll();
    }

    @PostMapping()
    public ResponseEntity<?> createRoute(@RequestBody RouteRequest routeRequest) {
        try {
            Route route = routeService.createRoute(routeRequest);
            return new ResponseEntity<>(route, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoute(@PathVariable("id") String id, @RequestBody RouteRequest routeRequest) {
        Route route = routeService.updateRoute(id, routeRequest);
        if (route != null) {
            return new ResponseEntity<>(route, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
