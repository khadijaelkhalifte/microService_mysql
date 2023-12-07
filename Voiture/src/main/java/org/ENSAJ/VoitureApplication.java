package org.ENSAJ;
import org.ENSAJ.Model.Voiture;
import org.ENSAJ.Repository.VoitureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@EnableFeignClients
@SpringBootApplication
public class VoitureApplication {
	private VoitureRepository voitureRepository;
	private ClientService clientService;
	public static void main(String[] args) {
		SpringApplication.run(VoitureApplication.class, args);
	}
	@FeignClient(name="SERVICE-CLIENT")
	interface ClientService{
		@GetMapping(path="/clients/{id}")
		public Client clientById(@PathVariable Long id);
	}


    @Transactional
    @Bean
    CommandLineRunner initializeDatabase(VoitureRepository voitureRepository, ClientService clientService) {
        return args -> {
            Client c1 = clientService.clientById(2L);
            Client c2 = clientService.clientById(1L);
            System.out.println("**************************");
            System.out.println("Id est :" + c1.getId());
            System.out.println("Nom est :" + c1.getNom());
            System.out.println("**************************");
            voitureRepository.save(new Voiture(1L, "BMW", "A 75 373", "75", c2));
            voitureRepository.save(new Voiture(2L, "Renault", "B 8 3986", "Megane", c2));
            voitureRepository.save(new Voiture(3L, "ferrari", "A 86 3244", "96", c1));
        };

	}


}
