/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontOffice.Controller;

import BackOffice.Entite.Reservation;
import BackOffice.Services.ChambreService;
import BackOffice.Services.ReservationService;
import static BackOffice.Utils.SmsTwillio.sms;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class ReserverController implements Initializable {
    public static int idchambre;
    
    @FXML
    private DatePicker datedebut;
    @FXML
    private DatePicker datefin;
    @FXML
    private JFXButton addbtn;
    @FXML
    private JFXTextField quantitRes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    

    @FXML
    private void save(MouseEvent event) {
       
        
         Date date = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
          if (quantitRes.getText().isEmpty() || datedebut.getValue().toString().isEmpty() || datefin.getValue().toString().isEmpty()){ 
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir toutes les données ! ");
            alert.showAndWait();
        }else{
               String dated = datedebut.getValue().toString();
        String datef = datefin.getValue().toString();
              
              if ((dated.compareTo(dateFormat.format(date))) > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("veuillez vérifier la date de début");
            alert.showAndWait();
            
            
              
        }else if((datef.compareTo(dated) <0)){
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("veuillez vérifier la date de fin");
            alert.showAndWait();
        }else {
            
               int quantiteres =  Integer.parseInt(quantitRes.getText());
               
               Reservation res = new Reservation();
               res.setDatedebut(dated);
               res.setDatefin(datef);
               res.setQuantite(quantiteres);
               res.setChambre_id(idchambre);
               res.setUtilisateur_id(AfficherChambreFrontController.iduser);
               ReservationService rs = new ReservationService();
               ChambreService cs = new ChambreService();
               if (rs.insert(res)) {
                    sms();
                    int quant = cs.getquantite(idchambre);
                    quant = quant-quantiteres;
                    cs.setquantite(idchambre, quant);
                    Stage stage = (Stage) addbtn.getScene().getWindow();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("reservation Ajouter ! ");
                    alert.showAndWait();
                    stage.close();
                    
                } else {
                    System.err.println("Error !!!");
                }
        }
              
          }
        
    }
    
}
