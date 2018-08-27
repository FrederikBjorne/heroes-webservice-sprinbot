package com.example.demo.heroes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

import static com.example.demo.core.CollectionUtils.zipToMap;


/**
 This class reads hero information from a comma separated CSV file.

 Typical usage:
 <code>
     FileReader reader = new FileReader(new ClassPathResource(PLAYERS_CSV_FILE).getFile());
     List<Hero> heroes = PlayersCSVFileImporter.parse(reader);
     heroes.forEach(System.out::println);
 </code>
*/
public class HeroCsvFileImporter
{
    /**
     * Read hero information from a comma separated CSV file and return Hero objects.
     *
     * Expects the first row to be the attribute names to be used as mapping keys/columns. Then each new row's values
     * are expected to be a heroes's corresponding data and consequently, are mapped to these keys into a Map, e.g.
     *                                                                                  {page_id=1422,
     *                                                                                   name="Batman (Bruce Wayne)",
     *                                                                                   year=1939, ...}.
     * @param reader: An input character stream reader.
     * @return A collection of Hero objects.
     * @throws MissingExpectedPropertyException is thrown if expected properties are missing.
     * @throws IOException if file is not found.
     */
    public static List<Hero> parse(Reader reader) throws MissingExpectedPropertyException, IOException {
        List<Hero> heroes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(reader)) { // try-with-resource
            // read the first line from the text file as keys
            List<String> keys = Arrays.asList(br.readLine().split(","));
            for (String expectedKey : Hero.EXPECTED_PROPS) {
                if (!(keys.contains(expectedKey))) {
                    throw new MissingExpectedPropertyException(expectedKey);
                }
            }

            Pattern findTrueCommas = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // avoid split "1959, October"
            String line;
            while ((line = br.readLine()) != null) {
                // read each new line as a hero's detailed information and map to keys
                heroes.add(new Hero(zipToMap(keys, Arrays.asList(findTrueCommas.split(line, -1)))));
            };
        }
        return heroes;
    }
}
