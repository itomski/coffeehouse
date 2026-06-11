package de.lubowiecki;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;

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

    private StringBuilder content = new StringBuilder();

    @FXML
    public void leftClicked() {
        content.append(LocalDateTime.now() + ": Left! \n");
        txtOutput.setText(content.toString());
    }

}
