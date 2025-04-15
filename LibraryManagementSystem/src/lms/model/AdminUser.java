package lms.model;

public class AdminUser extends User {
    public AdminUser(int id, String name, String email, String password) {
        super(id, name, "Admin", email, password);
    }
}
