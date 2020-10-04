package model;

import java.util.HashSet;
import java.util.Set;
public enum Status {

    CREATED, ACCEPTED, REJECTED;

    public static Status getByNumber(int i) {
        return Status.values()[i];
    }
    public String getName() {
        return name();
    }
    public int getInt(){
        return ordinal()+1;
    }
}
/*
public class Status {

    private int id;
    private String name;

    private String nameEn;
    private String nameUa;
   private Set<Request> requests = new HashSet<Request>();

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", requests=" + requests +
                '}';
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getName() {
        return name;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

*/