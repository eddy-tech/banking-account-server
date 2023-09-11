package com.java.bankingaccount.repositories;

import com.java.bankingaccount.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}