package com.br.alura.literAlura.principal;

import com.br.alura.literAlura.models.DataLivraria;
import com.br.alura.literAlura.models.DataLivro;
import com.br.alura.literAlura.services.ConsumoApi;
import com.br.alura.literAlura.services.ConverterJsonToObject;
import com.br.alura.literAlura.services.OperacoesDatabase;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
   // String endereco = "https://gutendex.com/books/";
   String endereco = "https://gutendex.com/books/?search=";
    String json = consumoApi.obterDados(endereco);
    ConverterJsonToObject conversor = new ConverterJsonToObject();
    OperacoesDatabase dataOperations = new OperacoesDatabase();






    public void exibirMenu(){
        boolean mostrar = true;
        int option = 6;

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
                    DataLivro livro = optDataLivro.get();
                    System.out.println(livro);
                    System.out.println("Este é o livro que buscava?");
                    Boolean eOLibro= scanner.nextBoolean();
                    if(eOLibro){
                        // dataOperations.insertaLivro();
                        System.out.println("Livro salvo com sucesso!");
                    }
                }
                break;

                case 2:
                    System.out.println("Opção selecionada: " + option + " - Listar livros registrados");
                    dataOperations.getAllLivros();
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
                default:
                    System.out.println("Muito obrigado ate mais!!");
                    mostrar = false;
                    break;
            }
        }

    }
}
