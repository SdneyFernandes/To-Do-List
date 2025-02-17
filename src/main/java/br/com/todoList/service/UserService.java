package br.com.todoList.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import br.com.todoList.repository.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import br.com.todoList.entity.User;

import java.util.stream.Collectors;

import br.com.todoList.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fsdney
 */


@Service
public class UserService {
	
	 private final Logger logger = LoggerFactory.getLogger(TaskService.class);
	    
	 @Autowired
	 private MeterRegistry meterRegistry;

	@Autowired
	private UserRepository userRepository;

    // Criar usuário
    public UserDTO criarUsuario(UserDTO userDTO) {
    	logger.info("Cadastrando novo usuario");
        meterRegistry.counter("usuario.criar.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
        Optional<User> usuarioExiste = userRepository.findByName(userDTO.getName());
    	if (usuarioExiste.isPresent()) {
    		logger.warn("O usuário com o nome '{}' já existe.", userDTO.getName());
            meterRegistry.counter("usuario.criar.falha").increment();
            throw new RuntimeException ("Ja existe um usuario cadastrado com esse nome");
    	}
    	
    	User novoUsuario = converterParaUsuario(userDTO);
        novoUsuario = userRepository.save(novoUsuario);
    	
    	logger.info("Usuario cadastrada com sucesso: {}", userDTO);
        meterRegistry.counter("usuario.criados.sucesso").increment(); 
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("usuario.criar.tempo").record(endTime - startTime, TimeUnit.MILLISECONDS);
       
        return converterParaDTO(novoUsuario);
        
        
    }

    // Listar todos os usuários
    public List<UserDTO> listarTodosUsuarios() {
    	
         logger.info("Listando todos os usuários.");
         meterRegistry.counter("usuario.listar.todas.chamadas").increment();
         long startTime = System.currentTimeMillis();
         
         List<User> user = userRepository.findAll();
         
         if (user.isEmpty()) {
             logger.warn("A lista está vazia.");
             meterRegistry.counter("usuario.listar.todas.vazio").increment();
         } else {
             logger.info("Total de usuários encontrados: {}", user.size());
             meterRegistry.counter("usuario.listar.todas.sucesso").increment();
         }
         
         
         long endTime = System.currentTimeMillis();
         meterRegistry.timer("usuario.listar.todas.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
         return converterParaDTO(user);
    	 
    	 
    	 
    	 
    }

    // Buscar usuário por ID
    public UserDTO buscarUsuarioPorId(Long id) {
         
        
       logger.info("Buscando usuario com ID: {}", id);
       meterRegistry.counter("usuario.buscarPorID.chamadas").increment();
       long startTime = System.currentTimeMillis();
    	
       User user = userRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado."));
       
       logger.info("Usuário encontrado: {}", user);
       meterRegistry.counter("usuario.buscarPorID.sucesso").increment();
       long endTime = System.currentTimeMillis();
		meterRegistry.timer("usuario.buscarPorID.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
        return converterParaDTO(user);
        
        
    }

    // Atualizar usuário
    public UserDTO atualizarUsuario(Long id, UserDTO userDTO) {
    	logger.info("Atualizando informações do usuario com ID: {}", id);
        meterRegistry.counter("usuario.atualizar.chamadas").increment();
        long startTime = System.currentTimeMillis();
    	
        User existeUsuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
        	logger.warn("Nome invalido");
            throw new RuntimeException(" Nome invalido ");	
        } 	
        	existeUsuario.setName(userDTO.getName());
        
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
        	logger.warn("Email invalido.");
            throw new RuntimeException("Email invalido.");	
        }      	
        	existeUsuario.setEmail(userDTO.getEmail());

        User usuarioAtualizado = userRepository.save(existeUsuario);
        return converterParaDTO(usuarioAtualizado);
    }

    // Excluir usuário
    public void deletarUsuario(Long id) {
    	if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
        userRepository.deleteById(id);
    }
    
 // Método para converter `User` para `UserDTO`
    private UserDTO converterParaDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
    
 // Método para converter uma lista de `User ` para `User DTO`
    private List<UserDTO> converterParaDTO(List<User> users) {
        return users.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    
    
    private User converterParaUsuario(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail()); 
        user.setPassword(userDTO.getPassword()); 
        return user;
    }
}
