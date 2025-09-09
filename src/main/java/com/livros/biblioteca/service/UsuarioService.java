package com.livros.biblioteca.service;

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

    // Regra - POST
    public UsuarioModel criarUsuario(UsuarioModel usuarioModel) {
        if (usuarioModel == null || usuarioModel.getEmail() == null) {
            throw new RuntimeException("Usuario ou Email não pode ficar vazio");
        }

        return usuarioRepository.save(usuarioModel);
    }

    // Regra - Get
    public List<UsuarioModel> listarUsuario() {
        return usuarioRepository.findAll();
    }

    // Regra - Get (id)
    public UsuarioModel buscarUsuarioPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }

        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario com ID "+id+" não encontrado"));
    }

    //Regra - Delete - Id
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario com ID "+id+" não encontrado");
        }

        usuarioRepository.deleteById(id);
    }

}
