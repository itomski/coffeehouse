package de.lubowiecki;

import java.time.LocalDate;

public class Person {

    private String vorname;
    private String nachname;
    private LocalDate geburtsDatum;

    public Person(String vorname, String nachname, LocalDate geburtsDatum) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsDatum = geburtsDatum;
    }

    @Override
    public String toString() {
        return new StringBuilder(vorname)
                .append(" ")
                .append(nachname)
                .append(", ")
                .append(geburtsDatum.format(GuiHelper.DATE_FMT))
                .toString();
    }
}
