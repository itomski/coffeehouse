package de.lubowiecki;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    /*
    Verbindet den Button btnRight so, dass es auch in das TextArea schreibt.

    Definiert einen neuen Button zum leeren des TextAreas.
    */

    // Name der Variable MUSS identisch mit der ID des Elementes im FXML sein

    @FXML // Variable ist für das FXML zugänglich
    Button btnLeft;

    @FXML
    Button btnRight;

    @FXML
    TextArea txtOutput;

    @FXML
    TextField vorname;

    @FXML
    TextField nachname;

    @FXML
    DatePicker geburtsDatum;

    @FXML
    Label errorOutput;

    @FXML
    ListView<Person> customerList;

    private StringBuilder content = new StringBuilder();

    private List<Person> persons = new ArrayList<>();

    @FXML
    public void leftClicked() {
        content.append(LocalDateTime.now()).append(": Left! \n");
        txtOutput.setText(content.toString());
    }

    @FXML
    public void rightClicked() {
        content.append(LocalDateTime.now()).append(": Right! \n");
        txtOutput.setText(content.toString());
    }

    @FXML
    public void clearOutput() {
        content = new StringBuilder();
//        content.setLength(0);
//        content.trimToSize();
//        System.out.println(content.length());
//        System.out.println(content.capacity());
        txtOutput.setText(content.toString());
    }

    public void savePerson() {

        if(!validateForm()) {
            return; // Wenn Error, dann die Methode verlassen
        }

        Person p = new Person(vorname.getText(), nachname.getText(), geburtsDatum.getValue());
        persons.add(p); // Person ion der Liste ablegen
        //txtOutput.setText(persons.toString()); // Ausgabe in das TextArea

        // Normale ArrayList muss für die ListView in eine ObservableList konvertiert werden
        customerList.setItems(FXCollections.observableList(persons)); // Inhalt von persons in die ListView ausgeben

        clearForm();
    }

    private boolean validateForm() {
        final StringBuilder errors = new StringBuilder();

        if(vorname.getText().isEmpty() || vorname.getText().length() < 2) {
            errors.append("Vorname ist nicht gültig!\n");
        }
        if(nachname.getText().isEmpty() || nachname.getText().length() < 2) {
            errors.append("Nachname ist nicht gültig!\n");
        }

        if(geburtsDatum.getValue() == null || !geburtsDatum.getValue().isBefore(LocalDate.now())) { // Überprüfen
            errors.append("Datum ist nicht gültig!\n");
        }

        errorOutput.setText(errors.toString());
        return errors.length() == 0;
    }

    private void clearForm() {
        vorname.clear();
        nachname.clear();
        geburtsDatum.setValue(LocalDate.now());
        geburtsDatum.getEditor().clear();
    }

    public void switchToEinkaufsliste() throws IOException {
        App.setRoot("einkaufsliste-view");
        App.mainStage.setWidth(300);
        App.mainStage.setHeight(660);
    }
}
