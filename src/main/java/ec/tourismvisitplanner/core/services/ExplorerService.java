package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.AppointmentStatus;
import ec.tourismvisitplanner.core.payload.request.AppointmentRequest;
import ec.tourismvisitplanner.core.repository.AppointmentRepository;
import ec.tourismvisitplanner.core.repository.OrganizationRepository;
import ec.tourismvisitplanner.core.repository.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExplorerService {

    private OrganizationRepository organizationRepository;
    private RouteRepository routeRepository;
    private AppointmentRepository appointmentRepository;

    public List<Organization> getOrganizations() {
        return organizationRepository.findByIsActiveTrue();
    }

    public Organization getOrganization(String idOrganization) {
        Optional<Organization> organization = organizationRepository.findByIdAndIsActiveTrue(idOrganization);
        return organization.orElse(null);
    }

    public List<Route> getRoutes(String idOrganization) {
        if (idOrganization == null) {
            return new ArrayList<>();
        }

        Optional<Organization> organization = organizationRepository.findByIdAndIsActiveTrue(idOrganization);
        if (organization.isEmpty()) {
            return new ArrayList<>();
        }
        return routeRepository.findByOrganizationAndIsActiveTrue(organization.get());
    }

    public Route getRoute(String idRoute) {
        if (idRoute == null) {
            return null;
        }
        Optional<Route> route = routeRepository.findByIdAndIsActiveTrue(idRoute);
        return route.orElse(null);
    }

    public Appointment createAppointment(AppointmentRequest appointmentRequest) {
        if (appointmentRequest.getRouteId() == null) {
            throw new RuntimeException("Route are required");
        }

        if (appointmentRequest.getEventDate() == null) {
            throw new RuntimeException("Event date is required");
        }

        if (appointmentRequest.getGroupSize() == null) {
            throw new RuntimeException("Group size is required");
        }

        if (appointmentRequest.getGroupSize() <= 0) {
            throw new RuntimeException("Group size must be greater than 0");
        }

        if (appointmentRequest.getEventTimeInit() == null || appointmentRequest.getEventTimeEnd() == null) {
            throw new RuntimeException("Event time init and end are required");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();


        Route route = findRoute(appointmentRequest.getRouteId());

        if (route == null) {
            throw new RuntimeException("Route not found");
        }

        // Convert time strings to Instant
        Instant eventTimeInit = convertTimeStringToInstant(appointmentRequest.getEventDate(), appointmentRequest.getEventTimeInit());
        Instant eventTimeEnd = convertTimeStringToInstant(appointmentRequest.getEventDate(), appointmentRequest.getEventTimeEnd());

        Appointment appointment = Appointment.builder()
                .tourist(userTmp)
                .route(route)
                .eventDate(appointmentRequest.getEventDate())
                .eventTimeInit(eventTimeInit)
                .eventTimeEnd(eventTimeEnd)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .groupSize(appointmentRequest.getGroupSize())
                .status(AppointmentStatus.PENDING)
                .build();

        return appointmentRepository.save(appointment);
    }

    private Instant convertTimeStringToInstant(Date date, String timeStr) {
        try {
            // Parse the time string (format: HH:mm)
            String[] timeParts = timeStr.split(":");
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);

            // Create a Calendar instance and set the date and time
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.toInstant();
        } catch (Exception e) {
            throw new RuntimeException("Invalid time format. Expected format: HH:mm (e.g., 08:00)");
        }
    }

    private Route findRoute(String idRoute) {
        if (idRoute != null) {
            Optional<Route> route = routeRepository.findByIdAndIsActiveTrue(idRoute);
            if (route.isPresent()) {
                return route.get();
            }
        }
        return null;
    }
}
