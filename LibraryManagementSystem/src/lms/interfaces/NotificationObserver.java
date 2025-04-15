package lms.interfaces;

import lms.model.User;
import lms.model.MemberUser;

public interface NotificationObserver {
    void addObserver(User user);
    void removeObserver(User user);   
    void checkMemberBooks(User user);
}
