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
