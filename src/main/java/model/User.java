package model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
  //  private Profession profession;
    private Role role;
  //  private int roleId;

    public User(){

    }

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = Role.valueOf (role.toUpperCase());
    }

    public int getId() {
        return id;
    }


 /*   public int getRoleId() {
        return roleId;
    }*/

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
/*   public void setRoleId(int roleId) {
        this.roleId = roleId;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", passwordEncoded='" + password + '\'' ;
         //       ", roleId=" + roleId ;
    }
}
