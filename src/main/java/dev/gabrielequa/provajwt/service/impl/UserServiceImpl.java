package dev.gabrielequa.provajwt.service.impl;

import dev.gabrielequa.provajwt.model.Role;
import dev.gabrielequa.provajwt.model.User;
import dev.gabrielequa.provajwt.repository.RoleRepository;
import dev.gabrielequa.provajwt.repository.UserRepository;
import dev.gabrielequa.provajwt.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service 
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            System.out.println("Utente non trovato nel database");
            throw new UsernameNotFoundException("Utente non trovato nel database");
        } else {
            System.out.println("Utente trovato nel database: " + username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public User saveUser(User user) {
        System.out.println("Salvo nuovo utente " + user.getName() + " nel database");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        System.out.println("Salvo nuovo ruolo " + role.getName() + " nel database");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        System.out.println("Aggiungo ruolo " + roleName + " all'utente " + username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        System.out.println("Recupero utente " + username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        System.out.println("Recupero tutti gli utenti");
        return userRepo.findAll();
    }
}