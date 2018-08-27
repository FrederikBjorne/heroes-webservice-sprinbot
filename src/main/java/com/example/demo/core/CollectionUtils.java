package com.example.demo.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CollectionUtils {
    /**
     * Zips a list of keys to a list of values with the same list size and returns a map.
     *
     * @param keys: A list keys of generic list item type K.
     * @param values: A list of values of generic list item type V.
     * @param <K>: The map generic key type.
     * @param <V>: The map genereic value type.
     * @return A map zipping keys with values.
     * @throws IllegalArgumentException if parameter lists are not the same sizes.
     */
    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        if (keys.size() != values.size()) {
            System.out.println(keys.size());
            System.out.println(values.size());
            throw new IllegalArgumentException ("Cannot combine lists with different sizes!");
        }
        Iterator<K> keyIter = keys.iterator();
        Iterator<V> valIter = values.iterator();
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(it -> keyIter.next(),
                                                                                it -> valIter.next()));
    }

    /**
     * Returns a filtered map on keys set by predicate method with key as parameter.
     *
     * @param map: The map to be filtered.
     * @param predicate: A predicate (boolean-valued function) with argument key of type K.
     * @param <K>: The map generic key type.
     * @param <V>: The map genereic value type.
     * @return A filtered map.
     *
     * Typical usage:
     * <code>
     *     Map<String, String> foo = new HashMap<>();
     *     foo.put("name", "Cristano Ronaldo");
     *     foo.put("teamName", "Juventus");
     *     foo.put("age", "30");
     *     foo.put("goal", "29");
     *     Map<String, String> bar = filterMapByKeys(this.statistics, item -> {item.equals("name") ||
     *                                                                         item.equals("teamName")));}
     *     bar.foreach(System::println);
     *     {name=Cristano Ronaldo}
     *     {teamName=Juventus}
     *
     * </code>
     */
    public static <K, V> Map<K, V> filterMapByKeys(Map<K, V> map, Predicate<K> predicate) {
        return map.entrySet().stream()
                .filter(item -> predicate.test(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
