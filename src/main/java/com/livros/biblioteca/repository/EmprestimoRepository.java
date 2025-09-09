package com.livros.biblioteca.repository;

import com.livros.biblioteca.model.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {
}
