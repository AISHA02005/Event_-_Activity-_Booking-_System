module event.booking.system {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;   // اختياري لكن مفيد

    // Open packages for FXML (مهم جداً)
    opens com.bookingsystem to javafx.fxml, javafx.graphics;

    opens com.bookingsystem.auth.controller to javafx.fxml;
    opens com.bookingsystem.event.controller to javafx.fxml;
    opens com.bookingsystem.booking.controller to javafx.fxml;
    opens com.bookingsystem.payment.controller to javafx.fxml;
    opens com.bookingsystem.notification.controller to javafx.fxml;
    opens com.bookingsystem.review.controller to javafx.fxml;
    opens com.bookingsystem.search.controller to javafx.fxml;  // لو عملتي controller
    opens com.bookingsystem.admin.controller to javafx.fxml;
    opens com.bookingsystem.facade to javafx.fxml;
}