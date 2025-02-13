package br.com.todoList.service;

import org.springframework.stereotype.Service;
import br.com.todoList.repository.TaskRepository;
import br.com.todoList.entity.*;
import br.com.todoList.enums. *;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * @author fsdney
 */

@Service
public class TaskService {
	
 @Autowired
 private TaskRepository taskRepository;
 
 //Criar uma nova tarefa
 public Task criarTarefa(Task task) {
	 return taskRepository.save(task);
 }
 
 //Buscar tarefas de um usuario
 public List<Task> buscarTarefasPorUsuario(User user) {
	 return taskRepository.buscarPorUsuario(user);
 }
 
 //Buscar uma tarefa por ID
 public Task buscarTarefaPorId(Long id) {
	 return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
			 
 }

 
 //Atualizar uma tarefa
 public Task atualizarTarefa(Long id, Task atualizarTarefa) {
	 Task existeTarefa = buscarTarefaPorId(id);
	 
	 if (atualizarTarefa.getTitle() != null) existeTarefa.setTitle(atualizarTarefa.getTitle());
	 if (atualizarTarefa.getDescription() != null) existeTarefa.setDescription(atualizarTarefa.getDescription());
	 if (atualizarTarefa.getStatus() != null) existeTarefa.setStatus(atualizarTarefa.getStatus());
	 if (atualizarTarefa.getDeadline() != null) existeTarefa.setDeadline(atualizarTarefa.getDeadline());
	 
	 return taskRepository.save(existeTarefa);
 }
 
 //Deletar Tarefa
 public void deletarTarefa(Long id) {
	 taskRepository.deleteById(id);
 }
 
 //filtrar por status
 public List<Task> buscarTarefaPorStatus(StatusTask status) {
	 return taskRepository.buscarPorStatus(status);
 }
 
 //ltrar por prazo expirado
 public List<Task> buscarTarefasExpiradas() {
	 return taskRepository.buscarPorPrazoExpirado(LocalDate.now());
 }
 
 //ordenar por prioridade
 public List<Task> buscarTarefasPorPrioridade() {
	 return taskRepository.buscarPorPrioridade();
 }
 
 //filtrar por status e ordenar por prioridade
 public List<Task> buscarTarefaPorStatusFiltrar(StatusTask status) {
	 return taskRepository.buscarPorStatusFiltrarPorPrioridade(status);
 }
 
 //fitrar tarefas por usuario e status
 public List<Task> buscarTarefaPorUsuarioStatus(User user, StatusTask status) {
	 return taskRepository.buscarPorUsuarioEStatus(user, status);
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
}
