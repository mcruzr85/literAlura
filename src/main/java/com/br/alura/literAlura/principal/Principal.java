package com.br.alura.literAlura.principal;

import com.br.alura.literAlura.models.*;
import com.br.alura.literAlura.repositories.AutorRepository;
import com.br.alura.literAlura.repositories.LivroRepository;
import com.br.alura.literAlura.services.ConsumoApi;
import com.br.alura.literAlura.services.ConverterJsonToObject;
import com.br.alura.literAlura.services.OperacoesDatabase;

import java.util.*;

public class Principal {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;
    // String endereco = "https://gutendex.com/books/";
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverterJsonToObject conversor = new ConverterJsonToObject();
    OperacoesDatabase dataOperations = new OperacoesDatabase();
    List<Livro> livros = new ArrayList<>();
    List<Autor> autores = new ArrayList<>();

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository){
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }


    public void exibirMenu(){
        boolean mostrar = true;
        int option = -1;

        String mensagem =
                """
                
                **************************************
                Benvindo a LiterAlura
                Seleccione a opção da sua preferencia
                **************************************
                1) Buscar livro pelo título
                2) Listar livros registrados
                3) Lista nossos autores
                4) Listar autores vivos em determinado ano
                5) Listar livros em determinado idioma
                6) Listar Top 5 livros
                0) Sair
                *************************************
                """;

        while(mostrar){

            System.out.println(mensagem);
            option = scanner.nextInt();

            switch(option){
                case 1:
                    //conectarse a API e salvar na base de dados
                    System.out.println("Opção selecionada: " + option + "- Buscar um livro");
                    getLivroFromApi();
                break;

                case 2:
                    System.out.println("Opção selecionada: " + option + " - Listar livros registrados");
                    getAllLivrosFromDb();
                    break;
                case 3:
                    System.out.println("Opção selecionada:" + option + " - Lista nossos autores");
                    getAllAutoresFromDb();
                    break;
                case 4:
                    System.out.println("Opção selecionada: " + option + " - Listar autores em determinado ano");
                    getAutoresVivoAno();
                    break;
                case 5:
                    System.out.println("Opção selecionada: " + option + " - Listar livros em determinado idioma");
                    getLivrosNaLingua();
                    break;
                case 6:
                    System.out.println("Opção selecionada: " + option + " - Top 5 Livros");
                    getTop5Livros();
                    break;
                case 0:
                    System.out.println("Muito obrigado ate mais!!");
                    mostrar = false;
                    break;
                default:
                    System.out.println("Digite uma opção valida");
                    break;
            }
        }

    }

    //metodo que obten o json da API e o transforma no objeto livaria(array de livros)
    private DataLivraria getLivraria() {
        System.out.println("Ingrese o nome do livro que deseja buscar:");
        scanner.nextLine();//limpando o buffer
        var bookName = scanner.nextLine();

        //obter o json
        var json = consumoApi.obterDados(ENDERECO + bookName.replace(" ","%20"));

        //transformar o json a objeto DataLivraria que tiene una lista de livros
        var livraria = conversor.converterDados(json, DataLivraria.class);
        return livraria;
    }






    private void getLivroFromApi(){

        DataLivraria dLivraria = getLivraria();

        Optional<DataLivro> optDataLivro = dLivraria.livros().stream()
                                           .sorted(Comparator.comparing(DataLivro::id))
                                           .findFirst();

       if(optDataLivro.isPresent()){
            DataLivro dataLivro = optDataLivro.get();


            //metodo para mostrar na tela as informações do livro
            imprimirDataLivro(dataLivro);

            System.out.println("Este é o livro que buscava?");
            System.out.println("Digite 1 se é o livro, 2 se não é o livro");
            int livroEncontrado= scanner.nextInt();
            scanner.nextLine();
            if(livroEncontrado == 1){

                Autor autor = new Autor(dataLivro.autores().get(0));
                Livro livro = new Livro(dataLivro);
                autor.setLivro(livro);
               /* List<Autor> autores = dataLivro.autores().stream()//para varios autores
                        .map(da -> new Autor(da))
                        .collect(Collectors.toList());*/

//                List<Autor> autores = new ArrayList<>();
//                dataLivro.autores().forEach(dA -> autores.add(new Autor(dA)));
//
//               livro.setAutores(autores);//aqui le pongo el livro dentro a cada autor
               autorRepository.save(autor);
                System.out.println("Livro salvo com sucesso!");
            }else{
                System.out.println("Tente agregando mais palavras ao titulo");
            }
        }else{
            System.out.println("Livro não encontrado");
        }

    }

    private void imprimirDataLivro(DataLivro dataLivro){

        System.out.println("-------Livro---------");
        System.out.println("Titulo: " + dataLivro.titulo());
        dataLivro.autores().forEach(this::imprimirAutor);
        System.out.println("Idioma: " + String.join(" ", dataLivro.idiomas()));
        System.out.println("Numero de downloads: " + dataLivro.downloads());
        System.out.println("Poster: " + dataLivro.formatos().poster());
        System.out.println("---------------------");
        System.out.println("\n");
    }


    private void imprimirAutor(DataAutor dataAutor){
        System.out.println( "Autor: " + dataAutor.nome());
    }


    private void getAllLivrosFromDb(){
        livros = livroRepository.findAll();
        imprimirLivros(livros);
    }

    private void imprimirLivros(List<Livro> livros) {
        System.out.println("================ Livros =====================");
        livros.forEach(l-> {
            System.out.println("---------------------------------------------");
            System.out.println("Titulo: " + l.getTitulo());
            System.out.println("Idioma: " + l.getIdioma());
            System.out.println("Downloads: " + l.getDownloads());
            System.out.println("Poster: " + l.getPoster());
            System.out.println("Autor: " + l.getAutor());
            System.out.println("---------------------------------------------");
        });
    }

 /*   private void getAllAutoresFromDb(){

        autores = autorRepository.findAll();
        System.out.println("*** Autores ***");

                //.sorted(Comparator.comparing(Livro::getIdiomas))
        autores.forEach(a-> {
                    System.out.println("---------------------------------------");
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("Nacimento: " + a.getAnoNac());
                    System.out.println("Morte: " + a.getAnoMorte());
                    System.out.print("Livros: [" );
                    a.getLivros().forEach(System.out::print);
                    System.out.println("]");
                    System.out.println("---------------------------------------");
                });
    }*/


         private void getAllAutoresFromDb(){
             autores = autorRepository.findAll();
             imprimirAutores(autores);
          }

    private void imprimirAutores(List<Autor> autores) {
        System.out.println("================ Autores =====================");
        autores.forEach(a-> {
            System.out.println("---------------------------------------------");
            System.out.println("Nome: " + a.getNome());
            System.out.println("Nacimento: " + a.getAnoNac());
            System.out.println("Morte: " + a.getAnoMorte());

            List<String> listaTitulos = new ArrayList<String>();
            a.getLivros().forEach(l -> listaTitulos.add(l.getTitulo()));

            System.out.println("Livros: " + listaTitulos);
            System.out.println("---------------------------------------------");
        });
    }

    private void getAutoresVivoAno(){
        System.out.println("Ingrese o ano:");
        Integer ano = scanner.nextInt();
        scanner.nextLine();
        System.out.println(ano);

      /*  List<Autor> autoresVivos = autorRepository.FindByanoNacLessThanEqualAndAnoMorteGreaterThan(ano);
        autoresVivos.forEach(System.out::println);*/
        System.out.println("*** Autores vivos no ano " + ano + " ***");
    }

    private void getTop5Livros(){
        List<Livro> topLivros = livroRepository.findTop5ByOrderByDownloadsDesc();
        System.out.println("*** Top 5 Livros ***");
        topLivros.forEach(System.out::println);
    }
    private void getLivrosNaLingua(){
         scanner.nextLine();
        System.out.println("Ingrese o idioma: ");
        String lingua = scanner.nextLine();

        List<Livro> livrosNumaLingua = livroRepository.findByIdioma(lingua);
        System.out.println("*** Livros no idioma" + lingua + " ***");
        livrosNumaLingua.forEach(l->
                System.out.println("Titulo: " + l.getTitulo() + " , Número de downloads: " + l.getDownloads()));
    }


}
