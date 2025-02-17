package br.com.todoList.service;

import org.springframework.stereotype.Service;
import br.com.todoList.repository.*;
import br.com.todoList.entity.*;
import br.com.todoList.enums.*;
import br.com.todoList.dto.TaskDTO;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.*;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * @author fsdney
 */

@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskService.class);
    
    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    // Criar uma nova tarefa 
    public Task criarTarefa(Task task, Long userId) {
    	logger.info("Cadastrando nova tarefa para o usuário ID: {}", userId);
        meterRegistry.counter("tarefa.criar.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        User  user = userRepository.findById(userId)
        	    .orElseThrow(() -> {
        	        logger.warn("Usuario com ID {} não encontrado.", userId);
        	        return new RuntimeException("Usuario não encontrada.");
        	    });
        task.setUser (user);
        	
        Task tarefaCriada = taskRepository.save(task);
        
        logger.info("Tarefa cadastrada com sucesso: {}", tarefaCriada);
        meterRegistry.counter("tarefas.criadas.sucesso").increment(); 
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefa.criar.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        return tarefaCriada;
    }

    // Buscar tarefas de um usuário
    public List<Task> buscarTarefasPorUsuario(Long userId) {
        logger.info("Buscando tarefas do usuário ID: {}", userId);
        meterRegistry.counter("tarefasPorUsuario.listar.todas.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        if (userId == null || userId <= 0) {
        	logger.warn("O usuario com ID {} não existe.", userId);
            throw new RuntimeException("Usuario não encontrada.");	
        }
        User user = userRepository.findById(userId)
        		.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        
        List<Task> tarefas = taskRepository.buscarPorUsuario(user);
        
        if (tarefas.isEmpty()) {
        	logger.info("Nenhuma tarefa encontrada para o usuario com id {} ", userId);
        } else {
        	 logger.info("Tarefas do usuario {}: {}", userId, tarefas);
        }
       
        meterRegistry.counter("usuario.listar.todas.tarefas.sucesso").increment();
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefasPorUsuario.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        return taskRepository.buscarPorUsuario(user);
    }

 // Buscar tarefa por ID
    public Task buscarTarefaPorId(Long id) {
        logger.info("Buscando tarefa ID: {}", id);
        meterRegistry.counter("tarefas.buscarPorID.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        if (id == null || id <= 0) {
            logger.warn("ID da tarefa inválido: {}", id);
            meterRegistry.counter("tarefas.buscarPorID.falha").increment();
            throw new IllegalArgumentException("ID da tarefa deve ser um número positivo.");
        }

        Optional<Task> tarefaOpt = taskRepository.findById(id);
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefas.buscarPorID.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);

        return tarefaOpt.orElseThrow(() -> {
            logger.warn("Tarefa com ID {} não encontrada!", id);
            meterRegistry.counter("tarefas.buscarPorID.falha").increment();
            logger.info("Tarefa com ID {}: ", id);
            return new RuntimeException("Tarefa não encontrada!");
        });
    }        
    

    // Atualizar tarefa
    public Task atualizarTarefa(Long id, Task atualizarTarefa, Long userId) {
        logger.info("Atualizando tarefa ID: {} para o usuário ID: {}", id, userId);
        meterRegistry.counter("tarefa.atualizar.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        Task existeTarefa = buscarTarefaPorId(id);
        
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (atualizarTarefa.getTitle() == null || atualizarTarefa.getTitle().isEmpty()) {
        	logger.warn("Titulo invalido!");
            throw new RuntimeException("Titulo Invalido!");
        }  existeTarefa.setTitle(atualizarTarefa.getTitle());
        
        if (atualizarTarefa.getDescription() == null || atualizarTarefa.getDescription().isEmpty()) {
        	logger.warn("Descrição invalida!");
            throw new RuntimeException("Descrição Inavlida!");
        }   existeTarefa.setDescription(atualizarTarefa.getDescription());
        
        if (atualizarTarefa.getStatus() == null) {
        	logger.warn("Status invalido!");
            throw new RuntimeException("Status Inavlido!");
        }   existeTarefa.setStatus(atualizarTarefa.getStatus());
        	
        if (atualizarTarefa.getDeadline() == null) {
        	logger.warn("Prazo invalido!");
            throw new RuntimeException("Prazo Inavlido!");
        }   existeTarefa.setDeadline(atualizarTarefa.getDeadline());
       
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefa.atualizar.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        
        logger.info("Tarefa atualizada com sucesso");
        return taskRepository.save(existeTarefa);
    }

    // Deletar tarefa
    public void deletarTarefa(Long id) {
        logger.info("Deletando tarefa ID: {}", id);
        meterRegistry.counter("tarefa.deletar.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        if (!taskRepository.existsById(id)) {
            logger.error("Tarefa com ID {} não encontrada!", id);
            meterRegistry.counter("tarefa.deletar.falha").increment();
            throw new RuntimeException("Tarefa não encontrada!");
        }
        
        long endTime = System.currentTimeMillis();
        meterRegistry.counter("tarefa.deletar.sucesso").increment();
        meterRegistry.timer("tarefa.atualizar.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        logger.info("Tarefa deletada com sucesso");
        taskRepository.deleteById(id);
    }

    // Filtrar tarefas por status
    public List<Task> buscarTarefaPorStatus(StatusTask status) {
        logger.info("Buscando tarefas com status: {}", status);
        meterRegistry.counter("tarefas.filtrar.por.status.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        List<Task> tarefas = taskRepository.buscarPorStatus(status);

        if (tarefas.isEmpty()) {
            logger.warn("Nenhuma tarefa encontrada com status: {}", status);
            meterRegistry.counter("tarefa.filtrar.por.status.falha").increment();
            throw new RuntimeException("Tarefa não encontrada!");
        }
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefa.filtrar.por.status.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        logger.info("Tarefas com tatus {} ", status);
        meterRegistry.counter("tarefas.filtrar.status", "status", status.name()).increment();
        return tarefas;
    }

    // Filtrar tarefas com prazo expirado**
    public List<Task> buscarTarefasExpiradas() {
        logger.info("Buscando tarefas com prazo expirado até {}", LocalDate.now());
        long startTime = System.currentTimeMillis();
        meterRegistry.counter("tarefa.filtrar.por.prazo.chamadas").increment();
        
        
        List<Task> tarefas = taskRepository.buscarPorPrazoExpirado(LocalDate.now());

        if (tarefas.isEmpty()) {
            logger.warn("Nenhuma tarefa com prazo expirado foi encontrada.");
            meterRegistry.counter("tarefa.filtrar.por.prazo.falha").increment();
            throw new RuntimeException("Tarefa não encontrada!");
        }
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefa.atualizar.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        meterRegistry.counter("tarefa.filtrar.por.prazo.sucesso").increment();
        logger.info("Tarefas com prazo expirado");
        return tarefas;
    }

    // Ordenar tarefas por prioridade
    public List<Task> buscarTarefasPorPrioridade(PriorityTask priority) {
        logger.info("Buscando tarefas com prioridade {}.", priority);
        long startTime = System.currentTimeMillis();
        meterRegistry.counter("tarefa.filtrar.por.prioridade.chamadas").increment();
        
        List<Task> tarefas = taskRepository.buscarPorPrioridadeEspecifica(priority);

        if (tarefas.isEmpty()) {
            logger.warn("Nenhuma tarefa encontrada com prioridade {} .", priority);
            meterRegistry.counter("tarefa.filtrar.por.prioridade.falha").increment();
            throw new RuntimeException("Tarefa não encontrada!");
        }
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefa.filtrar.por.prioridade.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        meterRegistry.counter("tarefa.filtrar.por.prioridade.sucesso").increment();
        logger.info("Tarefas com prioridade {}", priority);
        return tarefas;
    }

    // Filtrar tarefas por status e ordenar por prioridade
    public List<Task> buscarTarefaPorStatusFiltrar(StatusTask status, PriorityTask priority) {
        logger.info("Buscando tarefas com status: {} e ordenando por prioridade.", status);
        long startTime = System.currentTimeMillis();
        meterRegistry.counter("tarefa.filtrar.por.status_prioridade.chamadas").increment();
        
        List<Task> tarefas = taskRepository.buscarPorStatusFiltrarPorPrioridade(status, priority);

        if (tarefas.isEmpty()) {
            logger.warn("Nenhuma tarefa encontrada com status {} para ordenação por prioridade.", status);
            throw new RuntimeException("Tarefa não encontrada!");
        }
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("tarefa.filtrar.por.prioridade.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
        meterRegistry.counter("tasks.filtro.status_prioridade.sucesso", "status", status.name()).increment();
        logger.info("Tarefas com status {}, ordenadas por prioridade", status);
        return tarefas;
    }

    // Filtrar tarefas por usuário e status
    public List<Task> buscarTarefaPorUsuarioStatus(Long userId, StatusTask status) {
        logger.info("Buscando tarefas do usuário ID: {} com status: {}", userId, status);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("Usuário com ID {} não encontrado!", userId);
                    return new RuntimeException("Usuário não encontrado!");
                });

        List<Task> tarefas = taskRepository.buscarPorUsuarioEStatus(user, status);

        if (tarefas.isEmpty()) {
            logger.warn("Nenhuma tarefa encontrada para o usuário ID {} com status {}.", userId, status);
        }

        meterRegistry.counter("tasks.filtro.usuario_status", "status", status.name()).increment();
        return tarefas;
    }
}   