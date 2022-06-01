package com.geekbrain.cloud.cloudapplication;


import com.geekbrain.cloud.cloudapplication.dto.AuthRequest;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


// В контролее управляем элементами UI интерфейса
public class ChatController implements Initializable {

    private Client client;

    // таблицы
    public TableView<FileInfo> leftFilesTable;
    public TableView<FileInfo> rightFilesTable;

    // диски
    public ComboBox<String> leftDisksBox;
    public ComboBox<String> rightDisksBox;

    // пути
    public TextField leftPathField;
    public TextField rightPathField;

    // поля логина и пароля
    public TextField loginField;
    public TextField passwordField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         client = new Client();

        // клиентская колонка (левая)
        // тип файла
        TableColumn<FileInfo, String> leftFileTypeColumn = new TableColumn<FileInfo, String>("Type");
        leftFileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        leftFileTypeColumn.setPrefWidth(60);

        // имя файла
        TableColumn<FileInfo, String> leftFileNameColumn = new TableColumn<FileInfo, String>("Name");
        leftFileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
        leftFileNameColumn.setPrefWidth(240);

        // размер файла
        TableColumn<FileInfo, Long> leftFileSizeColumn = new TableColumn<>("Size");
        leftFileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getSize()));
        leftFileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileInfo, Long>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        String text = String.format("%,d bytes", item);
                        if (item == -1L) {
                            text = "";
                        }
                        setText(text);
                    }
                }
            };
        });
        leftFileSizeColumn.setPrefWidth(120);

        // время изменения
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo, String> leftFileDateModifiedColumn = new TableColumn<>("Modification time");
        leftFileDateModifiedColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModifiedTime().format(dtf)));
        leftFileDateModifiedColumn.setPrefWidth(120);

        // сортировка
        leftFilesTable.getSortOrder().add(leftFileTypeColumn);

        // добавление а таблицу
        leftFilesTable.getColumns().addAll(leftFileTypeColumn, leftFileNameColumn, leftFileSizeColumn, leftFileDateModifiedColumn);


        // левый диск
        // очищаем поле
        leftDisksBox.getItems().clear();
        for (Path p : FileSystems.getDefault().getRootDirectories()) {
            leftDisksBox.getItems().add(p.toString());
        }
        leftDisksBox.getSelectionModel().select(0);

        // добавление на клик мышкой
        leftFilesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    Path path = Paths.get(leftPathField.getText()).resolve(leftFilesTable.getSelectionModel().getSelectedItem().getFileName());
                    if (Files.isDirectory(path)) {
                        updateLeftList(path);
                    }
                }
            }
        });


        updateLeftList(Paths.get("."));
    }

    public void updateLeftList(Path path) {
        // ФАЙЛЫ
        leftFilesTable.getItems().clear();
        try {
            leftPathField.setText(path.normalize().toAbsolutePath().toString());
            leftFilesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            leftFilesTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to update file list", ButtonType.OK);
            alert.showAndWait();
        }
    }


    public void leftSelectDiskAction(ActionEvent actionEvent) {
        ComboBox<String> element = (ComboBox<String>) actionEvent.getSource();
        updateLeftList(Paths.get(element.getSelectionModel().getSelectedItem()));
    }

    public void leftButtonUpAction(ActionEvent actionEvent) {
        Path upperPath = Paths.get(leftPathField.getText()).getParent();
        if (upperPath != null) {
            updateLeftList(upperPath);
        }
    }

    public String leftGetSelectedFileName() {
        if (!leftFilesTable.isFocused()) {
            return null;
        }
        return leftFilesTable.getSelectionModel().getSelectedItem().getFileName();
    }

     public String leftGetCurrentPath() {
        return leftPathField.getText();
     }


    public void connectToServerAction(ActionEvent actionEvent) {
        AuthRequest authRequest = new AuthRequest(loginField.getText(), passwordField.getText());
        loginField.clear();
        passwordField.clear();
        client.sendAuthRequestMessage(authRequest);



    }
}
