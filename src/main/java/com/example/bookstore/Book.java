package com.example.bookstore;

public class Book {
    private final String name;
    private final String author;
    private final int year;
    private final int numberOfPages;
    private final String description;
    private final String filePath; // New attribute

    public Book(String name, String author, int year, int numberOfPages, String description, String filePath) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.description = description;
        this.filePath = filePath;
    }

    public Book(String name, String author, int year, int numberOfPages, String description) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.description = description;

        if (this.name == "The Catcher in the Rye"){
            this.filePath = "./src/main/resources/books/The_Catcher_in_the_Rye.txt";
        } else if (this.name == "To Kill a Mockingbird"){
            this.filePath = "./src/main/resources/books/To_Kill_a_Mockingbird.txt";
        } else if (this.name == "1984"){
            this.filePath = "./src/main/resources/books/1984.txt";
        } else if (this.name == "The Great Gatsby"){
            this.filePath = "./src/main/resources/books/The_Great_Gatsby.txt";
        } else {
            this.filePath = "./src/main/resources/books/" + name.replaceAll("\\s+", "_") + ".txt";
        }
    }


    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getDescription() {
        return description;
    }
    public String getFilePath() {
        return filePath;
    }
}