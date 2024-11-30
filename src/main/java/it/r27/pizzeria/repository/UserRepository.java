package it.r27.pizzeria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.r27.pizzeria.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
}
