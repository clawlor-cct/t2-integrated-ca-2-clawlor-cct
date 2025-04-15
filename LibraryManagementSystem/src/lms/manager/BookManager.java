package lms.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import lms.database.MySQL;
import lms.factory.BookFactory;
import lms.interfaces.BookOperations;
import lms.model.Book;
import lms.model.User;

public class BookManager implements BookOperations {
    private static BookManager instance;

    private BookManager() { }

    public static BookManager getInstance() {
        if (instance == null) {
           instance = new BookManager();
        }
        return instance;
    }
     
    public boolean borrowBook(User user, Book book) {
        MySQL.executeUpdate("UPDATE books SET Availability = ? WHERE ID = ?", new Object[]{false, book.getId()});
        MySQL.executeUpdate("INSERT INTO transactions (BookID, UserID, Status) VALUES (?, ?, ?)", new Object[]{book.getId(), user.getId(), "Issued"});
        JOptionPane.showMessageDialog(null, "Book issued!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean returnBook(User user, Book book) {
        MySQL.executeUpdate("UPDATE books SET Availability = ? WHERE ID = ?", new Object[]{true, book.getId()});
        MySQL.executeUpdate("UPDATE transactions SET Status = ?, ReturnDate = now() WHERE BookID = ?", new Object[]{"Returned", book.getId()});
        JOptionPane.showMessageDialog(null, "Book returned!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean updateBook(int id, String title, String author, String genre, boolean isAvailable) {
        MySQL.executeUpdate("UPDATE books SET Title = ?, Author = ?, Genre = ?, Availability = ? WHERE ID = ?", new Object[]{title, author, genre, isAvailable, id});
        JOptionPane.showMessageDialog(null, "Book updated successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean createBook(String title, String author, String genre, boolean isAvailable) {
        MySQL.executeUpdate("INSERT INTO books (Title, Author, Genre, Availability) VALUES (?, ?, ?, ?)", new Object[]{title, author, genre, isAvailable});
        JOptionPane.showMessageDialog(null, "Book created successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean deleteBook(int id) {
        MySQL.executeUpdate("DELETE FROM books WHERE ID = ?;", new Object[]{id});
        JOptionPane.showMessageDialog(null, "Book deleted successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public Book getBook(int id) {
        ResultSet resultSet = MySQL.executeQuery("SELECT * FROM books WHERE ID = ?", new Object[]{id});
        try {
            // Iterate through received book and use BookFactory to return instance
            ArrayList<Book> books = new ArrayList<Book>();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("ID");
                String bookTitle = resultSet.getString("Title");
                String bookAuthor = resultSet.getString("Author");
                String bookGenre = resultSet.getString("Genre");
                boolean bookAvailable = resultSet.getBoolean("Availability");

                return BookFactory.createBook(bookId, bookTitle, bookAuthor, bookGenre, bookAvailable);
            }
        }catch(SQLException e) {
            System.out.println("BookManager: SQLException.");
        }
        return null;
    }
    
    public ArrayList<Book> getAllBooks() {
        ResultSet resultSet = MySQL.executeQuery("SELECT * FROM books", new Object[]{});
        
        try {
            // Iterate through all received books and use BookFactory to create instance of each
            ArrayList<Book> books = new ArrayList<Book>();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("ID");
                String bookTitle = resultSet.getString("Title");
                String bookAuthor = resultSet.getString("Author");
                String bookGenre = resultSet.getString("Genre");
                boolean bookAvailable = resultSet.getBoolean("Availability");

                books.add(BookFactory.createBook(bookId, bookTitle, bookAuthor, bookGenre, bookAvailable));
            }
            return books;
        }catch(SQLException e) {
            System.out.println("BookManager: SQLException.");
        }
        return null;
    }
    
    public ArrayList<Book> getAllUserBooks(User user) {
        ResultSet resultSet = MySQL.executeQuery("SELECT b.ID, b.Title, b.Author, b.Genre, b.Availability FROM books b JOIN transactions t ON b.ID = t.BookID WHERE t.UserID = ? AND t.Status = 'Issued'", new Object[]{user.getId()});
        
        try {
            ArrayList<Book> books = new ArrayList<Book>();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("ID");
                String bookTitle = resultSet.getString("Title");
                String bookAuthor = resultSet.getString("Author");
                String bookGenre = resultSet.getString("Genre");
                boolean bookAvailable = resultSet.getBoolean("Availability");

                books.add(BookFactory.createBook(bookId, bookTitle, bookAuthor, bookGenre, bookAvailable));
            }
            return books;
        }catch(SQLException e) {
            System.out.println("BookManager: SQLException.");
        }
        return null;
    }
}
