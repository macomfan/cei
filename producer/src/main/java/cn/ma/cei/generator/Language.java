package cn.ma.cei.generator;

import java.util.Objects;

public class Language implements Comparable<Language> {
    public static final Language NA = new Language("", "");

    private final String name;
    private final String workingName;

    public Language(String name, String workingName) {
        this.name = name;
        this.workingName = workingName;
    }

    public String getName() {
        return name;
    }

    public String getWorkingName() {
        return workingName;
    }

    @Override
    public int compareTo(Language o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return name.equals(language.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
