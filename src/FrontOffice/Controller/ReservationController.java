/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontOffice.Controller;

import BackOffice.Controller.DisplayimageController;
import BackOffice.Entite.Chambre;
import BackOffice.Entite.Reservation;
import BackOffice.Services.ChambreService;
import BackOffice.Services.ReservationService;
import BackOffice.Utils.Mailling;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class ReservationController implements Initializable {

    ChambreService cs = ChambreService.getInstance();
   
    ReservationService rs = ReservationService.getInstance();

    @FXML
    private Label id;
    public static Label id2;
    public static boolean refresh = false;
    @FXML
    private JFXTextField chambreTitre;
    public static JFXTextField chambreTitre2;
    @FXML
    private JFXTextField chambrePrix;
    public static JFXTextField chambrePrix2;
    @FXML
    private JFXTextField ChambreQuantite;
    public static JFXTextField ChambreQuantite2;
    @FXML
    private JFXTextField datedebut;
    public static JFXTextField datedebut2;
    @FXML
    private JFXTextField datefin;
    public static JFXTextField datefin2;
    @FXML
    private ImageView chambreImage;
    public static ImageView chambreImage2;
    @FXML
    private Label totale;
    public static Label totale2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id2 = id;
        ChambreQuantite2 = ChambreQuantite;
        chambreImage2 = chambreImage;
        chambrePrix2 = chambrePrix;
        chambreTitre2 = chambreTitre;
        datedebut2 = datedebut;
        datefin2 = datefin;
        totale2=totale;
    }

    @FXML
    private void delete(ActionEvent event) {
        Reservation reservation = new Reservation();
        reservation = rs.displayById(Integer.parseInt(id.getText()));
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Confirmation");
        alert2.setHeaderText("Voulez-vous vraiment supprimer cette reservation ?");
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
            rs.delete(reservation);
            Mailling.envoyer();
        } else {
            alert2.close();
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FrontOffice/View/MesReservation.fxml"));
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
            Logger.getLogger(ChambreFrontController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        new animatefx.animation.ZoomIn(parent).play();
    }

}
