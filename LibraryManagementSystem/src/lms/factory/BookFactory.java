package lms.factory;

import lms.model.Book;

public class BookFactory {
    public static Book createBook(int id, String title, String author, String genre, boolean isAvailable) {
        return new Book(id, title, author, genre, isAvailable);
    }
}
