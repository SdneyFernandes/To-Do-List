package br.com.todoList.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.todoList.entity.User;
import br.com.todoList.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/users") 
public class UserController {
	
	@Autowired
	private UserService userService;


    // Criar usuário
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.criarUsuario(user);
    }

    // Listar todos os usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userService.listarTodosUsuarios();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.buscarUsuarioPorId(id);
    }

   
    // Atualizar usuário
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.atualizarUsuario(id, user);
    }

    // Excluir usuário
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deletarUsuario(id);
    }
}

