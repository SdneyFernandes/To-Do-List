package br.com.todoList.dto;

import java.time.LocalDate;

import br.com.todoList.entity.Task;

import br.com.todoList.enums. *;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author fsdney
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
	
	    private Long id;
	
	    @NotBlank(message = "A descrição não pode estar vazia")
	    private String description;

	 	@NotNull(message = "O status da tarefa é obrigatório")
	    private StatusTask status;

	    @NotNull(message = "A prioridade da tarefa é obrigatória")
	    private PriorityTask priority;
	    
	    @NotNull(message = "O Titulo da tarefa é obrigatória")
	    private String title;

	    @FutureOrPresent(message = "O prazo da tarefa não pode estar no passado")
	    private LocalDate deadline;

	    @NotNull(message = "O ID do usuário é obrigatório")
	    private Long userId;
	    
	    
}
