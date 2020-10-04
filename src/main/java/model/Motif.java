package model;

import java.util.HashSet;
import java.util.Set;


public enum Motif {
    ADD, REMOVE;

    public static Motif getByNumber(int i) {
        return Motif.values()[i];
    }
    public String getName() {
        return name();
    }
    public int getInt(){
        return ordinal()+1;
    }
}

/*
public class Motif {

    private int id;
    private String name;

    private String nameEn;

    private String nameUa;


     private Set<Request> requests = new HashSet<Request>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Motif{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", requests=" + requests +
                '}';
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getNameUa() {
        return nameUa;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
*/