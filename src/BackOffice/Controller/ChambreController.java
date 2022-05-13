/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Controller;

import BackOffice.Entite.Chambre;
import BackOffice.Services.ChambreService;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class ChambreController implements Initializable {
    ChambreService cs = ChambreService.getInstance();

    @FXML
    private Label id;
    public static Label id2;
    public static boolean refresh = false;
    @FXML
    private JFXTextField ChambreType;
    public static JFXTextField ChambreType2;
    @FXML
    private JFXTextField chambreView;
    public static JFXTextField chambreView2;
    @FXML
    private JFXTextField chambreTitre;
    public static JFXTextField chambreTitre2;
    @FXML
    private ImageView chambreImage;
    public static ImageView chambreImage2;
    @FXML
    private JFXTextField chambrePrix;
    public static JFXTextField chambrePrix2;
    @FXML
    private JFXTextField ChambreQuantite;
    public static JFXTextField ChambreQuantite2;
    @FXML
    private ImageView imageSubject;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       id2 = id; 
       ChambreQuantite2=ChambreQuantite;
       chambreImage2=chambreImage;
       ChambreType2=ChambreType;
       chambreView2= chambreView;
       chambrePrix2=chambrePrix;
       chambreTitre2=chambreTitre;
    }    
  
    @FXML
    private void delete(ActionEvent event) {
        Chambre chambre = new Chambre();
        chambre = cs.displayById(Integer.parseInt(id.getText()));
        
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Voulez-vous vraiment supprimer cette chambre ?");
            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                cs.delete(chambre);
            } else {
                alert2.close();
            }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/BackOffice/View/AfficherChambre.fxml"));
            id.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void displayimage(ScrollEvent event) {
         FXMLLoader loader = new FXMLLoader();

         Chambre chambre = new Chambre();
        chambre = cs.displayById(Integer.parseInt(id.getText()));
        DisplayimageController.displayimage2 = chambre.getImage();

        loader.setLocation(getClass().getResource("/BackOffice/View/displayimage.fxml"));
        try {
            loader.load();

        } catch (IOException ex) {
            Logger.getLogger(ChambreController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        new animatefx.animation.ZoomIn(parent).play();

    }
    
    
     public static void setrefresh(boolean t) {
        refresh = t;

    }

    @FXML
    private void modifierChambre(MouseEvent event) {
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BackOffice/View/modifierChambre.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ChambreController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Chambre chambre = new Chambre();
            chambre = cs.displayById(Integer.parseInt(id.getText()));

            ModifierChambreController modifierchambrecontroller = loader.getController();
            modifierchambrecontroller.setTextField(chambre);
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            stage.setOnHiding(event2 -> {
                try {
                    Parent parent2 = FXMLLoader.load(getClass().getResource("/BackOffice/View/AfficherChambre.fxml"));
                    Stage stage3 = (Stage) id.getScene().getWindow();
                    stage3.close();
                    Scene scene = new Scene(parent2);
                    Stage stage2 = new Stage();
                    stage2.setScene(scene);
                    stage2.initStyle(StageStyle.UTILITY);
                    stage2.show();

                } catch (IOException ex) {
                    Logger.getLogger(ChambreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });      
    }    
}
