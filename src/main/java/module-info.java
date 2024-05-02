module com.example.bookstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bookstore to javafx.fxml;
    exports com.example.bookstore;
}