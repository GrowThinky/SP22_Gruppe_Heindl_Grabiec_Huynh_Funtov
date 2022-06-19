package de.uni_marburg.iliasapp;

public class Modul {

 final public  String id;
 public String name;
 public String form;
 public String semester;
 public String tag;
 public String startTime;
 public String endTime;
 public String raum;
 public String dozent;

 public Modul(String id, String name, String form, String semster, String tag, String startTime, String bis, String raum, String dozent){
     this.id = id;
     this.name = name;
     this.form = form;
     this.semester = semester;
     this.tag = tag;
     this.startTime = startTime;
     this.endTime = bis;
     this.raum = raum;
     this.dozent = dozent;
 }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public String getTag() {
        return tag;
    }

    public String getStart() {
        return startTime;
    }

    public String getEnd() {
        return endTime;
    }

    public String getRaum() {
        return raum;
    }




    public enum Tag {
        MO, DI, MI, DO, FR ,SA,SO;
    }
}
