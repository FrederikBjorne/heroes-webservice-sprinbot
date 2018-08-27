package com.example.demo.heroes;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


class HeroNotFoundException extends Exception {

    public HeroNotFoundException(String param, String userId) {
        super("Could not find heroes with " + param + ": '" + userId + "'.");
    }
}


/**
 * This class implements the Hero service bean.
 */
@Service
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    /**
     * Returns all heroes with lightweight representation of each each heroes.
     *
     * @return A collection of heroes with each heroes as a Map<key, value>.
     */
    public Collection<Map<String, String>> getAllHeroesShortInfo() {
        Collection<Map<String, String>> heroes = new ArrayList<>();
        heroRepository.findAll().forEach(hero -> {heroes.add(hero.getShortInfo());});
        return heroes;
    }

    /**
     * Return a heroes's detailed information identified by id from repository.
     *
     * @param id: The identifier id of a Hero as a natural number, e.g. "1".
     * @return The heroes's detailed information as a Map<key, value>.
     * @throws HeroNotFoundException if heroes is not found.
     */
    public Hero getHeroAllInformationById(Long id) throws HeroNotFoundException {
        return heroRepository.findById(id).orElseThrow(() -> new HeroNotFoundException("id", id.toString()));
    }

    /**
     * Return a heroes's detailed information identified by name from repository.
     *
     * @param shortName: The name of a Hero, e.g. "Batman". Notice that it is case sensitive.
     * @return The heroes's detailed information as a Map<key, value>.
     * @throws HeroNotFoundException if heroes is not found.
     */
    public Hero getHeroAllInformationByName(String shortName)  throws HeroNotFoundException {
        return heroRepository.findByName(shortName).orElseThrow(() -> new HeroNotFoundException("shortName",
                                                                                                shortName));
    }
}
