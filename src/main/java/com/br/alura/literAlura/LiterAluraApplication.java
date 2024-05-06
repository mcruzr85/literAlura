package com.br.alura.literAlura;

import com.br.alura.literAlura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	//implementação dessa interfaz pq é app de linha de comando

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//este metodo vai ser como o meu main
		System.out.println("hola hola desde run");
		Principal principal= new Principal();
		principal.exibirMenu();

	}
}
