package de.uni_marburg.iliasapp;

public class Gebaeude {
    private String gebaeudeNr;
    private String adresse;
    private String breitengrad;
    private String laengengrad;

    public Gebaeude(String gebaeudeNr, String adresse, String breitengrad, String laengengrad) {
        this.gebaeudeNr = gebaeudeNr;
        this.adresse = adresse;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getGebaeudeNr() {
        return gebaeudeNr;
    }

    public String getBreitengrad() {
        return breitengrad;
    }

    public String getLaengengrad() {
        return laengengrad;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setGebaeudeNr(String gebaeudeNr) {
        this.gebaeudeNr = gebaeudeNr;
    }

    public void setBreitengrad(String breitengrad) {
        this.breitengrad = breitengrad;
    }

    public void setLaengengrad(String laengengrad) {
        this.laengengrad = laengengrad;
    }
}