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

  /*  @ManyToOne
    private Livro livro;*/

    public Autor(){}

    public Autor(DataAutor dataAutor){
        this.nome = dataAutor.nome();
        this.anoNac = Optional.ofNullable(dataAutor.anoNac()).orElse(0);
        this.anoMorte = Optional.ofNullable(dataAutor.anoMor()).orElse(0);
       // this.anoMorte = OptionalInt.of(dataAutor.anoMor()).orElse(0);
    }

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

    //de la clase
//    public void setLivros(List<Livro> livros) {
//        livros.forEach(l -> l.setAutor(this));
//        this.livros = livros;
//    }

    //mio

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

//    @Override
//    public String toString() {
//        return  " Nome: " + nome +
//                " | Ano de Nacimento: " + anoNac +
//                " | Ano da Morte: " + anoMorte +
//                " | Livros[ " + livros + "]";
//    }


    public void imprimeAutor(){
        System.out.println("------Autor-------");
        System.out.println("Nome: " + this.getNome() );
    }
}
