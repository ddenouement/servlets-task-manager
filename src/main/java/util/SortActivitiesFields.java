package util;

/**
 * Class to store fields from Database used in sorting
 *
 * @Author Yuliia Aleksandrova
 */
public enum SortActivitiesFields {

    ACTIVITY_PPL_COUNT("number_of_people"),
    ACTIVITY_CATEGORY("id_category"),
    ACTIVITY_NAME("name");
    private String value;

    SortActivitiesFields(String a) {
        this.value = a;
    }

    public String getValue() {
        return this.value;
    }

}
