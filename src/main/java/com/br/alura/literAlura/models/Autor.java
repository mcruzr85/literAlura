package com.br.alura.literAlura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(DataAutor dataAutor){
        this.nome = dataAutor.nome();
        this.anoNac = OptionalInt.of(dataAutor.anoNac()).orElse(0);
        this.anoMorte = OptionalInt.of(dataAutor.anoMor()).orElse(0);

    }


    public void addLivro(Livro livro) {
        this.livros.add(livro);
        livro.setAutor(this);
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
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", anoNac=" + anoNac +
                ", anoMorte=" + anoMorte +
                ", livros=" + livros +
                '}';
    }
}
