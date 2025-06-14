package ec.tourismvisitplanner.core.controllers.user.admincompany;

import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.services.adminCompany.GuidesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin-company/guides")
@AllArgsConstructor
public class GuidesAdminCompanyController {

    private final GuidesService guidesService;

    @GetMapping()
    public List<User> getGuides() {
        return guidesService.getGuides();
    }

    @GetMapping("/{id}")
    public User getGuide( @PathVariable String id){
        return guidesService.getGuide(id);
    }

}
