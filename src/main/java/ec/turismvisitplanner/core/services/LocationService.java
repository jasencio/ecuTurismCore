package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Route;
import ec.turismvisitplanner.core.payload.request.LocationRequest;
import ec.turismvisitplanner.core.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public List<Route> getAll() {
        return locationRepository.findAll();
    }

    public void createLocation(LocationRequest locationRequest) {

        Route location =
                Route.builder().
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
                        daysWeekDisabled(locationRequest.getDaysWeekDisabled()).build();

        locationRepository.save(location);

    }
}
