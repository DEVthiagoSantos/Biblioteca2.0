package com.livros.biblioteca.repository;

import com.livros.biblioteca.model.LivrosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivrosRepository extends JpaRepository<LivrosModel, Long> {
}
