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
    private String idioma;
    private String poster;
    private Integer downloads;

    @ManyToOne
    //@JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro(){}
    public Livro(DataLivro dataLivro){
        this.titulo = dataLivro.titulo();
        this.downloads = dataLivro.downloads();
        this.idioma = String.join(" ", dataLivro.idiomas());
        this.poster = dataLivro.formatos().poster();
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


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdiomas(String idioma) {
        this.idioma = idioma;
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
        return  titulo;
    }

    public void imprimeLivro(){
        System.out.println("------Livro-------");
        System.out.println("Nome: " + this.getTitulo());
        System.out.println("Idioma: " + this.getIdioma());
    }

}
