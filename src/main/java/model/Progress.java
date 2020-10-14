package model;

/**
 * Enum that represents Progress entity
 * @Author Yuliia Aleksandrova
 */
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
