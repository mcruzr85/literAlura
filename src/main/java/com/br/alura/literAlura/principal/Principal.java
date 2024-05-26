package com.br.alura.literAlura.principal;

import com.br.alura.literAlura.models.*;
import com.br.alura.literAlura.repositories.LivroRepository;
import com.br.alura.literAlura.services.ConsumoApi;
import com.br.alura.literAlura.services.ConverterJsonToObject;
import com.br.alura.literAlura.services.OperacoesDatabase;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private LivroRepository livroRepository;
    // String endereco = "https://gutendex.com/books/";
    String endereco = "https://gutendex.com/books/?search=";
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverterJsonToObject conversor = new ConverterJsonToObject();
    OperacoesDatabase dataOperations = new OperacoesDatabase();
    List<Livro> livros = new ArrayList<>();

    public Principal(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
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
                4) Listar autores em determinado ano
                5) Listar livros em determinado idioma
                6) Sair
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
                    getAllLivros();
                    break;
                case 3:
                    System.out.println("Opção selecionada:" + option + " - Lista nossos autores");
                    dataOperations.getAllAutores();
                    break;
                case 4:
                    System.out.println("Opção selecionada: " + option + " - Listar autores em determinado ano");
                    System.out.println("Ingrese o ano:");
                    Integer ano = scanner.nextInt();
                    dataOperations.getAutoresAno(ano);
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.println("Opção selecionada: " + option + " - Listar livros em determinado idioma");
                    System.out.println("Ingrese a lingua:");
                    String language = scanner.nextLine();
                    dataOperations.getLivrosLanguage(language);
                    break;
                case 6:
                    System.out.println("Muito obrigado ate mais!!");
                    mostrar = false;
                    break;
                default:
                    System.out.println("Digite uma opção valida");
                    break;
            }
        }

    }

    private void getLivroFromApi(){
        System.out.println("Ingrese o nome do livro que deseja buscar:");
        scanner.nextLine();//limpando o buffer
        String bookName = scanner.nextLine();
        System.out.println(bookName);
        //obter o json
        System.out.println(endereco + bookName.replace(" ","%20"));
        String json = consumoApi.obterDados(endereco + bookName.replace(" ","%20"));
        //String jsonLivro = consumoApi.obterDados("https://gutendex.com/books/5/");
        System.out.println(json);


        //transformar o json a objeto DataLivraria que tiene una lista de livros
        var livraria = conversor.converterDados(json, DataLivraria.class);


        var optDataLivro = livraria.livros().stream()
                .sorted(Comparator.comparing(DataLivro::titulo))
                .findFirst();


        if(optDataLivro.isPresent()){
            DataLivro dataLivro = optDataLivro.get();

            System.out.println(dataLivro);
            System.out.println("***************");

            //metodo para mostrar na tela as informações do livro
            imprimirLivro(dataLivro);

            System.out.println("Este é o livro que buscava?");
            System.out.println("Digite 1 se é o livro, 2 se não é o livro");
            int livroEncontrado= scanner.nextInt();
            scanner.nextLine();
            if(livroEncontrado == 1){
                // dataOperations.insertaLivro();

                Livro livro = new Livro(dataLivro);
               /* List<Autor> autores = dataLivro.autores().stream()//para varios autores
                        .map(da -> new Autor(da))
                        .collect(Collectors.toList());*/
                Autor autor = new Autor(dataLivro.autores().get(0));

                livro.setAutor(autor);
                livroRepository.save(livro);
                System.out.println("Livro salvo com sucesso!");
            }else{
                System.out.println("Tente agregando mais palavras ao titulo");
            }
        }else{
            System.out.println("Livro não encontrado");
        }

    }

    private void imprimirLivro(DataLivro dataLivro){

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
    private void getAllLivros(){
        livros = new ArrayList<>();
        livros = livroRepository.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getIdiomas))
                .forEach(System.out::println);

    }


}
