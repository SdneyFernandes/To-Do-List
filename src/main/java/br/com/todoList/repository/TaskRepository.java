package br.com.todoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todoList.entity.*;


/**
 * @author fsdney
 */

public interface TaskRepository extends JpaRepository<Task, Long>{

}


