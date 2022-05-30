package de.uni_marburg.iliasapp;

public class Modul {

 final public  String id;
 public String name;
 public String tag;
 public String startTime;
 public String endTime;
 public String raum;

 public Modul(String id, String name, String tag, String startTime, String bis, String raum){
     this.id = id;
     this.name = name;
     this.tag = tag;
     this.startTime = startTime;
     this.endTime = bis;
     this.raum = raum;
 }

    public enum Tag {
        MO, DI, MI, DO, FR ,SA,SO;
    }
}
