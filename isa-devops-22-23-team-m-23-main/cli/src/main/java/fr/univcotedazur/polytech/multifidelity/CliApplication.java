package fr.univcotedazur.polytech.multifidelity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CliApplication {


    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(CliApplication.class, args), () -> 0);
    }
}
