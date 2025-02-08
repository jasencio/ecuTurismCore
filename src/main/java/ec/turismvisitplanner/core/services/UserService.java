package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.models.Role;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.UserRequest;
import ec.turismvisitplanner.core.repository.OrganizationRepository;
import ec.turismvisitplanner.core.repository.RoleRepository;
import ec.turismvisitplanner.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    PasswordEncoder encoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User createUser(UserRequest userRequest) {
        User user = User.
                builder().
                name(userRequest.getName()).
                email(userRequest.getEmail()).
                phone(userRequest.getPhone()).
                roles(findRoles(userRequest.getIdRoles())).
                organization(findOrganization(userRequest.getIdOrganization())).
                password(encoder.encode(userRequest.getPassword())).build();
        return userRepository.save(user);
    }

    public User updateUser(String id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            Organization organization = findOrganization(userRequest.getIdOrganization());
            Set<Role> roles = findRoles(userRequest.getIdRoles());
            User _user = user.get();
            _user.setName(userRequest.getName()!=null?userRequest.getName():_user.getName());
            _user.setEmail(userRequest.getEmail()!=null?userRequest.getEmail():_user.getEmail());
            _user.setPhone(userRequest.getPhone()!=null?userRequest.getPhone():_user.getPhone());
            _user.setPassword(userRequest.getPassword()!=null?encoder.encode(userRequest.getPassword()):_user.getPassword());
            _user.setOrganization(organization != null ? organization
                    : _user.getOrganization());
            _user.setRoles(roles!=null? roles : _user.getRoles());
            return userRepository.save(_user);
        } else {
            return null;
        }
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    private Set<Role> findRoles(Set<String> idRoles){
        if(idRoles!=null){
            Set<Role> roles = new HashSet<>();
            for (String idRole : idRoles) {
                Optional<Role> auxRole= roleRepository.findById(idRole);
                auxRole.ifPresent(roles::add);
            }
           return roles;
        }
        return null;
    }

    private Organization findOrganization(String idOrganizations){
        if(idOrganizations!=null){
            Optional<Organization> organization = organizationRepository.findById(idOrganizations);
            return organization.orElse(null);
        }
        return null;
    }
}
