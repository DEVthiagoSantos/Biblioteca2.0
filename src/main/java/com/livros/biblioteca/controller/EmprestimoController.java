package com.livros.biblioteca.controller;

import com.livros.biblioteca.dto.EmprestimoDTO;
import com.livros.biblioteca.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-emprestimo")
public class EmprestimoController {

    public final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    // POST
    @PostMapping("/{idLivro}/{idUsuario}")
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@PathVariable Long idLivro, @PathVariable Long idUsuario) {
        EmprestimoDTO emprestimo = emprestimoService.criarEmprestimoDTO(idLivro, idUsuario);
        return ResponseEntity.status(201).body(emprestimo);
    }

    // PUT
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<EmprestimoDTO> finalizarEmprestimo(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.finalizarEmprestimoDTO(id));
    }

    // GET
    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos() {
        return ResponseEntity.ok(emprestimoService.listarEmprestimosDTO());
    }
}
