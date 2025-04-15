package lms.factory;

import lms.model.AdminUser;
import lms.model.MemberUser;
import lms.model.User;

public class UserFactory {
    public static User createUser(int id, String name, String role, String email, String password) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return new AdminUser(id, name, email, password);
            case "MEMBER":
                return new MemberUser(id, name, email, password);
            default:
                return new User(id, name, email, password, role);
        }
    }
}
