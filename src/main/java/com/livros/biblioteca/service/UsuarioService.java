package com.livros.biblioteca.service;

import com.livros.biblioteca.dto.UsuarioDTO;
import com.livros.biblioteca.model.UsuarioModel;
import com.livros.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO toDTO(UsuarioModel usuarioModel){
        return new UsuarioDTO(
                usuarioModel.getId(),
                usuarioModel.getNome(),
                usuarioModel.getEmail()
        );
    }

    // Regra - POST
    public UsuarioModel criarUsuario(UsuarioModel usuarioModel) {
        if (usuarioModel == null || usuarioModel.getEmail() == null) {
            throw new RuntimeException("Usuario ou Email não pode ficar vazio");
        }

        return usuarioRepository.save(usuarioModel);
    }

    // Regra - POST DTO
    public UsuarioDTO criarUsuarioDTO(UsuarioModel usuarioModel) {
        return toDTO(criarUsuario(usuarioModel));
    }

    // Regra - Get
    public List<UsuarioModel> listarUsuario() {
        return usuarioRepository.findAll();
    }
    // Regra = Get DTO
    public List<UsuarioDTO> listarUsuarioDTO() {
        return listarUsuario()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Regra - Get (id)
    public UsuarioModel buscarUsuarioPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }

        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario com ID "+id+" não encontrado"));
    }
    // Regra - Get (id)DTO
    public UsuarioDTO buscarUsuarioPorIdDTO(Long id) {
        return toDTO(buscarUsuarioPorId(id));
    }

    //PUT - ID
    public UsuarioModel atualizarUsuario(Long id, UsuarioModel usuarioModel) {
        if (usuarioModel == null || id == null) {
            throw new IllegalArgumentException("Usuario ou ID não pode ser nulo.");
        }

        UsuarioModel existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario ou ID "+id+" não encontrado"));

        existente.setNome(usuarioModel.getNome());
        existente.setEmail(usuarioModel.getEmail());

        return usuarioRepository.save(existente);
    }

    //PUT - ID DTO
    public UsuarioDTO atualizarUsuarioDTO(Long id, UsuarioDTO usuarioDTO) {
        UsuarioModel existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario com ID "+id+" não encontrado"));

        existente.setNome(usuarioDTO.nome());
        existente.setEmail(usuarioDTO.email());

        return toDTO(usuarioRepository.save(existente));
    }

    //Regra - Delete - Id
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario com ID "+id+" não encontrado");
        }

        usuarioRepository.deleteById(id);
    }
    // Regra - Delete - Id DTO
    public String deletarUsuarioDTO(Long id) {
        deletarUsuario(id);
        return "Usuário com ID "+ id +" foi deletado com sucesso.";
    }

}
