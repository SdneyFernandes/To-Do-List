package br.com.todoList.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.stream.Collectors;

import br.com.todoList.entity.Task;
import lombok.AllArgsConstructor;
import lombok.*;


import br.com.todoList.dto.TaskDTO;


import java.util.List;


/**
 * @author fsdney
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
private Long id;

@NotBlank(message = "O nome não pode estar vazia")
private String name;

@NotBlank(message = "O email não pode estar vazia")
private String email;

@NotBlank(message = "A senha não pode estar vazia")
private String password;


}
