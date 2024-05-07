package com.example.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackManager {
    private final List<Feedback> feedbacks = new ArrayList<>();

    public void addFeedback(String message) {
        feedbacks.add(new Feedback(message));
    }
    public void deleteFeedback(Feedback feedback) {
        feedbacks.remove(feedback);
    }

    public List<Feedback> getLastThreeFeedbacks() {
        return feedbacks.stream()
                .skip(Math.max(0, feedbacks.size() - 3))
                .collect(Collectors.toList());
    }
}
