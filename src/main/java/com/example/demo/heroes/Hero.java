package com.example.demo.heroes;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents a heroes entity.
 *
 * Usage:
 * <code>
 *     Map<String, String> heroInfo = new HashMap<>();
 *     heroInfo.put("page_id", "1422");
 *     heroInfo.put("name", "Batman (Bruce Wayne)");
 *     heroInfo.put("id", "Secret Identity");
 *     heroInfo.put("eye", "Blue Eyes");
 *     heroInfo.put("hair", "Black Hair");
 *     heroInfo.put("sex", "Male Characters");
 *     heroInfo.put("year", "1939"); // etc..
 *
 *     Hero HeroEntity = Hero(heroInfo);
 *     System.out.println(heroEntity);
 * </code>
 */
@Entity
public class Hero extends BaseEntity {
    // light weight representation of a hero
    private String alterEgo;
    private String name;

    @ElementCollection
    private Map<String, String> details; // All information excluded light weight information

    @JsonIgnore
    final static List<String> EXPECTED_PROPS = Arrays.asList("name", "page_id");

    private Hero() {
        super();
    }

    /**
     * The imported source data is split into a few class attributes (light weight info) and the details in a
     * separated details map.
     * @param heroInfo: A hashmap of all source data as key and value pairs.
     */
    public Hero(Map<String, String> heroInfo) {
        this();
        String fullName = heroInfo.remove("name");
        String[] nameParts = fullName.split("\\s+\\(", -1);
        name = nameParts[0];
        alterEgo = nameParts.length > 1 ? nameParts[1].replace(")", "") : name;
        details = heroInfo;
    }

    /**
     * Returns the hero's alter ego, e.g. "Clarke Kent".
     * @return A string value.
     */
    public String getAlterEgo() {
        return alterEgo;
    }

    /**
     * Returns the hero name, e.g "Superman".
     * @return A string value.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns short info (name, fullName and id).
     * @return A Map<key, value> of all all non-statistic data.
     */
    @JsonIgnore
    public Map<String, String> getShortInfo() {
        Map<String, String> shortInfo = new HashMap<>();
        shortInfo.put("id", Long.toString(getId() != null ? getId() : -1));  // Id is null until set by repository
        shortInfo.put("alterEgo", getAlterEgo());
        shortInfo.put("name", getName());
        return shortInfo;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    @Override public String toString() {
        return "\nName: " + getName() + " (" + getAlterEgo() + ")" + "\ndetails" + "[" + getDetails().toString() + "]";
    }
}
