package lms.interfaces;

import lms.model.User;
import java.util.ArrayList;

public interface UserOperations {
    User login(String id, String password);
    boolean updateUser(int id, String name, String role, String email);
    boolean createUser(String name, String role, String email, String password);
    boolean deleteUser(int id);
    ArrayList<User> getAllUsers();
}