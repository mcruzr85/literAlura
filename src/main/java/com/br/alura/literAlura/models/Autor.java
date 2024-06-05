package com.br.alura.literAlura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer anoNac;
    private Integer anoMorte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(DataAutor dataAutor){
        this.nome = dataAutor.nome();
        this.anoNac = Optional.ofNullable(dataAutor.anoNac()).orElse(0);
        this.anoMorte = Optional.ofNullable(dataAutor.anoMor()).orElse(0);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNac() {
        return anoNac;
    }

    public void setAnoNac(Integer anoNac) {
        this.anoNac = anoNac;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    public List<Livro> getLivros() {
        return livros;
    }


  public void setLivros(List<Livro> livros) {
       livros.forEach(l -> {
            l.setAutor(this);
            this.livros.add(l);
       });
    }
    public void setLivro(Livro livro) {
        livro.setAutor(this);
        this.livros.add(livro);
    }

    @Override
    public String toString() {
        return   nome ;
    }

}
