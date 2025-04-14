module demo1 {
    //requires com.itextpdf.kernel;
    //requires com.itextpdf.layout;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires kernel;
    requires layout;
    requires io;

    //requires  ;
    //requires com.itextpdf.layout;

    opens com.example.controleurs to javafx.fxml;
    exports com.example.controleurs;
}

