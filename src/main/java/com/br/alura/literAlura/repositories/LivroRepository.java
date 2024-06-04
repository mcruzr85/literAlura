package com.br.alura.literAlura.repositories;

import com.br.alura.literAlura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findTop5ByOrderByDownloadsDesc();
    List<Livro> findByIdioma(String idioma);
}
