package ec.tourismvisitplanner.core.controllers.user.admincompany;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.services.AdminCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/admin-company/appointments")
public class AppoinmentAdminCompanyController {

    private AdminCompanyService adminCompanyService;

    @GetMapping()
    public List<Appointment> getAppointments() {
        return adminCompanyService.getOrganizationAppointments();
    }

    @GetMapping("/{id}")
    public Optional<Appointment> getAppointment(@PathVariable String id) {
        return adminCompanyService.getOrganizationAppointment(id);
    }

    @PatchMapping("/{idAppointment}/assign-guide/{idTouristGuide}")
    public Optional<Appointment> assignGuideAppointment(@PathVariable String idAppointment, @PathVariable String idTouristGuide) {
        return adminCompanyService.assignGuideAppointment(idAppointment, idTouristGuide);
    }
}
