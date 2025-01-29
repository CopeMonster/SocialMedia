package me.alanton.userservice.repository;

import me.alanton.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);
}