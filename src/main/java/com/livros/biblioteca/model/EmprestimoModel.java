package com.livros.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.livros.biblioteca.status.EmprestimoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tb_emprestimo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livros_id") // coluna que vai armazenar a FK
    @JsonIgnoreProperties("emprestimos")
    private LivrosModel livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("emprestimos")
    private UsuarioModel usuario;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmprestimo = new Date();

    @Temporal(TemporalType.DATE)
    private Date dataDevolucaoPrevista;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucaoReal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmprestimoStatus status;

}
