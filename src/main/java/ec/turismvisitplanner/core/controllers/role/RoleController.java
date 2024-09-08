package ec.turismvisitplanner.core.controllers.role;

import ec.turismvisitplanner.core.models.Role;
import ec.turismvisitplanner.core.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(                      )
    public List<Role> getRoles() {
        return roleService.getAll();
    }

}
