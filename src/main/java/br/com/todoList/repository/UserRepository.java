package br.com.todoList.repository;


import br.com.todoList.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fsdney
 */

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByName(String name);
}
