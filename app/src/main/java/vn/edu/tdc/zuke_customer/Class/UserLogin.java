package vn.edu.tdc.zuke_customer.Class;

public class UserLogin {
    private String username, password, status;
    private int role_id;

    public UserLogin(String username, String password, String status, int role_id) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role_id = role_id;
    }

    public UserLogin() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public int getRole_id() {
        return role_id;
    }
}
