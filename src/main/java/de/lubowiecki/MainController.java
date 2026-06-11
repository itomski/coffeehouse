package de.lubowiecki;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        System.out.println(content.length());
        System.out.println(content.capacity());
        txtOutput.setText(content.toString());
    }

    public void savePerson() {
        Person p = new Person(vorname.getText(), nachname.getText(), geburtsDatum.getValue());
        persons.add(p); // Person ion der Liste ablegen
        txtOutput.setText(persons.toString());

        // Als String
        //txtOutput.setText(vorname.getText() + ", " + nachname.getText() + ", " + geburtsDatum.getValue());
    }
}
