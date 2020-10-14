package model;
/**
 * Enum that represents Status entity
 * @Author Yuliia Aleksandrova
 */
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