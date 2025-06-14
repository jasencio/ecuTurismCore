package ec.tourismvisitplanner.core.services.adminCompany;

import ec.tourismvisitplanner.core.exceptions.CustomException;
import ec.tourismvisitplanner.core.models.File;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.payload.request.RouteRequest;
import ec.tourismvisitplanner.core.repository.RouteRepository;
import ec.tourismvisitplanner.core.utils.FileUtils;
import ec.tourismvisitplanner.core.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RouteService {

    private RouteRepository routeRepository;
    private FileUtils fileUtils;

    public List<Route> getRoutesByUserOrganization() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        if(userTmp.getOrganization() == null){
            return new ArrayList<Route>();
        }
        return routeRepository.findByOrganization(userTmp.getOrganization());
    }

    public Route getRouteByIdAndUserOrganization(String routeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        if(userTmp.getOrganization() == null){
            return null;
        }
        return routeRepository.findByIdAndOrganization(routeId, userTmp.getOrganization());
    }

    public ResponseEntity<?> createRoute(RouteRequest routeRequest) {

        if(routeRequest== null){
            throw new IllegalArgumentException("Request must not be null");
        }

        if (routeRequest.getImageBase64() == null || routeRequest.getImageBase64().trim().isEmpty()) {
            throw new IllegalArgumentException("Image cannot be null or empty");
        }

        File fileSaved;
        try {
             fileSaved = fileUtils.saveImageBase64(routeRequest.getImageBase64());
        } catch (IOException e) {
            throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();

        Route route =
                Route.builder().
                        name(routeRequest.getName()).
                        description(routeRequest.getDescription()).
                        organization(userTmp.getOrganization()).
                        minutes(routeRequest.getMinutes()).
                        hardness(routeRequest.getHardness()).
                        mainImage(fileSaved).
                        isActive(routeRequest.getIsActive()).build();
        Route createdRoute = routeRepository.save(route);
        return ResponseUtil.success(createdRoute);
    }

    public ResponseEntity<?> updateRoute(String id, RouteRequest routeRequest) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isEmpty()) {
            throw new CustomException("Ruta no encontrada", HttpStatus.NOT_FOUND.value());
        }
        if (routeRequest.getImageBase64() == null || routeRequest.getImageBase64().trim().isEmpty()) {
            throw new IllegalArgumentException("Image cannot be null or empty");
        }

        Route _route = route.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();

        if(!_route.getOrganization().getId().equals(userTmp.getOrganization().getId())){
            throw new CustomException("Acceso no autorizado", HttpStatus.UNAUTHORIZED.value());
        }

        File fileSaved;
        try {
             fileSaved = fileUtils.saveImageBase64(routeRequest.getImageBase64());
        } catch (IOException e) {
            throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
        }
        _route.setMainImage(fileSaved);

        if (routeRequest.getName() != null) {
            _route.setName(routeRequest.getName());
        }
        
        if (routeRequest.getDescription() != null) {
            _route.setDescription(routeRequest.getDescription());
        }
        
        if (routeRequest.getMinutes() != null) {
            _route.setMinutes(routeRequest.getMinutes());
        }
        
        if (routeRequest.getHardness() != null) {
            _route.setHardness(routeRequest.getHardness());
        }
        
        if (routeRequest.getDistance() != null) {
            _route.setDistance(routeRequest.getDistance());
        }
        _route.setIsActive(routeRequest.getIsActive());

        Route routeUpdated = routeRepository.save(_route);
        return ResponseUtil.success(routeUpdated);
    }

}
