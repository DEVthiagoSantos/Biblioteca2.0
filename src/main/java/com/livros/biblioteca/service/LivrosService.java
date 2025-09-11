package com.livros.biblioteca.service;

import com.livros.biblioteca.dto.LivrosDTO;
import com.livros.biblioteca.model.LivrosModel;
import com.livros.biblioteca.repository.LivrosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class LivrosService {

    // fazendo a injeção de dependencia sem o uso do @Autowired
    public final LivrosRepository livrosRepository;

    public LivrosService(LivrosRepository livrosRepository) {
        this.livrosRepository = livrosRepository;
    }

    public LivrosDTO toDTO(LivrosModel livrosModel) {
        return new LivrosDTO(
                livrosModel.getId(),
                livrosModel.getTitulo(),
                livrosModel.getAutor(),
                livrosModel.getDescricao(),
                livrosModel.isDisponivel() ? "Disponivel" : "Indisponivel"
        );
    }

    // POST
    public LivrosModel adicionarLivro(LivrosModel livrosModel) {
        if (livrosModel == null || livrosModel.getAutor() == null) {
            throw new IllegalArgumentException("Os dados ou Autor não podem ser nulos");
        }

        return livrosRepository.save(livrosModel);
    }

    // POST DTO
    public LivrosDTO adicionarLivroDTO(LivrosDTO livrosDTO) {
        LivrosModel novo = new LivrosModel();

        novo.setTitulo(livrosDTO.titulo());
        novo.setAutor(livrosDTO.autor());
        novo.setDescricao(livrosDTO.descricao());
        novo.setDisponivel(true);

        return toDTO(livrosRepository.save(novo));
    }

    //GET
    public List<LivrosModel> listarLivros() {
        List<LivrosModel> livrosModels = livrosRepository.findAll();
        if (livrosModels.isEmpty()) {
            throw new IllegalArgumentException("Nenhum livro encontrado");
        }

        return livrosModels;
    }
    //GET DTO
    public List<LivrosDTO> listarLivrosDTO() {
        return listarLivros()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // GET - ID
    public LivrosModel listarLivroPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        return livrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID "+id+" não encontrado"));
    }
    // GET - ID DTO
    public LivrosDTO listarLivroPorIdDTO(Long id) {
        return toDTO(listarLivroPorId(id));
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
    // PUT - ID DTO
    public LivrosDTO atualizarLivroDTO(Long id, LivrosDTO livrosDTO) {
        LivrosModel existente = livrosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID " +id+ " não foi encontrado"));

        existente.setAutor(livrosDTO.autor());
        existente.setTitulo(livrosDTO.titulo());
        existente.setDescricao(livrosDTO.descricao());

        return toDTO(livrosRepository.save(existente));
    }

    // DELETE - ID
    public void removerLivroPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo.");
        }

        livrosRepository.deleteById(id);
    }
    // DELETE - ID DTO
    public String removerLivroPorIdDTO(Long id) {
        if (!livrosRepository.existsById(id)) {
            throw new RuntimeException("Livro com id " +id+ " não encontrado");
        }

        removerLivroPorId(id);
        return "Livro com o ID " +id+ " foi deletado com sucesso";
    }
}
