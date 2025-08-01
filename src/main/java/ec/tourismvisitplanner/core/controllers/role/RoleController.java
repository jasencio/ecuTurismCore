package ec.tourismvisitplanner.core.controllers.role;

import ec.tourismvisitplanner.core.models.Role;
import ec.tourismvisitplanner.core.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(                      )
    public List<Role> getRoles() {
        return roleService.getAll();
    }

}
