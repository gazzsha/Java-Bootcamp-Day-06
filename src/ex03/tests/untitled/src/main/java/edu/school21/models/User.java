package edu.school21.models;

public class User {
    private Integer id;
    private String login;
    private String password;

    private  Boolean status = false;

    public User() {
    }

    public User(Integer id, String login, String password, Boolean status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
