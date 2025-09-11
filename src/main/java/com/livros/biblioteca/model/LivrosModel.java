package com.livros.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_livros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivrosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    private String descricao;

    @Column(nullable = false)
    private boolean disponivel = true;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmprestimoModel> emprestimos;

}
