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
	
	@PostMapping
	public Task novaTarefa(@RequestBody Task task) {
		return taskService.criarTarefa(task);
	}
	
	@GetMapping
	public List<Task> listarTarefasPorUsuario(@RequestParam(required = false) Long userId) {
		if (userId != null) {
			User user = new User();
			user.setId(userId);
			return taskService.buscarTarefasPorUsuario(user);
		}
		return taskService.buscarTarefasPorPrioridade();
	}
	
	@GetMapping("/{id}")
	public Task encontrarTarefaPorId(@PathVariable Long id) {
		return taskService.buscarTarefaPorId(id);
		
	}
	
	@PutMapping("/{id}")
	public Task atualizarTarefaPorId(@PathVariable Long id, @RequestBody Task task) {
		return taskService.atualizarTarefa(id,  task);
	}
	
	@DeleteMapping("/{id}")
	public void deletarTarefaPorId(@PathVariable Long id) {
		taskService.deletarTarefa(id);
	}
	
	@GetMapping("/filter/status")
	public List<Task> filtrarTarefasPorStatus(@RequestParam StatusTask status) {
		return taskService.buscarTarefaPorStatus(status);
	}
	
	@GetMapping("/filter/expired")
	public List<Task> filtrarTarefasPorPrazo() {
		return taskService.buscarTarefasExpiradas();
	}
	
	@GetMapping("/filter/priority")
	public List<Task> filtrarTarefasPorPrioridade() {
		return taskService.buscarTarefasPorPrioridade();
	}
	
	@GetMapping("/filter/status-priority")
	public List<Task> filtrarTarefasPorStatusEPrioridade(@RequestParam StatusTask status) {
		return taskService.buscarTarefaPorStatusFiltrar(status);
	}
	
	@GetMapping("/filter/user-status")
	public List<Task> filtrarTarefasPorUsuarioEStatus(@RequestParam Long userId, @RequestParam statusTask status) {
		User user = new User();
		user.serId(userId);
		return taskService.buscarTarefaPorUsuarioStatus(user,  status);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
