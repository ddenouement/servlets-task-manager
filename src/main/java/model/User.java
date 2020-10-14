package model;

import dto.UserDTO;
import java.util.HashSet;
import java.util.Set;
/**
 * Model that represents User entity
 * @Author Yuliia Aleksandrova
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private Role role;

    public User() {

    }

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

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", passwordEncoded='" + password + '\'';
    }

    public Set<String> validate() {
        Set<String> errors = new HashSet<>();
        if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) errors.add("validation.email_format");
        if (!(login.length() > 5)) errors.add("validation.login_size");
        if (!(password.length() > 5)) errors.add("validation.password_size");
        return errors;
    }

    public UserDTO getSimpleUserDTO() {
        UserDTO userDTO = new UserDTO.Builder()
                .withEmail(this.email)
                .withFirstname(this.firstName)
                .withLastname(this.lastName)
                .withLogin(this.login)
                .withId(this.id)
                .withRole(this.role.getName())
                .build();
        return userDTO;
    }

    public static class Builder {
        private User user;

        public Builder() {
            this.user = new User();
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

        public Builder withPassword(String password) {
            user.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            user.email = email;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
