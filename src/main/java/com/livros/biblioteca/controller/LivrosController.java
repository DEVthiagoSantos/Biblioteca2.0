package com.livros.biblioteca.controller;

import com.livros.biblioteca.dto.LivrosDTO;
import com.livros.biblioteca.model.LivrosModel;
import com.livros.biblioteca.service.LivrosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-livros")
public class LivrosController {

    public final LivrosService livrosService;

    public LivrosController(LivrosService livrosService) {
        this.livrosService = livrosService;
    }

    // POST
    @PostMapping
    public ResponseEntity<LivrosDTO> adicionarLivros(@RequestBody LivrosDTO livrosModel) {
        LivrosDTO livro = livrosService.adicionarLivroDTO(livrosModel);
        return ResponseEntity.status(201).body(livro);
    }

    // GET
    @GetMapping
    public ResponseEntity<List<LivrosDTO>> listarLivros() {
        return ResponseEntity.ok(livrosService.listarLivrosDTO());
    }

    //GET - ID
    @GetMapping("/{id}")
    public ResponseEntity<LivrosDTO> listarLivroPorId(@PathVariable Long id) {
        LivrosDTO livro = livrosService.listarLivroPorIdDTO(id);
        return ResponseEntity.ok(livro);
    }

    //PUT - ID
    @PutMapping("/{id}")
    public ResponseEntity<LivrosDTO> atualizarLivro(@PathVariable Long id, @RequestBody LivrosDTO livrosDTO) {
        LivrosDTO livro = livrosService.atualizarLivroDTO(id, livrosDTO);
        return ResponseEntity.ok(livro);
    }

    //DELETE - ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long id) {
        livrosService.removerLivroPorIdDTO(id);
        return ResponseEntity.noContent().build();
    }

}
