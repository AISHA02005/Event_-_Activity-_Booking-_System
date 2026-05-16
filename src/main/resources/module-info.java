module org.example {

    // ── JavaFX ────────────────────────────────────────────────────
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // ── Backend modules ───────────────────────────────────────────
    // Adjust 'requires' names to match whatever module-info your
    // backend declares (or use --add-modules / unnamed module path).
    requires com.bookingsystem;

    // ── Open packages to javafx.fxml for reflection ───────────────
    opens org.example to javafx.fxml, javafx.graphics;
    opens org.example.controllers to javafx.fxml;

    // ── Export the main packages ──────────────────────────────────
    exports org.example;
    exports org.example.controllers;
}
