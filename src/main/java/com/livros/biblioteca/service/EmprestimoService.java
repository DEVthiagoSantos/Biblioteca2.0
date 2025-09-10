package com.livros.biblioteca.service;

import com.livros.biblioteca.model.EmprestimoModel;
import com.livros.biblioteca.model.LivrosModel;
import com.livros.biblioteca.model.UsuarioModel;
import com.livros.biblioteca.repository.EmprestimoRepository;
import com.livros.biblioteca.repository.LivrosRepository;
import com.livros.biblioteca.repository.UsuarioRepository;
import com.livros.biblioteca.status.EmprestimoStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class EmprestimoService {

    public final EmprestimoRepository emprestimoRepository;
    public final LivrosRepository livrosRepository;
    public final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivrosRepository livrosRepository, UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livrosRepository = livrosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // POST
    public EmprestimoModel criarEmprestimo(Long idLivro, Long idUsuario){
        if (idLivro == null || idUsuario == null) {
            throw new IllegalArgumentException("Id do livro ou do usuario não podem ser nulos.");
        }

        LivrosModel existenteLivro = livrosRepository.findById(idLivro)
                .orElseThrow(() -> new RuntimeException("Livro com ID "+idLivro+" não encontrado"));

        if (!existenteLivro.isDisponivel()) {
            throw new IllegalArgumentException("Esse livro não esta disponivel");
        }

        UsuarioModel usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario com ID"+idUsuario+" não encontrado"));

        existenteLivro.setDisponivel(false);
        EmprestimoModel emprestimoModel = new EmprestimoModel();
        emprestimoModel.setUsuario(usuario);
        emprestimoModel.setLivro(existenteLivro);
        emprestimoModel.setStatus(EmprestimoStatus.ATIVO);
        emprestimoModel.setDataEmprestimo(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        emprestimoModel.setDataDevolucaoPrevista(calendar.getTime());

        livrosRepository.save(existenteLivro);
        return emprestimoRepository.save(emprestimoModel);
    }

    // PUT
    public EmprestimoModel finalizarEmprestimo(Long idEmprestimo) {
        if (idEmprestimo == null) {
            throw new IllegalArgumentException("ID do emprestimo não pode ser nulo.");
        }

        EmprestimoModel emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Emprestimo com ID "+idEmprestimo+" não encontrado"));

        if (emprestimo.getStatus().equals(EmprestimoStatus.ATIVO)) {
            throw new RuntimeException("Emprestimo já foi finalizado");
        }

        emprestimo.setStatus(EmprestimoStatus.FINALIZADO);
        emprestimo.setDataDevolucaoReal(new Date());

        LivrosModel livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livrosRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    // GET
    public List<EmprestimoModel> listarEmprestimos() {
        if (emprestimoRepository.count() == 0) {
            throw new IllegalArgumentException("Nenhum livro encontrado");
        }

        return emprestimoRepository.findAll();
    }
}
