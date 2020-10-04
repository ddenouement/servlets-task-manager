package model;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Activity {

        private int id;

        private String name;
        private String nameEn;
        private String nameUa;

        private int peopleAmount;


        private String  description;
        private String  descriptionEn;
        private String  descriptionUa;


        private Category category;

        public Set<Request> requests = new HashSet<Request>();
        public Set<UserActivity> userActivities = new HashSet<UserActivity>(); ;

        public void setId(int id) {
                this.id = id;
        }

        public int getId() {
                return id;
        }

        public int getPeopleAmount() {
                return peopleAmount;
        }

        public void setPeopleAmount(int peopleAmount) {
                this.peopleAmount = peopleAmount;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getName() {
                return name;
        }

        public void setNameEn(String nameEn) {
                this.nameEn = nameEn;
        }

        public void setNameUa(String nameUa) {
                this.nameUa = nameUa;
        }

        public String getNameUa() {
                return nameUa;
        }

        public String getNameEn() {
                return nameEn;
        }

        public String getDescriptionEn() {
                return descriptionEn;
        }

        public String getDescriptionUa() {
                return descriptionUa;
        }

        public void setDescriptionEn(String descriptionEn) {
                this.descriptionEn = descriptionEn;
        }

        public void setDescriptionUa(String descriptionUa) {
                this.descriptionUa = descriptionUa;
        }

        @Override
        public String toString() {
                return "Activity{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", category=" + category +
                        ", requests=" + requests +
                        ", userActivities=" + userActivities +
                        '}';
        }

        public Category getCategory() {
                return category;
        }

        public Set<Request> getRequests() {
                return requests;
        }

        public Set<UserActivity> getUserActivities() {
                return userActivities;
        }

        public String getDescription() {
                return description;
        }

        public void setCategory(String category) {
                this.category = Category.valueOf(category.toUpperCase());
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public void setRequests(Set<Request> requests) {
                this.requests = requests;
        }

        public void setUserActivities(Set<UserActivity> userActivities) {
                this.userActivities = userActivities;
        }
}
