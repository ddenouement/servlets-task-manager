package model;

/**
 * Class that represents Category entity
 *
 * @Author Yuliia Aleksandrova
 */

public class Category {

    private int id;
    private String name;
    private String nameEn;
    private String nameUa;

    public Category(int id) {
        this.id = id;
    }

    public Category() {

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
                ", nameUa='" + nameUa;
    }
}
