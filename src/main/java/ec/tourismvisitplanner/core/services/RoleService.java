package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Role;
import ec.tourismvisitplanner.core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
