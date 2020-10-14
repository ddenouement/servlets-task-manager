package model;
/**
 * Enum that represents Motif entity
 * @Author Yuliia Aleksandrova
 */
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
