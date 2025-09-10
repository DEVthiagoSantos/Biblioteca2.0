package com.livros.biblioteca.service;

import com.livros.biblioteca.model.LivrosModel;
import com.livros.biblioteca.repository.LivrosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivrosService {

    // fazendo a injeção de dependencia sem o uso do @Autowired
    public final LivrosRepository livrosRepository;

    public LivrosService(LivrosRepository livrosRepository) {
        this.livrosRepository = livrosRepository;
    }

    // POST
    public LivrosModel adicionarLivro(LivrosModel livrosModel) {
        if (livrosModel == null || livrosModel.getAutor() == null) {
            throw new IllegalArgumentException("Os dados ou Autor não podem ser nulos");
        }

        return livrosRepository.save(livrosModel);
    }

    //GET
    public List<LivrosModel> listarLivros() {
        List<LivrosModel> livrosModels = livrosRepository.findAll();
        if (livrosModels.isEmpty()) {
            throw new IllegalArgumentException("Nenhum livro encontrado");
        }

        return livrosModels;
    }

    // GET - ID
    public LivrosModel listarLivroPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        return livrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID "+id+" não encontrado"));
    }

    //PUT - ID
    public LivrosModel atualizarLivro(Long id, LivrosModel livrosModel) {
        if (id == null || livrosModel == null) {
            throw new IllegalArgumentException("Livro ou Id não podem ser nulos");
        }

        LivrosModel existente = livrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe livro com o ID "+id));

        existente.setAutor(livrosModel.getAutor());
        existente.setTitulo(livrosModel.getTitulo());
        existente.setDescricao(livrosModel.getDescricao());

        return livrosRepository.save(existente);
    }

    // DELETE - ID
    public void removerLivroPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo.");
        }

        livrosRepository.deleteById(id);
    }

}
