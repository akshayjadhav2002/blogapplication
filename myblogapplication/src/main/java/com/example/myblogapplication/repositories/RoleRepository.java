package com.example.myblogapplication.repositories;

import com.example.myblogapplication.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
