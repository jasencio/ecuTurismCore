package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Location;
import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.LocationRequest;
import ec.turismvisitplanner.core.repository.LocationRepository;
import ec.turismvisitplanner.core.repository.OrganizationRepository;
import ec.turismvisitplanner.core.utils.I18n;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import ec.turismvisitplanner.core.utils.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    private final OrganizationRepository organizationRepository;

    private final I18n i18n;

    public List<Location> getLocations() {
        User user = SessionUtils.getUserOnSession();
        if (SessionUtils.isAdmin(user)) {
            return locationRepository.findAll();
        }
        return locationRepository.findByOrganizationIdAndDeletedAtIsNull(user.getOrganization().getId());
    }

    public ResponseEntity<?> createLocation(LocationRequest locationRequest) {
        Location locationToCreate =
                Location.builder().
                        name(locationRequest.getName()).
                        description(locationRequest.getDescription()).
                        address(locationRequest.getAddress()).
                        latitude(locationRequest.getLatitude()).
                        longitude(locationRequest.getLongitude()).
                        timeOpenWeek(locationRequest.getTimeOpenWeek()).
                        timeCloseWeek(locationRequest.getTimeCloseWeek()).
                        timeOpenSaturday(locationRequest.getTimeOpenSaturday()).
                        timeCloseSaturday(locationRequest.getTimeCloseSaturday()).
                        timeOpenSunday(locationRequest.getTimeOpenSunday()).
                        timeCloseSunday(locationRequest.getTimeCloseSunday()).
                        daysWeekDisabled(locationRequest.getDaysWeekDisabled()).
                        organization(findOrganization(locationRequest.getIdOrganization())).build();
        Location location = locationRepository.save(locationToCreate);
        return ResponseUtil.success(i18n.getMessage("location.update.success", "Ubicación actualizada con éxito"), location);
    }

    public ResponseEntity<?> updateLocation(String id, LocationRequest locationRequest) {
        Optional<Location> locationToUpdate = locationRepository.findById(id);

        if (locationToUpdate.isEmpty()) {
            return ResponseUtil.error(i18n.getMessage("location.notFound", "Ubicación no encontrada"), HttpStatus.NOT_FOUND);
        }

        Organization organization = findOrganization(locationRequest.getIdOrganization());
        Location _location = locationToUpdate.get();
        _location.setName(locationRequest.getName() != null ? locationRequest.getName() : _location.getName());
        _location.setDescription(locationRequest.getDescription() != null ? locationRequest.getDescription() : _location.getDescription());
        _location.setAddress(locationRequest.getAddress() != null ? locationRequest.getAddress() : _location.getAddress());
        _location.setLatitude(locationRequest.getLatitude() != null ? locationRequest.getLatitude() : _location.getLatitude());
        _location.setLongitude(locationRequest.getLongitude() != null ? locationRequest.getLongitude() : _location.getLongitude());
        _location.setTimeOpenWeek(locationRequest.getTimeOpenWeek() != null ? locationRequest.getTimeOpenWeek() : _location.getTimeOpenWeek());
        _location.setTimeCloseWeek(locationRequest.getTimeCloseWeek() != null ? locationRequest.getTimeCloseWeek() : _location.getTimeCloseWeek());
        _location.setTimeOpenSaturday(locationRequest.getTimeOpenSaturday() != null ? locationRequest.getTimeOpenSaturday() : _location.getTimeOpenSaturday());
        _location.setTimeCloseSaturday(locationRequest.getTimeCloseSaturday() != null ? locationRequest.getTimeCloseSaturday() : _location.getTimeCloseSaturday());
        _location.setTimeOpenSunday(locationRequest.getTimeOpenSunday() != null ? locationRequest.getTimeOpenSunday() : _location.getTimeOpenSunday());
        _location.setTimeCloseSunday(locationRequest.getTimeCloseSunday() != null ? locationRequest.getTimeCloseSunday() : _location.getTimeCloseSunday());
        _location.setDaysWeekDisabled(locationRequest.getDaysWeekDisabled() != null ? locationRequest.getDaysWeekDisabled() : _location.getDaysWeekDisabled());
        _location.setOrganization(organization != null ? organization : _location.getOrganization());
        Location location = locationRepository.save(_location);
        return ResponseUtil.success(i18n.getMessage("location.update.success", "Ubicación actualizada con éxito"), location);

    }

    public ResponseEntity<?> deleteLocation(String id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            return ResponseUtil.error(i18n.getMessage("location.notFound", "Ubicación no encontrada"), HttpStatus.NOT_FOUND);
        }
        Location locationToDelete = location.get();
        locationToDelete.setDeletedAt(new Date());
        locationRepository.save(locationToDelete);
        return ResponseUtil.success(i18n.getMessage("location.delete.success", "Ubicación eliminada con éxito"));
    }

    private Organization findOrganization(String idOrganizations) {
        if (idOrganizations != null) {
            Optional<Organization> organization = organizationRepository.findById(idOrganizations);
            return organization.orElse(null);
        }
        return null;
    }

}
