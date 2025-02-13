package br.com.todoList.service;

import java.util.List;
import org.springframework.stereotype.Service;
import br.com.todoList.repository.UserRepository;
import br.com.todoList.entity.User;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fsdney
 */


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

    // Criar usuário
    public User criarUsuario(User user) {
        return userRepository.save(user);
    }

    // Listar todos os usuários
    public List<User> listarTodosUsuarios() {
        return userRepository.findAll();
    }

    // Buscar usuário por ID
    public User buscarUsuarioPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Atualizar usuário
    public User atualizarUsuario(Long id, User atualizarUsuario) {
        User existeUsuario = buscarUsuarioPorId(id);

        if (atualizarUsuario.getName() != null) existeUsuario.setName(atualizarUsuario.getName());
        if (atualizarUsuario.getEmail() != null)existeUsuario.setEmail(atualizarUsuario.getEmail());

        return userRepository.save(existeUsuario);
    }

    // Excluir usuário
    public void deletarUsuario(Long id) {
        userRepository.deleteById(id);
    }
}
