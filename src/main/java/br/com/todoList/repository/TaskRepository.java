package br.com.todoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import br.com.todoList.entity.*;
import br.com.todoList.enums. *;



/**
 * @author fsdney
 */

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	//Retorna todas as tarefas associadas a um usuario especifico
	//O Spring Data JPA interpreta esse nome(user) e gera automaticamente a consulta SQL
	List<Task> buscarPorUsuario(User user);
	
	//Recebe um status como paramentro e retorna todas as tarefas com esse status
	List<Task> buscarPorStatus(StatusTask status);
	
	//Retorna as tarefas com prazo expirado
	//O metodo verifica a data limite(deadline), O Spring Data JPA gera a query WHERE deadline<data> 
	List<Task> buscarPorPrazoExpirado(LocalDate data);
	
	//Retorna as tarefas ordenadas da mais alta a mais baixa prioridade
	List<Task> buscarPorPrioridade();
	
	//primeiro filtra pelo ststaus depois ordena por prioridade
	List<Task> buscarPorStatusFiltrarPorPrioridade(StatusTask status);
	
	//Filtra por usuario e depois pelo status
	List<Task> buscarPorUsuarioEStatus(User user, StatusTask status);
	
	

}


