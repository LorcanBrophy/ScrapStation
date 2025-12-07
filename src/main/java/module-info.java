module com.example.scrapstation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scrapstation to javafx.fxml;
    exports com.example.scrapstation;
}