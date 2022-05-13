
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceFlight;

import Entities.Flight;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.input.MouseEvent;
import Services.FlightService;
import Services.Pdf;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;

/**
 * FXML Controller class
 *
 * @author nour
 */
public class AfficherflightController implements Initializable {

    
    @FXML
    private TableView<Flight> FlightTable;
    
    @FXML
    private TableColumn<Flight, String> villedepTF;
    @FXML
    private TableColumn<Flight, String> villearrTF;
    @FXML
    private TableColumn<Flight, Integer> prixTF;
    @FXML
    private TableColumn<Flight, String> numplaneTF;
     ObservableList list ;
   @FXML
    private TableColumn<Flight, Integer> idFlightTF;
    @FXML
    private TextField villedepTF1;
    @FXML
    private TextField villearrTF1;
    @FXML
    private TextField prixTF1;
    @FXML
    private TextField numplaneTF1;
    @FXML
    private TextField idFlightTF1;
    
    int index=-1;
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
        villedepTF.setCellValueFactory(new PropertyValueFactory<>("villedepart"));
        villearrTF.setCellValueFactory(new PropertyValueFactory<>("villearrive"));
        prixTF.setCellValueFactory(new PropertyValueFactory<>("prix"));
        numplaneTF.setCellValueFactory(new PropertyValueFactory<>("numplane"));
    }    


    @FXML
    private void modifier(ActionEvent event) {
         FlightService serv = new FlightService();
         Flight t = new Flight();
         if ((prixTF1.getText().length()!=0)&&(isNumeric(prixTF1.getText()) ) && numplaneTF.getText().length()!=0 ){
         t.setId_flight((Integer.parseInt(idFlightTF1.getText())));
         t.setVilledepart(villedepTF1.getText());
         t.setVillearrive(villearrTF1.getText());
         t.setPrix((Integer.parseInt(prixTF1.getText())));
         t. setNumplane(numplaneTF1.getText());
       try {
            serv.modifier(t);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("flight is update!");
            alert.show();
            } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        }else if ( (prixTF1.getText().isEmpty() ) || (prixTF1.getText().length()>4) || !(isNumeric(prixTF1.getText()) )  ){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Attention");
                    alert.setHeaderText("Echec");
                    alert.setContentText("Veillez enter un prix valide ! ");
                    alert.show();
                    prixTF1.requestFocus();   
             }else if ( (numplaneTF1.getText().isEmpty() ) || (numplaneTF1.getText().length()>8) || !(isNumeric(numplaneTF1.getText()) )  ){
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Echec");
                            alert.setContentText("Veillez enter numplane valide ");
                            alert.show();
                            numplaneTF1.requestFocus();   
             }
             
    }  
        

    @FXML
    private void Supprimer(ActionEvent event) {
          FlightService serv = new FlightService();
            int F=Integer.parseInt(idFlightTF1.getText());
                     serv.supprimer(F);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("flight is deleted!");
            alert.show();


    }
    
    public static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    private void refresh(ActionEvent event) {
         FlightService ps = new FlightService();
        List<Flight> personnes = ps.AfficherFlight();
        list = FXCollections.observableList(personnes);
        FlightTable.setItems(list);
        idFlightTF.setCellValueFactory(new PropertyValueFactory<>("id_flight"));
        villedepTF.setCellValueFactory(new PropertyValueFactory<>("villedepart"));
        villearrTF.setCellValueFactory(new PropertyValueFactory<>("villearrive"));
        prixTF.setCellValueFactory(new PropertyValueFactory<>("prix"));
        numplaneTF.setCellValueFactory(new PropertyValueFactory<>("numplane"));
    }
    
    @FXML
    void getSelected(MouseEvent event){
        index = FlightTable.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        idFlightTF1.setText(idFlightTF.getCellData(index).toString());
        villedepTF1.setText(villedepTF.getCellData(index));
        villearrTF1.setText(villearrTF.getCellData(index));
        numplaneTF1.setText(numplaneTF.getCellData(index));
//        prixTF1.setText(prixTF.getCellData(index).toString());
    }

    @FXML
    private void searchButton(ActionEvent event) {
    }


    @FXML
    private void gestionFlight(ActionEvent event) {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("AfficheFlight.fxml"));
            villedepTF1.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
     @FXML
    private void gestionChambre(ActionEvent event) {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("/FrontOffice/View/AfficherChambreFront.fxml"));
            villedepTF1.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void pdf(ActionEvent event) throws FileNotFoundException, SQLException, DocumentException {
        ObservableList<Flight> rs=FlightTable.getSelectionModel().getSelectedItems();
        Flight r=rs.get(0);
        Pdf p=new Pdf();
        System.out.println(p);
       p.add("Flight", String.valueOf(r.getVilledepart()), String.valueOf(r.getVillearrive()), String.valueOf(r.getPrix()));
    }
    
}
