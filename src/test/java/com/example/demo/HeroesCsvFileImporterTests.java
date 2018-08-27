package com.example.demo;

import com.example.demo.heroes.Hero;
import com.example.demo.heroes.MissingExpectedPropertyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import com.example.demo.heroes.HeroCsvFileImporter;


@RunWith(SpringJUnit4ClassRunner.class)
public class HeroesCsvFileImporterTests {
    @Test
    public void testCompleteOneHero() throws MissingExpectedPropertyException, IOException {
        StringReader reader = new StringReader(
            "page_id,name,urlslug,id,align,eye,hair,sex,gsm,alive,apperances,apperance,year\n" +
               "1422,Batman (Bruce Wayne),\\/wiki\\/Batman_(Bruce_Wayne),Secret Identity,Good " +
               "Characters,Blue Eyes,Black Hair,Male Characters,,Living Characters,3093,\"1939, May\",1939\n");
        List<Hero> heroes = HeroCsvFileImporter.parse(reader);
        assertEquals(1, heroes.size());
        Hero hero = heroes.get(0);
        assertEquals("Batman", hero.getName());
        assertEquals("Bruce Wayne", hero.getAlterEgo());
        assertNotNull(hero.getShortInfo());
        assertEquals("-1", hero.getShortInfo().get("id"));
        assertEquals("Bruce Wayne", hero.getShortInfo().get("alterEgo"));
        assertEquals("Batman", hero.getShortInfo().get("name"));
        assertNotNull(hero.getDetails());
        assertEquals(12, hero.getDetails().size());
        assertEquals("1422", hero.getDetails().get("page_id"));
        assertEquals("\\/wiki\\/Batman_(Bruce_Wayne)", hero.getDetails().get("urlslug"));
        assertEquals("Secret Identity", hero.getDetails().get("id"));
        assertEquals("Good Characters", hero.getDetails().get("align"));
        assertEquals("Blue Eyes", hero.getDetails().get("eye"));
        assertEquals("Black Hair", hero.getDetails().get("hair"));
        assertEquals("Male Characters", hero.getDetails().get("sex"));
        assertEquals("", hero.getDetails().get("gsm"));
        assertEquals("Living Characters", hero.getDetails().get("alive"));
        assertEquals("3093", hero.getDetails().get("apperances"));
        assertEquals("\"1939, May\"", hero.getDetails().get("apperance"));
        assertEquals("1939", hero.getDetails().get("year"));
    }

    @Test
    public void testTwoPlayersWithoutStatisticsNormal() throws IOException, MissingExpectedPropertyException {
        StringReader reader = new StringReader(
                "page_id,name,id,year\n" +
                   "1422,Batman (Bruce Wayne),Secret Identity,1939\n" +
                   "23387,Superman (Clark Kent),Secret Identity,1986\n" +
                   "1471,Harvey Dent (New Earth),Public Identity,1942\n" +
                   "403631,GenderTest,Secret Identity,1956");

        List<Hero> heroes = HeroCsvFileImporter.parse(reader);
        assertEquals(4, heroes.size());
        Hero hero = heroes.get(0);
        assertEquals("Batman", hero.getName());
        assertEquals("Bruce Wayne", hero.getAlterEgo());
        assertNotNull(hero.getShortInfo());
        assertEquals("-1", hero.getShortInfo().get("id"));
        assertEquals("Bruce Wayne", hero.getShortInfo().get("alterEgo"));
        assertEquals("Batman", hero.getShortInfo().get("name"));
        assertNotNull(hero.getDetails());
        assertEquals(3, hero.getDetails().size());
        assertEquals("1422", hero.getDetails().get("page_id"));
        assertEquals("Secret Identity", hero.getDetails().get("id"));
        assertEquals("1939", hero.getDetails().get("year"));
        hero = heroes.get(1);
        assertEquals("Superman", hero.getName());
        assertEquals("Clark Kent", hero.getAlterEgo());
        assertNotNull(hero.getShortInfo());
        assertEquals("-1", hero.getShortInfo().get("id"));
        assertEquals("Clark Kent", hero.getShortInfo().get("alterEgo"));
        assertEquals("Superman", hero.getShortInfo().get("name"));
        assertNotNull(hero.getDetails());
        assertEquals(3, hero.getDetails().size());
        assertEquals("23387", hero.getDetails().get("page_id"));
        assertEquals("Secret Identity", hero.getDetails().get("id"));
        assertEquals("1986", hero.getDetails().get("year"));
        hero = heroes.get(2);
        assertEquals("Harvey Dent", hero.getName());
        assertEquals("New Earth", hero.getAlterEgo());
        assertNotNull(hero.getShortInfo());
        assertEquals("-1", hero.getShortInfo().get("id"));
        assertEquals("New Earth", hero.getShortInfo().get("alterEgo"));
        assertEquals("Harvey Dent", hero.getShortInfo().get("name"));
        assertNotNull(hero.getDetails());
        assertEquals(3, hero.getDetails().size());
        assertEquals("1471", hero.getDetails().get("page_id"));
        assertEquals("Public Identity", hero.getDetails().get("id"));
        assertEquals("1942", hero.getDetails().get("year"));
        hero = heroes.get(3);
        assertEquals("GenderTest", hero.getName());
        assertEquals("GenderTest", hero.getAlterEgo());
        assertNotNull(hero.getShortInfo());
        assertEquals("-1", hero.getShortInfo().get("id"));
        assertEquals("GenderTest", hero.getShortInfo().get("alterEgo"));
        assertEquals("GenderTest", hero.getShortInfo().get("name"));
        assertNotNull(hero.getDetails());
        assertEquals(3, hero.getDetails().size());
        assertEquals("403631", hero.getDetails().get("page_id"));
        assertEquals("Secret Identity", hero.getDetails().get("id"));
        assertEquals("1956", hero.getDetails().get("year"));
    }

    @Test
    public void testInconsistentColumnsShouldFail() throws IOException, MissingExpectedPropertyException {
        StringReader reader = new StringReader(
                "page_id,name,id,year\n" +
                   "1422,Batman (Bruce Wayne),Secret Identity,\"1939, May\",1939\\n");

        try {
            HeroCsvFileImporter.parse(reader);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testExpectedPropertyNameValueIsMissingShouldFail() throws IOException {
        StringReader reader = new StringReader("page_id,id,year\n" +
                                               "1422,Batman (Bruce Wayne),Secret Identity,1939\\n");

        try {
            HeroCsvFileImporter.parse(reader);
            fail();
        } catch (MissingExpectedPropertyException e) {
        }
    }

    @Test
    public void testEmptyEndingValuesShouldWork() throws IOException, MissingExpectedPropertyException {
        StringReader reader = new StringReader("page_id,name,id,apperances,apperance,year\n" +
                                               "1422,Batman (Bruce Wayne),Secret Identity,,,\n");

        List<Hero> heroes = HeroCsvFileImporter.parse(reader);
        assertEquals(1, heroes.size());
        Hero hero = heroes.get(0);
        assertEquals("Batman", hero.getName());
        assertEquals("Bruce Wayne", hero.getAlterEgo());
        assertNotNull(hero.getShortInfo());
        assertEquals("-1", hero.getShortInfo().get("id"));
        assertEquals("Bruce Wayne", hero.getShortInfo().get("alterEgo"));
        assertEquals("Batman", hero.getShortInfo().get("name"));
        assertNotNull(hero.getDetails());
        assertEquals(5, hero.getDetails().size());
        assertEquals("1422", hero.getDetails().get("page_id"));
        assertEquals("Secret Identity", hero.getDetails().get("id"));
        assertEquals("", hero.getDetails().get("apperances"));
        assertEquals("", hero.getDetails().get("apperance"));
        assertEquals("", hero.getDetails().get("year"));
    }
}
