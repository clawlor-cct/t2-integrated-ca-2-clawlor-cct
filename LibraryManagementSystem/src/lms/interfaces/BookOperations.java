package lms.interfaces;

import lms.model.Book;
import java.util.ArrayList;
import lms.model.User;

public interface BookOperations {
    boolean borrowBook(User user, Book book);
    boolean returnBook(User user, Book book);
    boolean updateBook(int id, String title, String author, String genre, boolean isAvailable);
    boolean createBook(String title, String author, String genre, boolean isAvailable);
    boolean deleteBook(int id);
    Book getBook(int id);
    ArrayList<Book> getAllBooks();
    ArrayList<Book> getAllUserBooks(User user);
}