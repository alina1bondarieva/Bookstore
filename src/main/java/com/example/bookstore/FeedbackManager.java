package com.example.bookstore;

import java.util.ArrayList;
import java.util.List;

public class FeedbackManager {
    private final List<Feedback> feedbacks = new ArrayList<>();

    public void addFeedback(String message) {
        feedbacks.add(new Feedback(message));
    }
    public void deleteFeedback(Feedback feedback) {
        feedbacks.remove(feedback);
    }

    public List<Feedback> getLastThreeFeedbacks() {
        int size = feedbacks.size();
        return size > 3 ? feedbacks.subList(size - 3, size) : new ArrayList<>(feedbacks);
    }
}
