package com.example.demo.heroes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

import static com.example.demo.core.CollectionUtils.zipToMap;


/**
 This class reads heroes' properties stored as data source in CSV file.

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
     * Read statistics comma separated in a CSV file.
     *
     * Expects the first row to be the statistics attributes to be used as mapping keys. Then each new row's values
     * are expected to be a heroes's corresponding data and consequently,
     * they are mapped to these keys into a Map, e.g. {teamName=Real Madrid,
     *                                                 name=James Rodríguez,
     *                                                 ranking=20, ...}.
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

            Pattern findTrueCommas = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // avoid splitting "1959, October"
            String line;
            while ((line = br.readLine()) != null) {
                // read each new line as a heroes's detailed information
                heroes.add(new Hero(zipToMap(keys, Arrays.asList(findTrueCommas.split(line, -1)))));
            };
        }
        return heroes;
    }
}
