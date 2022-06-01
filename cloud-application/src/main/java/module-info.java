module com.geekbrain.cloud.cloudapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.transport;
    requires io.netty.codec;


    opens com.geekbrain.cloud.cloudapplication to javafx.fxml;
    exports com.geekbrain.cloud.cloudapplication;
}