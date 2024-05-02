package com.example.bookstore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Feedback {
    private final LocalDateTime timestamp;
    private final String message;

    public Feedback(String message) {
        this.timestamp = LocalDateTime.now(); // Automatically sets the current time
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
        return "at: " + timestamp.format(formatter) + "  message: " + message;
    }
}