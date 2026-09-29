package br.com.univesp.camara;

import br.com.univesp.camara.etl.EtlService;
import br.com.univesp.camara.repository.DeputadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CamaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamaraApplication.class, args);
	}

	@Bean
	CommandLineRunner init(EtlService etl, DeputadoRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				System.out.println(">>> Banco vazio. Iniciando ETL de deputados...");
				etl.sincronizarDeputados();
				System.out.println(">>> ETL concluído: " + repo.count() + " deputados no banco.");
			} else {
				System.out.println(">>> Banco já possui " + repo.count() + " deputados.");
			}
		};
	}
}