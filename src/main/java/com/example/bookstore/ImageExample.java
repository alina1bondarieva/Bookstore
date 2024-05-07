package com.example.bookstore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.Stack;

public class ImageExample extends Application {
    private final FeedbackManager feedbackManager = new FeedbackManager();
    private final Stack<Parent> viewHistory = new Stack<>();
    private ListView<Book> bookListView = new ListView<>();
    private ListView<Book> myList = new ListView<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //Creating an image
        Image image = new Image(new FileInputStream("src/main/resources/img5.jpg"));

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(455);
        imageView.setFitWidth(500);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //Creating a Group object
        Group root = new Group(imageView);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 500);

        //Setting title to the Stage
        stage.setTitle("Loading an image");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
        try {
            stage.setTitle("My Bookstore <3");

            BorderPane border = new BorderPane();
            border.setStyle("-fx-background-color: #FFC5C5;"); // light pink

            // Define buttons
            Button homeButton = new Button("Home");
            Button searchButton = new Button("Search");
            Button myListButton = new Button("My List");
            Button addBookButton = new Button("Add Book");
            Button returnButton = new Button("Return");

            // Attach button events
            homeButton.setOnAction(e -> border.setCenter(bookListView));
            searchButton.setOnAction(e -> searchView(border));
            myListButton.setOnAction(e -> {
                viewHistory.push((Parent) border.getCenter());
                border.setCenter(myList);
            });
            addBookButton.setOnAction(e -> addBookView(border));
            returnButton.setOnAction(e -> {
                if (!viewHistory.isEmpty()) {
                    border.setCenter(viewHistory.pop());
                }
            });
            homeButton.setOnAction(e -> {
                viewHistory.push((Parent) border.getCenter());
                border.setCenter(bookListView);
            });

            VBox navigation = new VBox(10, homeButton, searchButton, myListButton, addBookButton, returnButton);
            navigation.setAlignment(Pos.CENTER);
            border.setBottom(navigation);

            TextArea contentArea = new TextArea("Select an option from the bottom.");
            contentArea.setEditable(false);

            bookListView.getItems().addAll(BookData.getBooks());
            myList.setCellFactory(param -> new ListCell<Book>() {
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

            myList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    showBookDetails(newValue, border));

            bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    showBookDetails(newValue, border));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private Button createButton(String buttonText) {
        Button button = new Button(buttonText);
        return button;
    }

    private void setView(BorderPane border, Node... nodes) {
        viewHistory.push((Parent) border.getCenter());
        VBox view = new VBox(10);
        view.getChildren().addAll(nodes);
        view.setAlignment(Pos.CENTER);
        border.setCenter(view);
    }

    private void searchView(BorderPane border) {
        TextField searchField = createTextField("Enter book name or author");
        ListView<Book> searchResults = new ListView<>();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchResults.getItems().clear();
            bookListView.getItems().stream()
                    .filter(book -> book.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(newValue.toLowerCase()))
                    .forEach(searchResults.getItems()::add);
        });
        searchResults.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                showBookDetails(newValue, border));

        setView(border, searchField, searchResults);
    }

    private void addBookView(BorderPane border) {
        TextField nameField = createTextField("Enter book name");
        TextField authorField = createTextField("Enter author name");
        TextField yearField = createTextField("Enter year of publication");
        TextField pagesField = createTextField("Enter number of pages");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter description");

        Button addButton = createButton("Add Book");
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

        setView(border, nameField, authorField, yearField, pagesField, descriptionArea, addButton);
    }

    private void showBookDetails(Book book, BorderPane border) {
        if (book != null) {
            VBox bookDetails = new VBox(10);

            Label nameLabel = new Label("Name: " + book.getName());
            Label authorLabel = new Label("Author: " + book.getAuthor());
            Label yearLabel = new Label("Year: " + book.getYear());
            Label pagesLabel = new Label("Pages: " + book.getNumberOfPages());
            TextArea descriptionArea = new TextArea(book.getDescription());
            descriptionArea.setEditable(false);

            Button addToMyListButton = new Button("Add to my list");
            addToMyListButton.setOnAction(e -> myList.getItems().add(book));

            bookDetails.getChildren().addAll(nameLabel, authorLabel, yearLabel, pagesLabel, descriptionArea, addToMyListButton);
            border.setCenter(bookDetails);
        } else {
            border.setCenter(new Label("No book selected"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
