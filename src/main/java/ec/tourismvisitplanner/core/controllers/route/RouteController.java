package ec.tourismvisitplanner.core.controllers.route;

import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.payload.request.RouteRequest;
import ec.tourismvisitplanner.core.services.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping()
    public List<Route> getRoutes() {
        return routeService.getAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRoute(
            @Valid @RequestPart("data") RouteRequest routeRequest,
            @RequestPart("file") MultipartFile file) {
        return routeService.createRoute(routeRequest, file);
    }

    @PutMapping(value ="/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateRoute(
            @PathVariable("id") String id,
            @Valid @RequestPart("data") RouteRequest routeRequest,
            @RequestPart("file") MultipartFile file) {
        return routeService.updateRoute(id, routeRequest, file);

    }

}
