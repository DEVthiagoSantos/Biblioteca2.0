package com.livros.biblioteca.controller;

import com.livros.biblioteca.dto.UsuarioDTO;
import com.livros.biblioteca.model.UsuarioModel;
import com.livros.biblioteca.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-usuario")
public class UsuarioController {

    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //POST
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioService.criarUsuarioDTO(
                new UsuarioModel(null, usuarioDTO.nome(), usuarioDTO.email(), null)
        );
        return ResponseEntity.status(201).body(usuario);
    }

    //GET
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarioDTO());
    }

    //GET - ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarUsuarioPorIdDTO(id);
        return ResponseEntity.ok(usuario);
    }

    //PUT - ID
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioService.atualizarUsuarioDTO(id, usuarioDTO);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuarioDTO(id);
        return ResponseEntity.noContent().build();
    }
}
