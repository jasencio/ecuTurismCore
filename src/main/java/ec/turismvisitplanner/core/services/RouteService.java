package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.models.File;
import ec.turismvisitplanner.core.models.Location;
import ec.turismvisitplanner.core.models.Route;
import ec.turismvisitplanner.core.payload.request.RouteRequest;
import ec.turismvisitplanner.core.repository.LocationRepository;
import ec.turismvisitplanner.core.repository.RouteRepository;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RouteService {

    private RouteRepository routeRepository;
    private LocationRepository locationRepository;
    private S3Service s3Service;

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public ResponseEntity<?> createRoute(RouteRequest routeRequest, MultipartFile file) {
        File fileSaved;
        try {
             fileSaved = s3Service.uploadFile(file);
        } catch (IOException e) {
            throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
        }

        Route route =
                Route.builder().
                        name(routeRequest.getName()).
                        description(routeRequest.getDescription()).
                        location(findLocation(routeRequest.getIdLocation())).
                        minutes(routeRequest.getMinutes()).
                        hardness(routeRequest.getHardness()).
                        mainImage(fileSaved).build();
        Route createdRoute = routeRepository.save(route);
        return ResponseUtil.success(createdRoute);
    }

    public ResponseEntity<?> updateRoute(String id, RouteRequest routeRequest, MultipartFile file) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isEmpty()) {
            throw new CustomException("Ruta no encontrada", HttpStatus.NOT_FOUND.value());
        }
        Location location = findLocation(routeRequest.getIdLocation());
        Route _route = route.get();
        if (file != null && !file.isEmpty()) {
            s3Service.deleteFile(_route.getMainImage().getFullPath());
            File fileSaved;
            try {
                fileSaved = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
            }
            _route.setMainImage(fileSaved);
        }


        _route.setName(routeRequest.getName() != null ? routeRequest.getName() : _route.getName());
        _route.setDescription(routeRequest.getDescription() != null ? routeRequest.getDescription() : _route.getDescription());
        _route.setLocation(location != null ? location : _route.getLocation());
        _route.setMinutes(routeRequest.getMinutes() != null ? routeRequest.getMinutes() : _route.getMinutes());
        _route.setHardness(routeRequest.getHardness() != null ? routeRequest.getHardness() : _route.getHardness());
        Route routeUpdated = routeRepository.save(_route);
        return ResponseUtil.success(routeUpdated);
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
