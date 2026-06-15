package de.lubowiecki;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

// Das Interface Initializable bietet eine Methode die automatisch beim Start des Controllers aufgerufen wird
public class EinkaufslisteController implements Initializable {

    // TODO: Filter einsetzen
    // TODO: Action für Filter-Buttos festlegen
    // TODO: Stage-Größe an die Größe der Scene automatisch anpassen

    @FXML
    TextField input;

    @FXML
    ListView<Task> taskList;

    //private List<Task> alleTasks = new ArrayList<>();

    private Predicate<Task> filter;

    private TaskRepository repo;

    public void add(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            if(!input.getText().isEmpty()) {
                // Hinzufügen zu der Liste als Instanzvariable
                // alleTasks.add(new Task(input.getText()));
                try {
                    repo.save(new Task(input.getText()));
                    updateOutput();
                    clearFields();
                }
                catch(SQLException e) {
                    // TODO: Fehler in der GUI ausgeben
                    System.out.println(e.getMessage());
                }
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
            //alleTasks.remove(item); // Entfernen aus der Liste als Instanzvariable
            try {
                repo.delete(item);
                updateOutput();
            }
            catch(SQLException e) {
                // TODO: Fehler in der GUI ausgeben
                System.out.println(e.getMessage());
            }
        }
    }

    public void toggle() {
        Task item = taskList.getSelectionModel().getSelectedItem();
        if(item != null) {
            item.toggleOpen();
            try {
                repo.save(item);
                updateOutput();
            }
            catch(SQLException e) {
                // TODO: Fehler in der GUI ausgeben
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateOutput() {
        // Verwendet die List als Instanzvariable
        // ObservableList<Task> tasks = FXCollections.observableList(alleTasks);

        try {
            // Fragt die Daten aus der Datenbank ab
            ObservableList<Task> tasks = FXCollections.observableList(repo.findAll());

            if (filter == null) {
                taskList.setItems(tasks); // Alle Tasks anzeigen
            } else {
                taskList.setItems(new FilteredList<>(tasks, filter)); // Elemente gefiltert anzeigen
            }
        }
        catch(SQLException e) {
            // TODO: Fehler in der GUI ausgeben
            System.out.println(e.getMessage());
        }
    }

    private void clearFields() {
        input.clear();
    }


    public void switchToMain() throws IOException {
        App.setRoot("main-view");
        App.mainStage.setWidth(600);
        App.mainStage.setHeight(520);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            repo = new TaskRepository();
            updateOutput(); // Anzeige der Daten aus der DB
        }
        catch (SQLException e) {
            System.out.println("Fehler beim Verbinden mit der Datenbank!");
            e.printStackTrace();
        }
    }
}
