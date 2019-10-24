package br.com.ibm.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void calcularCedulas(int valor) {
		int[] cedulas = {100, 50, 20, 10};

		for (int i = 0; i < cedulas.length; i++) {
			if (valor >= cedulas[i]) {
				System.out.println(valor/cedulas[i] + " notas de " + cedulas[i]);
				valor = valor % cedulas[i];
			}
		}
		System.out.println("Sobram: " + valor);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("calculando cedulas");
		calcularCedulas(130);
	}
}