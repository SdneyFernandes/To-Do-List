package br.com.todoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import br.com.todoList.entity.*;
import br.com.todoList.enums. *;



/**
 * @author fsdney
 */

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	// Buscar tarefas por usuário
    @Query("SELECT t FROM Task t WHERE t.user = :user")
    List<Task> buscarPorUsuario(@Param("user") User user);

    // Buscar tarefas por status
    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> buscarPorStatus(@Param("status") StatusTask status);

    // Buscar tarefas com prazo expirado
    @Query("SELECT t FROM Task t WHERE t.deadline < :data")
    List<Task> buscarPorPrazoExpirado(@Param("data") LocalDate data);

    // Buscar tarefas ordenadas por prioridade (maior para menor)
    @Query("SELECT t FROM Task t ORDER BY t.priority DESC")
    List<Task> buscarPorPrioridade();

    // Buscar tarefas por status e ordenar por prioridade
    @Query("SELECT t FROM Task t WHERE t.status = :status ORDER BY t.priority DESC")
    List<Task> buscarPorStatusFiltrarPorPrioridade(@Param("status") StatusTask status);

    // Buscar tarefas por usuário e status
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.status = :status")
    List<Task> buscarPorUsuarioEStatus(@Param("user") User user, @Param("status") StatusTask status);
}