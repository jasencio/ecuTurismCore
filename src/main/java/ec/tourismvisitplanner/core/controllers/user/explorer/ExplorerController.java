package ec.tourismvisitplanner.core.controllers.user.explorer;

import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.services.ExplorerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/explorer")
@AllArgsConstructor
public class ExplorerController {
    
    private ExplorerService explorerService;

    @GetMapping("/organizations")
    public List<Organization> getOrganizations() {
        return explorerService.getOrganizations();
    }

    @GetMapping("/organizations/{id}")
    public Organization getOrganization(@PathVariable String id){
        return explorerService.getOrganization(id);
    }

    @GetMapping("/organizations/{id}/routes")
    public List<Route> getRoutes(@PathVariable String id){
        return explorerService.getRoutes(id);
    }

    @GetMapping("/routes/{id}")
    public Route getRoute(@PathVariable String id){
        return explorerService.getRoute(id);
    }

}
