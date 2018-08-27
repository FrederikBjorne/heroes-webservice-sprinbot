package com.example.demo.heroes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class handling source data and storing Hero objects in repository.
 */
@Component
public class HeroRepositoryHandler implements ApplicationRunner {
    private static final String HEROES_CSV_FILE = "dc_heroes.csv";
    private HeroRepository heroesRepo;

    @Autowired
    public HeroRepositoryHandler(HeroRepository heroesRepo) { // used by JPA
        this.heroesRepo = heroesRepo;
    }

    /**
     Right after Spring has started, this hook is run where we parse all heroes' information from a source CSV file
     located in resources directory and stores each Hero object entities as defined by the auto-generated implementation
     of HeroRepository.
     @throws java.io.FileNotFoundException if HEROES_CSV_FILE does not exist in resources directory.
     */
    @Override
    public void run(ApplicationArguments args) throws IOException, MissingExpectedPropertyException {
        FileReader reader = new FileReader(new ClassPathResource(HEROES_CSV_FILE).getFile());
        List<Hero> heroes = HeroCsvFileImporter.parse(reader);
        heroes.forEach(System.out::println);
        heroesRepo.saveAll(heroes);
    }
}
