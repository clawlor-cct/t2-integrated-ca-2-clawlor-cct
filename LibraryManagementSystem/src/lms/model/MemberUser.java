package lms.model;

public class MemberUser extends User {
    public MemberUser(int id, String name, String email, String password) {
        super(id, name, email, password, "Member");
    }
}
