package com.br.alura.literAlura;

import com.br.alura.literAlura.principal.Principal;
import com.br.alura.literAlura.repositories.AutorRepository;
import com.br.alura.literAlura.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	//implementação dessa interfaz pq é app de linha de comando

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}
    @Autowired
    private AutorRepository autorRepository;
	@Autowired
	private LivroRepository livroRepository;

	@Override
	public void run(String... args) throws Exception {
		//este metodo vai ser como o meu main
		Principal principal= new Principal(livroRepository, autorRepository);
		principal.exibirMenu();

	}
}
