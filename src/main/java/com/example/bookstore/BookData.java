package com.example.bookstore;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 277, "A story of teenage angst and alienation."));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, 324, "A story of racial injustice and the destruction of innocence."));
        books.add(new Book("1984", "George Orwell", 1949, 328, "A dystopian social science fiction novel and cautionary tale."));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 218, "A story of the fabulously wealthy Jay Gatsby and his love for Daisy Buchanan."));
        return books;
    }
}
