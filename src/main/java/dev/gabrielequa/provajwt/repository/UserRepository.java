package dev.gabrielequa.provajwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gabrielequa.provajwt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
