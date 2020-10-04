package model;

import java.util.HashSet;
import java.util.Set;


public enum Progress {
    REQUESTED, ASSIGNED, FINISHED, CANCELLED;

    public static Progress getByNumber(int i) {
        return Progress.values()[i];
    }
    public String getName() {
        return name();
    }
    public int getInt(){
        return ordinal()+1;
    }
}

/*
public class Progress {

    private int id;

    private String name;

    private String nameEn;

    private String nameUa;
   public Set<UserActivity> userActivities = new HashSet<UserActivity>();

    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", userActivities=" + userActivities +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
*/