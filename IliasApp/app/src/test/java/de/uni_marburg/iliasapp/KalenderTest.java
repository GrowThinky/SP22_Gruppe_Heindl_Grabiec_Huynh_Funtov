package de.uni_marburg.iliasapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class KalenderTest {

    @Test
    public void creatNewKaldendereintrag() {
        Kalender k = new Kalender();
        k.creatNewKaldendereintrag("Datenbanken", "10:30", "14:30", "Mi");
    }
}