package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.Role;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.payload.request.UserRequest;
import ec.tourismvisitplanner.core.repository.OrganizationRepository;
import ec.tourismvisitplanner.core.repository.RoleRepository;
import ec.tourismvisitplanner.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> getOne(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public User createUser(UserRequest userRequest) {
        User user = User.
                builder().
                name(userRequest.getName()).
                email(userRequest.getEmail()).
                phone(userRequest.getPhone()).
                roles(userRequest.getRoles()).
                organization(findOrganization(userRequest.getIdOrganization())).
                password(encoder.encode(userRequest.getPassword())).build();
        return userRepository.save(user);
    }

    public User updateUser(String id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            Organization organization = findOrganization(userRequest.getIdOrganization());
            User _user = user.get();
            _user.setName(userRequest.getName() != null ? userRequest.getName() : _user.getName());
            _user.setEmail(userRequest.getEmail() != null ? userRequest.getEmail() : _user.getEmail());
            _user.setPhone(userRequest.getPhone() != null ? userRequest.getPhone() : _user.getPhone());
            _user.setPassword(userRequest.getPassword() != null ? encoder.encode(userRequest.getPassword()) : _user.getPassword());
            _user.setOrganization(organization != null ? organization
                    : _user.getOrganization());
            _user.setRoles(userRequest.getRoles() != null ? userRequest.getRoles() : _user.getRoles());
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
