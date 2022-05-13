/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceResFlight;

import InterfaceResFlight.*;
import static InterfaceFlight.AfficherflightController.isAlpha;
import static InterfaceFlight.AfficherflightController.isNumeric;
import Entities.Flight;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Services.FlightService;

/**
 * FXML Controller class
 *
 * @author nour
 */
public class AjoutFlightController implements Initializable {

    
    @FXML
    private TableView<Flight> FlightTable;
    @FXML
    private TableColumn<Flight, Integer> idFlightTF;
   @FXML
    private TableColumn<Flight, String> villedepTF1;
    @FXML
    private TableColumn<Flight, String> villearrTF1;
    @FXML
    private TableColumn<Flight, Integer> prixTF1;
    @FXML
    private TableColumn<Flight, String> numplaneTF1;

    ObservableList list ;
    @FXML
    private TextField villedepTF2;
    @FXML
    private TextField villearrTF2;
    @FXML
    private TextField prixTF2;
    @FXML
    private TextField numplaneTF2;
    @FXML
    private TextField idFlightTF2;
    @FXML
    private TextField search1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FlightService ps = new FlightService();
        List<Flight> personnes = ps.AfficherFlight();
        list = FXCollections.observableList(personnes);
        FlightTable.setItems(list);
        idFlightTF.setCellValueFactory(new PropertyValueFactory<>("id_flight"));
        villedepTF1.setCellValueFactory(new PropertyValueFactory<>("villedep"));
        villearrTF1.setCellValueFactory(new PropertyValueFactory<>("villearr"));
        prixTF1.setCellValueFactory(new PropertyValueFactory<>("prix"));
        numplaneTF1.setCellValueFactory(new PropertyValueFactory<>("numplane"));
    }    

    @FXML
    private void ajouter(ActionEvent event) {
        Flight p = new Flight();
         if ( (villedepTF2.getText().length()!=0 &&(isAlpha(villedepTF2.getText()) ) )&& ( (prixTF2.getText().length()!=0)&&(isNumeric(prixTF2.getText()) ) ) && numplaneTF2.getText().length()!=0  && villearrTF2.getText().length()!=0  ){
        p.setVilledepart(villedepTF2.getText());
        p.setVillearrive(villearrTF2.getText());
        p.setPrix(Integer.parseInt(prixTF2.getText()));  
        p.setNumplane(numplaneTF2.getText());
        FlightService ps = new FlightService();
        ps.ajoutFlight(p);
        
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Flight ajoutée");
        a.show(); 
   }else if(villedepTF2.getText().isEmpty() || !(isAlpha(villedepTF2.getText()) ) ){
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Attention");
                   alert.setHeaderText("Echec");
                   alert.setContentText("Veillez enter une ville valide!");
                   alert.show();
                  villedepTF2.requestFocus(); 
             }else if ( (prixTF2.getText().isEmpty() ) || (prixTF2.getText().length()>8) || !(isNumeric(prixTF2.getText()) )  ){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Attention");
                    alert.setHeaderText("Echec");
                    alert.setContentText("Veillez enter un prix valide ! ");
                    alert.show();
                    prixTF2.requestFocus();   
             }else if (numplaneTF2.getText().isEmpty()){
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Echec");
                            alert.setContentText("Veillez enter numplane ");
                            alert.show();
                            numplaneTF2.requestFocus();   
             } else if (villearrTF2.getText().isEmpty()){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Echec");
                            alert.setContentText("Veillez enter ville !");
                            alert.show();
                           villearrTF2.requestFocus();   
                   }
    }

    @FXML
    private void afficher(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("AfficherFlight.fxml"));
            villedepTF2.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    
    }

   
    @FXML
    private void searchButton(ActionEvent event) {
    }

    @FXML
    private void gestionFlight(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("AjoutFlight.fxml"));
            villedepTF2.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
}
