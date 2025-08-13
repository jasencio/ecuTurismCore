package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.AppointmentStatus;
import ec.tourismvisitplanner.core.payload.request.OrganizationRequest;
import ec.tourismvisitplanner.core.repository.AppointmentRepository;
import ec.tourismvisitplanner.core.repository.OrganizationRepository;
import ec.tourismvisitplanner.core.repository.RouteRepository;
import ec.tourismvisitplanner.core.repository.UserRepository;
import ec.tourismvisitplanner.core.utils.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminCompanyService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationService organizationService;
    private final AppointmentRepository appointmentRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public Organization getOrganization(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        Optional<Organization> organization =  organizationRepository.findById( userTmp.getOrganization().getId());
        return organization.orElse(null);
    }

    public Organization updateOrganization(OrganizationRequest organizationRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        if(userTmp.getOrganization()!= null){
            return organizationService.updateOrganization(userTmp.getOrganization().getId(), organizationRequest);
        }
        return null;
    }

    public List<Appointment> getOrganizationAppointments() {
        User user = SessionUtils.getUserOnSession();
        List<Route> routeList = routeRepository.findByOrganization(user.getOrganization());
        List<Appointment> appointmentList = new ArrayList<>();
        for(Route route : routeList){
            appointmentList.addAll(appointmentRepository.findByRouteAndStatus(route, AppointmentStatus.PENDING));
        }
        return appointmentList;
    }

    public Optional<Appointment> getOrganizationAppointment(String appointmentId) {
        User user = SessionUtils.getUserOnSession();

        Optional<Appointment> appointment =  appointmentRepository.findById(appointmentId);
        if(appointment.isPresent()&& user.getOrganization().getId().equals(appointment.get().getRoute().getOrganization().getId())){
            return appointment;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Appointment> assignGuideAppointment(String idAppointment, String idTouristGuide) {
        User user = SessionUtils.getUserOnSession();
        Optional<Appointment> appointment = getOrganizationAppointment(idAppointment);
        if(appointment.isPresent() && user.getOrganization().getId().equals(appointment.get().getRoute().getOrganization().getId())){
            appointment.get().setTouristGuide(userRepository.findById(idTouristGuide).orElse(null));
            return Optional.of(appointmentRepository.save(appointment.get()));
        }
        return Optional.empty();
    }
}
