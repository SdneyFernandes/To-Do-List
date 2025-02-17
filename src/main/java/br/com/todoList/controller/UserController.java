package br.com.todoList.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.todoList.entity.User;
import br.com.todoList.service.TaskService;
import br.com.todoList.service.UserService;
import br.com.todoList.dto.UserDTO;
import org.springframework.http.*;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/users") 
public class UserController {
	
	 private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;


	// Criar usuário
    @PostMapping
    public ResponseEntity<UserDTO> criarUsuario(@RequestBody UserDTO userDTO) {
    	logger.info("Recebida requisição para cadastrar novo usuario");
        UserDTO criarUsuario = userService.criarUsuario(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUsuario);
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserDTO>> listarTodosUsuarios() {
    	logger.info("Recebida requisição para listar todos usuarios");
        List<UserDTO> listarTodos = userService.listarTodosUsuarios();
        return ResponseEntity.ok(listarTodos);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarUsuarioPorId(@PathVariable Long id) {
    	logger.info("Recebida requisição para buscar usuario");
    	UserDTO buscarPorId = userService.buscarUsuarioPorId(id);
    	return ResponseEntity.ok(buscarPorId);
    }

   
    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UserDTO userDTO) {
    	logger.info("Recebida requisição para atualizar informações de usuario");
        UserDTO atualizarUsuario = userService.atualizarUsuario(id, userDTO);
        return ResponseEntity.ok(atualizarUsuario);
    }

    // Excluir usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    	logger.info("Recebida requisição para deletar usuario");
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}