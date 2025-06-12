package ec.tourismvisitplanner.core.controllers.appointment.touristGuide;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tourist-guide/appointments")
@RequiredArgsConstructor
public class TouristGuideAppointmentController {
    
    private final AppointmentService appointmentService;

    @GetMapping()
    public List<Appointment> getAppointments() {
        return appointmentService.getTouristGuideAppointments();
    }

}
