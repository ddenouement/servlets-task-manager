package model;

public enum  Role {

        USER, ADMIN;

        public static Role getByNumber(int i) {

            return Role.values()[i];
        }
    public int getInt(){
        return ordinal()+1;
    }
        public String getName() {
            return name();
        }

}
