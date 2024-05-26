package com.br.alura.literAlura.repositories;

import com.br.alura.literAlura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository <Livro, Long> {
}
