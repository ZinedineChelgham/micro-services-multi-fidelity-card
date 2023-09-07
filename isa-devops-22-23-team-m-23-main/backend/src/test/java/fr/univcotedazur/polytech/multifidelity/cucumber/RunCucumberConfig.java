package fr.univcotedazur.polytech.multifidelity.cucumber;

import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
@SpringBootTest
public class RunCucumberConfig {
    /*@Bean   a faire pour que le Autowired marche ??
    public CustomerRepository customerRepository() {
        return new CustomerRepository();
    }*/
}
