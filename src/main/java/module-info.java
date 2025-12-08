module com.example.scrapstation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.scrapstation to javafx.fxml;
    exports com.example.scrapstation;
    exports com.example.scrapstation.entities;
    opens com.example.scrapstation.entities to javafx.fxml;
}