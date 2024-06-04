package com.br.alura.literAlura.repositories;

import com.br.alura.literAlura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository <Autor, Long> {

   // List<Autor> FindByanoNacLessThanEqualAndAnoMorteGreaterThan(Integer ano);
}
