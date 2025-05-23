package dev.gabrielequa.provajwt.service;

import java.util.List;

import dev.gabrielequa.provajwt.model.Role;
import dev.gabrielequa.provajwt.model.User;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User>getUsers();
}
