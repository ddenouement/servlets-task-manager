package model;

/**
 * Enum that represents Category entity
 * @Author Yuliia Aleksandrova
 */

public enum Category {
    DESIGN, DANCE, SPORT, DATABASES, OTHER;
    public static Category getByNumber(int i) {
        return Category.values()[i];
    }
    public String getName() {
        return name();
    }
    public int getInt(){
        return ordinal()+1;
    }
    @Override
    public String toString(){
        return name();
    }

}

/*
public class Category {



    private int id;
    private String name;

    private String nameEn;
    private String nameUa;
  public Set<Activity> activities = new HashSet<Activity>();
  public Category (int id){
      this.id = id;
  }
  public Category(){

  }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameUa() {
        return nameUa;
    }

    public String getNameEn() {
        return nameEn;
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", activities=" + activities +
                '}';
    }
}
*/