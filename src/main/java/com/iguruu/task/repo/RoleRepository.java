package com.iguruu.task.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iguruu.task.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRolename(String rolename); 
}
