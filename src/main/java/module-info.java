module com.example.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    opens com.example.bookstore to javafx.fxml;
    exports com.example.bookstore;
}