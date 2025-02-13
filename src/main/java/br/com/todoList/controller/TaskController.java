package br.com.todoList.controller;

import org.springframework.web.bind.annotation. *;

import br.com.todoList.service.TaskService;
import br.com.todoList.entity.*;
import br.com.todoList.enums.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/tasks")
public class TaskController {
	 
	@Autowired
	private TaskService taskService;
	
	// ✅ Criar uma tarefa associada a um usuário
    @PostMapping
    public Task criarTarefa(@RequestBody Task task, @RequestParam Long userId) {
        return taskService.criarTarefa(task, userId);
    }

    // ✅ Listar todas as tarefas de um usuário
    @GetMapping
    public List<Task> buscarTarefasPorUsuario(@RequestParam Long userId) {
        return taskService.buscarTarefasPorUsuario(userId);
    }

    // ✅ Buscar uma tarefa por ID
    @GetMapping("/{id}")
    public Task buscarTarefaPorId(@PathVariable Long id) {
        return taskService.buscarTarefaPorId(id);
    }

    // ✅ Atualizar uma tarefa (requer um usuário válido)
    @PutMapping("/{id}")
    public Task atualizarTarefa(@PathVariable Long id, @RequestBody Task task, @RequestParam Long userId) {
        return taskService.atualizarTarefa(id, task, userId);
    }

    // ✅ Excluir uma tarefa
    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable Long id) {
        taskService.deletarTarefa(id);
    }

    // ✅ Filtrar por status
    @GetMapping("/status")
    public List<Task> buscarTarefaPorStatus(@RequestParam StatusTask status) {
        return taskService.buscarTarefaPorStatus(status);
    }

    // ✅ Filtrar tarefas com prazo expirado
    @GetMapping("/expiradas")
    public List<Task> buscarTarefasExpiradas() {
        return taskService.buscarTarefasExpiradas();
    }

    // ✅ Ordenar por prioridade
    @GetMapping("/prioridade")
    public List<Task> buscarTarefasPorPrioridade() {
        return taskService.buscarTarefasPorPrioridade();
    }

    // ✅ Filtrar por status e ordenar por prioridade
    @GetMapping("/status-prioridade")
    public List<Task> buscarTarefaPorStatusFiltrar(@RequestParam StatusTask status) {
        return taskService.buscarTarefaPorStatusFiltrar(status);
    }

    // ✅ Filtrar por usuário e status
    @GetMapping("/usuario-status")
    public List<Task> buscarTarefaPorUsuarioStatus(@RequestParam Long userId, @RequestParam StatusTask status) {
        return taskService.buscarTarefaPorUsuarioStatus(userId, status);
    }
}