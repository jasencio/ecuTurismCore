package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Location;
import ec.turismvisitplanner.core.models.Route;
import ec.turismvisitplanner.core.payload.request.RouteRequest;
import ec.turismvisitplanner.core.repository.RouteRepository;
import ec.turismvisitplanner.core.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public Route createRoute(RouteRequest routeRequest) {
        Route route =
                Route.builder().
                        name(routeRequest.getName()).
                        description(routeRequest.getDescription()).
                        location(findLocation(routeRequest.getIdLocation())).
                        minutes(routeRequest.getMinutes()).
                        hardness(routeRequest.getHardness()).build();

        return routeRepository.save(route);
    }

    public Route updateRoute(String id, RouteRequest routeRequest) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            Location location = findLocation(routeRequest.getIdLocation());
            Route _organization = route.get();
            _organization.setName(routeRequest.getName() != null ? routeRequest.getName() : _organization.getName());
            _organization.setDescription(routeRequest.getDescription() != null ? routeRequest.getDescription(): _organization.getDescription());
            _organization.setLocation(location!= null ? location : _organization.getLocation());
            _organization.setMinutes(routeRequest.getMinutes() != null ? routeRequest.getMinutes(): _organization.getMinutes());
            _organization.setHardness(routeRequest.getHardness() != null ? routeRequest.getHardness(): _organization.getHardness());
            return routeRepository.save(_organization);
        } else {
            return null;
        }
    }

    private Location findLocation(String idLocation) {
        if (idLocation != null) {
            Optional<Location> locationAux = locationRepository.findById(idLocation);
            if (locationAux.isPresent()) {
                return locationAux.get();
            }
        }
        return null;
    }
}
