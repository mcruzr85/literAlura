package com.br.alura.literAlura.repositories;

import com.br.alura.literAlura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {

   // List<Autor> FindByanoNacLessThanEqualAndAnoMorteGreaterThan(Integer ano);

    @Query("SELECT a FROM Autor a WHERE a.anoNac <= :ano AND a.anoMorte>:ano")
    List<Autor> BuscaAutoresVivosNumAnoDado(Integer ano);

   Optional<Autor> findByNomeEqualsIgnoreCase(String nome);

    Optional<Autor> findFirstByNomeContainingIgnoreCase(String nome);
}
