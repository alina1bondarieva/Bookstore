package com.example.bookstore;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Optional;
import java.util.Stack;

public class BookstoreApp extends Application {

    private final FeedbackManager feedbackManager = new FeedbackManager();
    private final Stack<Parent> viewHistory = new Stack<>();
    private ListView<Book> bookListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My Bookstore <3");

        BorderPane border = new BorderPane();
        Pane titlePage = new Pane();
        titlePage.setPrefSize(900,200);
//        border.setStyle("-fx-background-color: #FFC5C5;"); // light pink
//        Image image = new Image("/Users/alinabondarieva/IdeaProjects/Bookstore/src/main/java/com/example/bookstore/images/img1.jpg");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(900);
//        imageView.setFitHeight(200);

        // Define buttons
        Button homeButton = new Button("Home");
        Button searchButton = new Button("Search");
        Button watchlistButton = new Button("My Watchlist");
        Button addBookButton = new Button("Add Book");

        // Attach button events
        homeButton.setOnAction(e -> border.setCenter(bookListView));
        searchButton.setOnAction(e -> searchView(border));
        addBookButton.setOnAction(e -> addBookView(border));

        VBox navigation = new VBox(10, homeButton, searchButton, watchlistButton, addBookButton);
        navigation.setAlignment(Pos.CENTER);
        border.setBottom(navigation);

        TextArea contentArea = new TextArea("Select an option from the bottom.");
        contentArea.setEditable(false);
        border.setCenter(contentArea);

        bookListView.getItems().addAll(BookData.getBooks());
        bookListView.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getName() + " by " + book.getAuthor());
                }
            }
        });

        bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                showBookDetails(newValue, border));

        Scene scene = new Scene(border, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void searchView(BorderPane border) {
        TextField searchField = new TextField();
        searchField.setPromptText("Enter book name or author");

        ListView<Book> searchResults = new ListView<>();
        searchResults.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getName() + " by " + book.getAuthor());
                }
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchResults.getItems().clear();
            bookListView.getItems().stream()
                    .filter(book -> book.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(newValue.toLowerCase()))
                    .forEach(searchResults.getItems()::add);
        });
        // Add a listener to handle when a book is selected from the search results
        searchResults.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                showBookDetails(newValue, border));

        VBox searchView = new VBox(10, searchField, searchResults);
        searchView.setAlignment(Pos.CENTER);
        border.setCenter(searchView);
    }

    private void addBookView(BorderPane border) {
        TextField nameField = new TextField();
        nameField.setPromptText("Enter book name");
        TextField authorField = new TextField();
        authorField.setPromptText("Enter author name");
        TextField yearField = new TextField();
        yearField.setPromptText("Enter year of publication");
        TextField pagesField = new TextField();
        pagesField.setPromptText("Enter number of pages");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter description");

        Button addButton = new Button("Add Book");
        addButton.setOnAction(event -> {
            if (!nameField.getText().isEmpty() && !authorField.getText().isEmpty() &&
                    yearField.getText().matches("\\d+") && pagesField.getText().matches("\\d+")) {
                int year = Integer.parseInt(yearField.getText());
                int pages = Integer.parseInt(pagesField.getText());
                Book newBook = new Book(nameField.getText(), authorField.getText(), year, pages, descriptionArea.getText(), "filePath");
                bookListView.getItems().add(newBook);
                nameField.clear();
                authorField.clear();
                yearField.clear();
                pagesField.clear();
                descriptionArea.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields correctly.");
                alert.showAndWait();
            }
        });
        VBox addBookForm = new VBox(10, nameField, authorField, yearField, pagesField, descriptionArea, addButton);
        addBookForm.setAlignment(Pos.CENTER);
        border.setCenter(addBookForm);
    }


    private void showBookDetails(Book book, BorderPane border) {
        if (book != null) {
            Label bookName = new Label(book.getName());
            Label authorName = new Label(book.getAuthor());
            Label year = new Label("Year: " + book.getYear());
            Label pages = new Label("Number of Pages: " + book.getNumberOfPages());
            Label description = new Label(book.getDescription());
            Button goBackButton = new Button("Go Back");

            Button feedbackButton = new Button("Feedback");
            Image image = new Image("/img1.jpg"); // Assuming img1.jpg is in the resources folder
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            feedbackButton.setBackground(background);

            TextArea bookContentArea = new TextArea();
            bookContentArea.setText(book.getDescription()); // Or any other content you want to display
            bookContentArea.setEditable(false);
            bookContentArea.setWrapText(true);

            VBox bookDetails = new VBox(10, bookName, authorName, year, pages, description, goBackButton, feedbackButton, bookContentArea);
            bookDetails.setAlignment(Pos.CENTER);
            Button downloadButton = new Button("Download");
            downloadButton.setOnAction(event -> {
                try {
//                    getHostServices().showDocument(book.getFilePath("/Users/alinabondarieva/IdeaProjects/Bookstore/src/main/java/com/example/bookstore/books/1984.pdf"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            bookDetails.getChildren().addAll(downloadButton);

            feedbackButton.setOnAction(event -> {
                Stage feedbackStage = new Stage();
                TextArea feedbackArea = new TextArea();
                feedbackArea.setEditable(false);
                feedbackArea.setWrapText(true);
                feedbackManager.getLastThreeFeedbacks().forEach(feedback -> feedbackArea.appendText(feedback.toString() + "\n"));

                Button submitButton = new Button("Write my feedback");
                submitButton.setOnAction(e -> {
                    TextInputDialog feedbackDialog = new TextInputDialog();
                    feedbackDialog.setTitle("Writing my feedback <3");
                    feedbackDialog.setHeaderText(null);
                    feedbackDialog.setContentText("Enter your feedback");
                    Optional<String> result = feedbackDialog.showAndWait();
                    result.ifPresent(feedback -> {
                        feedbackManager.addFeedback(feedback);
                        feedbackArea.clear();
                        feedbackManager.getLastThreeFeedbacks().forEach(f -> feedbackArea.appendText(f.toString() + "\n"));
                    });
                });

                VBox feedbackForm = new VBox(10, feedbackArea, submitButton);
                feedbackForm.setAlignment(Pos.CENTER);
                Scene feedbackScene = new Scene(feedbackForm, 400, 300);
                feedbackStage.setScene(feedbackScene);
                feedbackStage.show();
            });

            goBackButton.setOnAction(event -> {
                if (!viewHistory.isEmpty()) {
                    Parent previousView = viewHistory.pop();
                    border.setCenter(previousView); // Ensure this is a valid operation
                }
            });

            viewHistory.push((Parent) border.getCenter());
            border.setCenter(bookDetails);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}