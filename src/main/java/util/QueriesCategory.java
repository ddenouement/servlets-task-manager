package util;

public class QueriesCategory {
    public static final String SQL_CREATE_CATEGORY = "insert into category(name, nameEn, nameUa) values (?, ?, ?)";
    public static final String SQL_FIND_CATEGORY_BY_ID = "SELECT * FROM category where id = ?";
    public static final String SQL_FIND_ALL_CATEGORIES = "select * from category";

}
