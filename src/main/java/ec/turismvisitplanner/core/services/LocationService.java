package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Location;
import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.payload.request.LocationRequest;
import ec.turismvisitplanner.core.repository.LocationRepository;
import ec.turismvisitplanner.core.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location createLocation(LocationRequest locationRequest) {

        Location location =
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

        return locationRepository.save(location);
    }

    public Location updateLocation(String id, LocationRequest locationRequest) {
        Optional<Location> location = locationRepository.findById(id);

        if (location.isPresent()) {
            Organization organization = findOrganization(locationRequest.getIdOrganization());
            Location _location = location.get();
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
            return locationRepository.save(_location);
        } else {
            return null;
        }
    }

    private Organization findOrganization(String idOrganizations) {
        if (idOrganizations != null) {
            Optional<Organization> organization = organizationRepository.findById(idOrganizations);
            return organization.orElse(null);
        }
        return null;
    }

}
