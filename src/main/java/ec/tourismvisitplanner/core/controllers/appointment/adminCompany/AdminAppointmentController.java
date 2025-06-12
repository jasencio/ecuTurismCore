package ec.tourismvisitplanner.core.controllers.appointment.adminCompany;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.payload.request.AssignGuideRequest;
import ec.tourismvisitplanner.core.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/appointments")
@RequiredArgsConstructor
public class AdminAppointmentController {
    
    private final AppointmentService appointmentService;

    @GetMapping()
    public List<Appointment> getAppointments() {
        return appointmentService.getCompanyAppointments();
    }

    @PostMapping("/{id}/assignGuide")
    public ResponseEntity<?> assignGuideAppointment(@PathVariable("id") String id, @RequestBody AssignGuideRequest assignGuideRequest) {
        try {
            Appointment appointment = appointmentService.assignGuideAppointment(id, assignGuideRequest.getIdTouristGuide());
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
