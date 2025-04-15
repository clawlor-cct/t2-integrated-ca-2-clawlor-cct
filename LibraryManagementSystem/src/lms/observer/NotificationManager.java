package lms.observer;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lms.interfaces.BookOperations;
import lms.interfaces.NotificationObserver;
import lms.manager.BookManager;
import lms.model.Book;
import lms.model.User;

public class NotificationManager implements NotificationObserver {
    private final BookOperations bookOperations = BookManager.getInstance();
    private List<User> observers = new ArrayList<User>();
    
    public void addObserver(User user) {
        observers.add(user);
    }
    
    public void removeObserver(User user) {
        observers.remove(user);
    }
    
    public void checkMemberBooks(User user) {
        ArrayList<Book> books = bookOperations.getAllUserBooks(user); 
        for (Book book : books) {
           if (!book.isAvailable()) {
               notifyObservers("Book: \"" + book.getTitle() + "\" is due for return.");
           }
        }
    }
    
    private void notifyObservers(String s) {
        for (User user : observers) {
            JOptionPane.showMessageDialog(null, s, "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}