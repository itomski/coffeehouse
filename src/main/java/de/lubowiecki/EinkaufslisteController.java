package de.lubowiecki;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EinkaufslisteController {

    // TODO: Filter einsetzen
    // TODO: Action für Filter-Buttos festlegen
    // TODO: Stage-Größe an die Größe der Scene automatisch anpassen

    @FXML
    TextField input;

    @FXML
    ListView<Task> taskList;

    private List<Task> alleTasks = new ArrayList<>();

    private Predicate<Task> filter;

    public void add(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            if(!input.getText().isEmpty()) {
                alleTasks.add(new Task(input.getText()));
                updateOutput();
            }
        }
    }

    public void filter(ActionEvent event) {

        String btnText = ((Button)event.getSource()).getText(); // Auslöser der Aktion
        switch(btnText) {
            case "offen":
                filter = t -> t.isOpen(); // Nur offene
                break;

            case "erledigt":
                filter = t -> !t.isOpen(); // Nur erledigte
                break;

            default:
                filter = null; // Alle
        }
        updateOutput();
    }

    public void checkKey(KeyEvent event) {
        switch(event.getCode()) {
            case BACK_SPACE: remove();
                break;
            case SPACE: toggle();
                break;
        }
    }

    public void remove() {
        Task item = taskList.getSelectionModel().getSelectedItem();
        if(item != null) {
            alleTasks.remove(item);
            updateOutput();
        }
    }

    public void toggle() {
        Task item = taskList.getSelectionModel().getSelectedItem();
        if(item != null) {
            item.toggleOpen();
            updateOutput();
        }
    }

    private void updateOutput() {
        ObservableList<Task> tasks = FXCollections.observableList(alleTasks);

        if(filter == null) {
            taskList.setItems(tasks); // Alle Tasks anzeigen
        }
        else {
            taskList.setItems(new FilteredList<>(tasks, filter)); // Elemente gefiltert anzeigen
        }
    }

    public void switchToMain() throws IOException {
        App.setRoot("main-view");
        App.mainStage.setWidth(600);
        App.mainStage.setHeight(520);
    }
}
