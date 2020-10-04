package util;

public enum SortActivitiesFields {

    ACTIVITY_PPL_COUNT ("number_of_people"),
    ACTIVITY_CATEGORY  ("category"),
    ACTIVITY_NAME("name");
     private String value;
      SortActivitiesFields(String a){
         this.value = a;
    }

    public String getValue() {
        return this.value;
    }

}
