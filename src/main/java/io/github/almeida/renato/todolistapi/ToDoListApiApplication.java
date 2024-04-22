package io.github.almeida.renato.todolistapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoListApiApplication implements CommandLineRunner {

	@Value("${spring.application.name}")
	private String valor;

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("-".repeat(50));
		System.out.println(valor);
		System.out.println("-".repeat(50));
	}
}
