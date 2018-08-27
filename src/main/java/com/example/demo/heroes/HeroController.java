package com.example.demo.heroes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;


/**
 This class represents the controller of the Hero REST API (JSON).
 */
@RestController
public class HeroController {
    @Autowired
    private HeroService heroService;

    /**
     * Returns all heroes with light weight information (excluded statistics) as REST endpoint /api/heroes.
     *
     * @return An array of heroes with each heroes as a Map<key, value>.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/heroes")
    Collection<Map<String, String>> getAllHeros() {
        return heroService.getAllHeroesShortInfo();
    }

    /**
     * Returns an object containing all fields for a specific hero by id as REST endpoint /api/heroes/:id.
     *
     * @param id: The identifier id of a Hero as a natural number.
     * @return The heroes's detailed statistics as a Map<key, value>.
     * @throws HeroNotFoundException if heroes is not found.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/hero/:{id}")
    Hero getHeroAllInformationById(@PathVariable Long id) throws HeroNotFoundException {
        return heroService.getHeroAllInformationById(id);
    }

    /**
     * returns an object containing all fields for a specific hero by name as REST endpoint /api/heroes/name.
     *
     * @param shortName: The name of a Hero, e.g. "Batman". Notice that it is case sensitive.
     * @return The heroes's detailed statistics as a Map<key, value>.
     * @throws HeroNotFoundException if heroes is not found.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/hero/{shortName}")
    Hero getHeroAllInformationByName(@PathVariable String shortName) throws HeroNotFoundException {
        return heroService.getHeroAllInformationByName(shortName);
    }
}
