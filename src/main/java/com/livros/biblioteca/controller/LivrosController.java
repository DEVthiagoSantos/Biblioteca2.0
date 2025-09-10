package com.livros.biblioteca.controller;

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
    public ResponseEntity<LivrosModel> adicionarLivros(@RequestBody LivrosModel livrosModel) {
        LivrosModel livro = livrosService.adicionarLivro(livrosModel);
        return ResponseEntity.status(201).body(livro);
    }

    // GET
    @GetMapping
    public ResponseEntity<List<LivrosModel>> listarLivros() {
        return ResponseEntity.ok(livrosService.listarLivros());
    }

    //GET - ID
    @GetMapping("/{id}")
    public ResponseEntity<LivrosModel> listarLivroPorId(@PathVariable Long id) {
        LivrosModel livro = livrosService.listarLivroPorId(id);
        return ResponseEntity.ok(livro);
    }

    //PUT - ID
    @PutMapping("/{id}")
    public ResponseEntity<LivrosModel> atualizarLivro(@PathVariable Long id, @RequestBody LivrosModel livrosModel) {
        LivrosModel livro = livrosService.atualizarLivro(id, livrosModel);
        return ResponseEntity.ok(livro);
    }

    //DELETE - ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long id) {
        livrosService.removerLivroPorId(id);
        return ResponseEntity.noContent().build();
    }

}
