package model;


import java.util.HashSet;
import java.util.Set;

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
}
