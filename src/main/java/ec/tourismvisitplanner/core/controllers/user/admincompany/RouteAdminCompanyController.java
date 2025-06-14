package ec.tourismvisitplanner.core.controllers.user.admincompany;

import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.payload.request.RouteRequest;
import ec.tourismvisitplanner.core.services.adminCompany.RouteService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin-company/route")
public class RouteAdminCompanyController {

    @Autowired
    private RouteService routeService;

    @GetMapping("")
    public List<Route> getRoutes() {
        return routeService.getRoutesByUserOrganization();
    }

    @GetMapping("/{id}")
    public Route getRouteByIdAndUserOrgnization(
            @Parameter(description = "ID of the organization to retrieve", required = true)
            @PathVariable String id) {
        return routeService.getRouteByIdAndUserOrganization(id);
    }

    @PostMapping("")
    public ResponseEntity<?> createRoute(
            @Valid @RequestBody RouteRequest routeRequest) {
        return routeService.createRoute(routeRequest);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<?> updateRoute(
            @PathVariable("id") String id,
            @Valid @RequestBody RouteRequest routeRequest) {
        return routeService.updateRoute(id, routeRequest);

    }
}
