package br.com.todoList.controller;

import org.springframework.web.bind.annotation.*;
import org.slf4j.*;
import br.com.todoList.service.TaskService;
import br.com.todoList.entity.Task;
import br.com.todoList.enums. *;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;


/**
 * @author fsdney
 */

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private final Logger logger = LoggerFactory.getLogger(TaskController.class);
    
    @Autowired
    private TaskService taskService;


    @PostMapping
    public ResponseEntity<Task> criarTarefa(@RequestBody Task task, @RequestParam Long userId) {
        Task tarefaCriada = taskService.criarTarefa(task, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    
    @GetMapping
    public ResponseEntity<List<Task>> buscarTarefasPorUsuario(@RequestParam Long userId) {
    	logger.info("Recebida requisição para buscar tarefas Por usuario ");
    	List<Task> tarefas = taskService.buscarTarefasPorUsuario(userId);
        return ResponseEntity.ok(tarefas);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarTarefaPorId(@PathVariable Long id) {
    	logger.info("Recebida requisição para buscar tarefas por id");
    	Task tarefaOpt = taskService.buscarTarefaPorId(id);
    	return ResponseEntity.ok(tarefaOpt);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Task> atualizarTarefa(@PathVariable Long id, @RequestBody Task task, @RequestParam Long userId) {
    	logger.info("Recebida requisição para atualizar as informações de uma tarefa");
    	Task tarefaAtualizada = taskService.atualizarTarefa(id, task, userId);
    	return ResponseEntity.ok(tarefaAtualizada);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
    	logger.info("Recebida requisição para deletar tarefa");
    	taskService.deletarTarefa(id);
    	return ResponseEntity.noContent().build();
    }

 
    @GetMapping("/status")
    public ResponseEntity<List<Task>> buscarTarefaPorStatus(@RequestParam StatusTask status) {
    	logger.info("Recebida requisição para buscar tarefa por status");
    	List<Task> buscarPorStatus = taskService.buscarTarefaPorStatus(status);
    	return ResponseEntity.ok(buscarPorStatus);
    }

    
    @GetMapping("/expiradas")
    public ResponseEntity<List<Task>> buscarTarefasExpiradas() {
    	logger.info("Recebida requisição para buscar tarefas com prazo expirado");
    	List<Task> buscarExpiradas = taskService.buscarTarefasExpiradas();
    	return ResponseEntity.ok(buscarExpiradas);
    }

    @GetMapping("/prioridade")
    public ResponseEntity<List<Task>> buscarTarefasPorPrioridade(@RequestParam PriorityTask priority) {
    	logger.info("Recebida requisição para buscar tarefas por prioridade");
    	List<Task> buscarPorPrioridade = taskService.buscarTarefasPorPrioridade(priority);
    	return ResponseEntity.ok(buscarPorPrioridade);
    }

    
    @GetMapping("/status-prioridade")
    public ResponseEntity<List<Task>> buscarTarefaPorStatusFiltrar(@RequestParam StatusTask status, @RequestParam PriorityTask priority) {
    	logger.info("Recebida requisição para buscar tarefa por status e filtrar por prioridade");
        List<Task> buscarPorStatusFiltrarPrioridade = taskService.buscarTarefaPorStatusFiltrar(status, priority);
        return ResponseEntity.ok(buscarPorStatusFiltrarPrioridade);
    }

   
    @GetMapping("/usuario-status")
    public ResponseEntity<List<Task>> buscarTarefaPorUsuarioStatus(@RequestParam Long userId, @RequestParam StatusTask status) {
    	logger.info("Recebida requisição para buscar tarefa por usuario filtrar por status");
        List<Task> buscarPorUsuarioFiltrarStatus = taskService.buscarTarefaPorUsuarioStatus(userId, status);
        return ResponseEntity.ok(buscarPorUsuarioFiltrarStatus);
    }
}