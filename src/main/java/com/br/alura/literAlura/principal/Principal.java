package com.br.alura.literAlura.principal;

import com.br.alura.literAlura.models.*;
import com.br.alura.literAlura.repositories.AutorRepository;
import com.br.alura.literAlura.repositories.LivroRepository;
import com.br.alura.literAlura.services.ConsumoApi;
import com.br.alura.literAlura.services.ConverterJsonToObject;

import java.util.*;

import static java.util.Comparator.*;

public class Principal {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;
    // String endereco = "https://gutendex.com/books/";
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverterJsonToObject conversor = new ConverterJsonToObject();
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
                
                ====================================================
                Benvindo a LiterAlura 📚  Catálogo de Livros online
                Seleccione a opção da sua preferencia
                ====================================================
                 ❤️ 1) Buscar livro pelo título
                 🧡 2) Listar livros registrados
                 💛 3) Listar autores registrados  
                 💚 4) Listar autores vivos em determinado ano 
                 🩵 5) Listar livros em determinado idioma
                 💙 6) Listar Top 5 livros 
                 🩷 7) Buscar autor por nome
                 💜 0) Sair
                ====================================================
                """;
        try{
            while(mostrar){

                System.out.println(mensagem);
                option = scanner.nextInt();

                switch(option){
                    case 1:
                        //conectarse a API e salvar na base de dados
                        System.out.println("Opção " + option + " selecionada: Buscar um livro");
                        getLivroFromApi();
                        break;

                    case 2:
                        System.out.println("Opção " + option + " selecionada: Listar livros registrados");
                        getAllLivrosFromDb();
                        break;
                    case 3:
                        System.out.println("Opção " + option +  " selecionada: Listar autores registrados");
                        getAllAutoresFromDb();
                        break;
                    case 4:
                        System.out.println("Opção " + option + " selecionada: Listar autores vivos em determinado ano");
                        getAutoresVivoAno();
                        break;
                    case 5:
                        System.out.println("Opção " + option + " selecionada: Listar livros em determinado idioma");
                        getLivrosNaLingua();
                        break;
                    case 6:
                        System.out.println("Opção " + option + " selecionada: Buscar Top 5 Livros");
                        getTop5Livros();
                        break;
                    case 7:
                        System.out.println("Opção " + option + " selecionada: Buscar autor por nome");
                        getAutorPorNome();
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
        }catch (InputMismatchException e){
            System.out.println("Entrada inválida, por favor insira um número do menu");
        }
    }


    //metodo que obten o json da API e o transforma no objeto dataLivaria(array de livros)
    private DataLivraria getLivraria() {
        System.out.println("Ingrese o nome do livro que deseja buscar:");
        scanner.nextLine();//limpando o buffer
        var bookName = scanner.nextLine();

        //obter o json
        String json = consumoApi.obterJson(ENDERECO + bookName.replace(" ","%20").toLowerCase().trim());

        //transformar o json a objeto DataLivraria que tiene una lista de livros
        var livraria = conversor.converterDados(json, DataLivraria.class);
        return livraria;
    }



    private void getLivroFromApi(){

        //obter array de DataLivros
        DataLivraria dLivraria = getLivraria();

        //pego o do menor id
        Optional<DataLivro> optDataLivro = dLivraria.livros().stream()
                .sorted(comparing(DataLivro::id))
                .findFirst();

        if(optDataLivro.isPresent()){

            DataLivro dataLivro = optDataLivro.get();
            String titulo = dataLivro.titulo();

            //conferindo se esta no banco de dados
            Optional<Livro> optLivro = livroRepository.findByTituloEqualsIgnoreCase(titulo);

            if(optLivro.isPresent()){
                System.out.println("Esse livro já esta registrado no banco de dados, tente outro por favor.");
            }else{

                //metodo para mostrar na tela as informações do livro
                imprimirDataLivro(dataLivro);

                System.out.println("Digite 1 se é o livro que buscava ou 2 se não é o livro");
                int achou= scanner.nextInt();
                scanner.nextLine();
                if(achou == 1){//é o livro desejado

                    Autor autor = new Autor(dataLivro.autores().get(0));
                    Livro livro = new Livro(dataLivro);
                    autor.setLivro(livro);

                    Optional<Autor> optAutor = autorRepository.findByNomeEqualsIgnoreCase(autor.getNome());

                    //confiro se o autor esta registrado
                    if(optAutor.isPresent()){
                        //guardo so o livro com a info do autor dentro(id_autor)
                        Autor autorRegistrado = optAutor.get();
                        livro.setAutor(autorRegistrado);
                        livroRepository.save(livro);

                    }else{
                        //guardo o autor e o livro
                        autorRepository.save(autor);
                    }
                    System.out.println("Livro salvo com sucesso!");
                }else{
                    System.out.println("Tente agregando mais palavras ao titulo");
                }
            }
        }else{
            System.out.println("Livro não encontrado");
        }
    }

    private void imprimirDataLivro(DataLivro dataLivro){
        System.out.println("-------Livro---------");
        System.out.println("Titulo: " + dataLivro.titulo());
        dataLivro.autores().forEach(this::imprimirDataAutor);
        System.out.println("Idioma: " + String.join(" ", dataLivro.idiomas()));
        System.out.println("Numero de downloads: " + dataLivro.downloads());
        System.out.println("Poster: " + dataLivro.formatos().poster());
        System.out.println("---------------------");
        System.out.println("\n");
    }


    private void imprimirDataAutor(DataAutor dataAutor){
        System.out.println( "Autor: " + dataAutor.nome());
    }


    private void getAllLivrosFromDb(){
        livros = livroRepository.findAll();
        if(livros.isEmpty()){
            System.out.println("========== Não existem livros registrados ==========");
        }
        livros.sort(Comparator.comparing(Livro::getIdioma));
        imprimirLivros(livros);
    }

    private void imprimirLivros(List<Livro> livros) {
        System.out.println("=================== Livros ========================");
        livros.forEach(l-> {
            System.out.println("Titulo: " + l.getTitulo());
            System.out.println("Idioma: " + l.getIdioma());
            System.out.println("Downloads: " + l.getDownloads());
            System.out.println("Poster: " + l.getPoster());
            System.out.println("Autor: " + l.getAutor());
            System.out.println("-----------------------------------------------------");
        });
    }



    private void getAllAutoresFromDb(){
        autores = autorRepository.findAll();

        if(autores.isEmpty()){
            System.out.println("=========== Não existem autores registrados ============");
        }else{
          System.out.println("=================== Autores ========================");

            autores.sort(Comparator.comparing(Autor::getNome));
            imprimirAutores(autores);
         }
        }

    private void imprimirAutores(List<Autor> autores) {
        autores.forEach(a-> {
            System.out.println("Nome: " + a.getNome());
            System.out.println("Nacimento: " + a.getAnoNac());
            System.out.println("Morte: " + a.getAnoMorte());

            List<String> listaTitulos = new ArrayList<>();
            a.getLivros().forEach(l -> listaTitulos.add(l.getTitulo()));

            System.out.println("Livros: " + listaTitulos);
            System.out.println("-----------------------------------------------------");
        });

    }

    private void getAutoresVivoAno(){
        System.out.println("Ingrese o ano:");
        Integer ano = scanner.nextInt();
        scanner.nextLine();

        List<Autor> autoresVivos = autorRepository.BuscaAutoresVivosNumAnoDado(ano);

        if(autoresVivos.isEmpty()){
            System.out.println("Não tem registrados autores vivos no ano " + ano);
        }else{
            System.out.println("========= Autores vivos no ano " + ano + " ===============");
            System.out.println('\n');
            imprimirAutores(autoresVivos);
        }
    }

    private void getTop5Livros(){
        List<Livro> topLivros = livroRepository.findTop5ByOrderByDownloadsDesc();

        if(topLivros.isEmpty()){
            System.out.println("Não tem livros registrados");
        }else{
            System.out.println("================ Top 5 Livros ================");
            System.out.println('\n');
            topLivros.forEach(l->
                    System.out.println("Titulo: " + l.getTitulo() + " , Número de downloads: " + l.getDownloads()));
        }
    }
    private void getLivrosNaLingua(){
        scanner.nextLine();
        String menuIdioma =
                """                                              
                Esreva o idioma por favor
                ---------------------------
                 pt - Português 
                 en - Inglês
                 es - Espanhol  
                 fr - Francês        
                ---------------------------
                """;
        System.out.println(menuIdioma);
        String lingua = scanner.nextLine();

        List<Livro> livrosNumaLingua = livroRepository.findByIdioma(lingua);

        if(livrosNumaLingua.isEmpty()){
            System.out.println("Não tem livros registrados no idioma " + lingua);

        }else{
            System.out.println("================ Livros no idioma " + lingua + " ================");
            System.out.println('\n');
            livrosNumaLingua.forEach(l->
                    System.out.println("Titulo: " + l.getTitulo() + " , Autor: " + l.getAutor().getNome()));
        }
    }

    private void getAutorPorNome() {
        scanner.nextLine();
        System.out.println("Inserte o nome do autor que deseja procurar");
        String nome = scanner.nextLine();

        Optional<Autor> optAutor = autorRepository.findFirstByNomeContainingIgnoreCase(nome);
        if(optAutor.isPresent()){

            Autor autorBuscado = optAutor.get();
            List<Autor> listaParaImprimir = new ArrayList<>();
            listaParaImprimir.add(autorBuscado);
            System.out.println();
            System.out.println("================== Dados do Autor ==================");
            imprimirAutores(listaParaImprimir);

        }else{
            System.out.println("========== Autor não registrado no banco de dados ============");
            System.out.println('\n');
        }
    }

}
