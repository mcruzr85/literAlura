package com.br.alura.literAlura.models;

import jakarta.persistence.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    //IDENTITY. estratégia escolhida é a auto-incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idiomas;
    private String poster;
    private Integer downloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
   // private List<DataAutor> autores = new ArrayList<>();

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Livro(){}
    public Livro(DataLivro dataLivro){
        this.titulo = dataLivro.titulo();
        this.downloads = dataLivro.downloads();
        this.idiomas = String.join(" ", dataLivro.idiomas());
        this.poster = dataLivro.formatos().poster();

        //**asignando valores por default para no tener problemas

        // this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);

        //otra forma

       /* try{
            this.avaliacao = Double.valueOf(dadosSerie.avaliacao());
        }catch(NumberFormatException ex){
            this.avaliacao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosSerie.dataLancamento());
        }catch(DateTimeParseException ex){
            this.dataLancamento = null;
        }*/


    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

   /* public List<DataAutor> getAutores() {
        return autores;
    }

    public void setAutores(List<DataAutor> autores) {
        this.autores = autores;
    }*/

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", poster='" + poster + '\'' +
                ", downloads=" + downloads +
                ", autor=" + autor +
                '}';
    }

   /* @Override
    public String toString() {
        return "Livro::{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autores=" + autores +
               // ", idiomas=" + Arrays.toString(idiomas) +
                ", idiomas=" + idiomas +
                ", downloads=" + downloads +
                '}';
    }*/
}
