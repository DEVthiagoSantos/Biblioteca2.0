package com.livros.biblioteca.dto;

import com.livros.biblioteca.status.EmprestimoStatus;

import java.util.Date;

public record EmprestimoDTO(
        Long id,
        String tituloLivro,
        String autorLivro,
        String usuarioNome,
        String usuarioEmail,
        Date dataEmprestimo,
        Date dataDevolucaoPrevista,
        Date dataDevolucaoReal,
        EmprestimoStatus status
) { }
