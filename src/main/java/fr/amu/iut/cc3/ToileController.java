package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @FXML
    private Pane radar;
    @FXML
    private TextField comp1;
    @FXML
    private TextField comp2;
    @FXML
    private TextField comp3;
    @FXML
    private TextField comp4;
    @FXML
    private TextField comp5;
    @FXML
    private TextField comp6;
    @FXML
    private Button tracer;
    @FXML
    private Button vider;

    @FXML
    private Label messageErreur;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    public ArrayList<TextField> listeNotes(){
        ArrayList<TextField> notes= new ArrayList<>();
        notes.add(comp1);
        notes.add(comp2);
        notes.add(comp3);
        notes.add(comp4);
        notes.add(comp5);
        notes.add(comp6);
        return notes;
    }
    @FXML
    public void tracerPoints(){
        createBindings();
        
        for (int i = 0; i < 6; ++i) {
            Circle point = new Circle();
            point.setLayoutX(getXRadarChart(Double.valueOf(listeNotes().get(i).getText()), i+1));
            point.setLayoutY(getYRadarChart(Double.valueOf(listeNotes().get(i).getText()), i+1));
            point.setRadius(5);
            radar.getChildren().add(point);
        }
    }

    private void createBindings(){
        
        
        for (TextField note : listeNotes()) {
            BooleanBinding containsErrorBinding = new BooleanBinding() {
                {
                    super.bind(note.textProperty());
                }

                @Override
                protected boolean computeValue() {
                    return Integer.valueOf(note.getText()) > 20;
                }
            };

            messageErreur.styleProperty().bind(Bindings.when(containsErrorBinding)
                    .then(Bindings.concat("-fx-text-fill:","#FF0000"))
                    .otherwise(Bindings.concat("-fx-text-fill","FFFFFF"))
            );
        }
    }

    @FXML
    private void viderGraphe(){
        comp1.setText("0");
        comp2.setText("0");
        comp3.setText("0");
        comp4.setText("0");
        comp5.setText("0");
        comp6.setText("0");
    }

}
