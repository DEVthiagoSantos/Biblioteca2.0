package com.livros.biblioteca.dto;

public record LivrosDTO(
        Long id,
        String titulo,
        String autor,
        String descricao,
        String status
) {}
