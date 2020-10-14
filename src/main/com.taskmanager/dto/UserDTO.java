package dto;

import model.Role;
import model.User;

/**
 * Class represents DTO for User
 * it has all fields of User, excluding password
 * @Author  Yuliia Aleksandrova
 */
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private Role role;


    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role != null)
            this.role = Role.valueOf(role.toUpperCase());
    }

    public int getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public static class Builder {
        private UserDTO user;

        public Builder() {
            this.user = new UserDTO();
        }

        public Builder withFirstname(String name) {
            user.firstName = name;
            return this;
        }

        public Builder withLastname(String surname) {
            user.lastName = surname;
            return this;
        }

        public Builder withId(int id) {
            user.id = id;
            return this;
        }

        public Builder withRole(String role) {
            if (role != null)
                user.role = Role.valueOf(role.toUpperCase());
            return this;
        }

        public Builder withLogin(String login) {
            user.login = login;
            return this;
        }

        public Builder withEmail(String email) {
            user.email = email;
            return this;
        }

        public UserDTO build() {
            return user;
        }
    }

}
