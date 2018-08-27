package com.example.demo.heroes;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface HeroRepository extends CrudRepository<Hero, Long> {
    /**
     * Returns all the Hero objects from the repository by name.
     * @param name: The name of a hero, e.g. "Batman".
     * @return A heroes object matching the name or null if not.
     */
    Optional<Hero> findByName(String name);  // name needs to be property of Hero.
}
