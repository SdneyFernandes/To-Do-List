package br.com.todoList.service;

import org.springframework.stereotype.Service;
import br.com.todoList.repository.*;
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
 @Autowired
 private UserRepository userRepository;
 
//✅ Criar uma nova tarefa (somente se o usuário existir)
 public Task criarTarefa(Task task, Long userId) {
     User user = userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

     task.setUser(user);  // Garante que a tarefa pertence a um usuário existente
     return taskRepository.save(task);
 }

 // ✅ Buscar tarefas de um usuário (verifica se o usuário existe)
 public List<Task> buscarTarefasPorUsuario(Long userId) {
     User user = userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
     return taskRepository.buscarPorUsuario(user);
 }

 // ✅ Buscar uma tarefa por ID
 public Task buscarTarefaPorId(Long id) {
     return taskRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
 }

 // ✅ Atualizar uma tarefa (garante que a tarefa pertence a um usuário existente)
 public Task atualizarTarefa(Long id, Task atualizarTarefa, Long userId) {
     Task existeTarefa = buscarTarefaPorId(id);
     
     // Verifica se o usuário existe antes de atualizar a tarefa
     userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

     if (atualizarTarefa.getTitle() != null) existeTarefa.setTitle(atualizarTarefa.getTitle());
     if (atualizarTarefa.getDescription() != null) existeTarefa.setDescription(atualizarTarefa.getDescription());
     if (atualizarTarefa.getStatus() != null) existeTarefa.setStatus(atualizarTarefa.getStatus());
     if (atualizarTarefa.getDeadline() != null) existeTarefa.setDeadline(atualizarTarefa.getDeadline());

     return taskRepository.save(existeTarefa);
 }

 // ✅ Deletar tarefa (verifica se a tarefa existe antes de deletar)
 public void deletarTarefa(Long id) {
     if (!taskRepository.existsById(id)) {
         throw new RuntimeException("Tarefa não encontrada!");
     }
     taskRepository.deleteById(id);
 }

 // ✅ Filtrar por status
 public List<Task> buscarTarefaPorStatus(StatusTask status) {
     return taskRepository.buscarPorStatus(status);
 }

 // ✅ Filtrar por prazo expirado
 public List<Task> buscarTarefasExpiradas() {
     return taskRepository.buscarPorPrazoExpirado(LocalDate.now());
 }

 // ✅ Ordenar por prioridade
 public List<Task> buscarTarefasPorPrioridade() {
     return taskRepository.buscarPorPrioridade();
 }

 // ✅ Filtrar por status e ordenar por prioridade
 public List<Task> buscarTarefaPorStatusFiltrar(StatusTask status) {
     return taskRepository.buscarPorStatusFiltrarPorPrioridade(status);
 }

 // ✅ Filtrar tarefas por usuário e status (verifica se o usuário existe)
 public List<Task> buscarTarefaPorUsuarioStatus(Long userId, StatusTask status) {
     User user = userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
     return taskRepository.buscarPorUsuarioEStatus(user, status);
 }
}