/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontOffice.Controller;

import BackOffice.Controller.DisplayimageController;
import BackOffice.Entite.Chambre;
import BackOffice.Services.ChambreService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class ChambreFrontController implements Initializable {

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
    private JFXTextField chambrePrix;
    public static JFXTextField chambrePrix2;
    @FXML
    private JFXTextField ChambreQuantite;
    public static JFXTextField ChambreQuantite2;
    @FXML
    private ImageView chambreImage;
    public static ImageView chambreImage2;
    @FXML
    private ImageView imageSubject;
    @FXML
    private Label labemnblike;
    @FXML
    private Label labelnbdislike;
    @FXML
    private JFXButton reservationbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id2 = id;
        ChambreQuantite2 = ChambreQuantite;
        chambreImage2 = chambreImage;
        ChambreType2 = ChambreType;
        chambreView2 = chambreView;
        chambrePrix2 = chambrePrix;
        chambreTitre2 = chambreTitre;
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

    @FXML
    private void like(MouseEvent event) {
        Chambre chambre = new Chambre();
        chambre = cs.displayById(Integer.parseInt(id.getText()));
        labemnblike.setText(Integer.toString(cs.getnblike(chambre.getId())));
        labelnbdislike.setText(Integer.toString(cs.getnbdislike(chambre.getId())));
        int check = cs.checklikeuser(AfficherChambreFrontController.iduser, chambre.getId());
        if (check == 0) {
            if (cs.checkdislikeuser(AfficherChambreFrontController.iduser, chambre.getId()) == 0) {
                cs.addlike(AfficherChambreFrontController.iduser, chambre.getId());
                int nblike = cs.getnblike(chambre.getId());
                nblike++;
                cs.setlike(chambre.getId(), nblike);
            } else {
                cs.addlike(AfficherChambreFrontController.iduser, chambre.getId());
                int nblike = cs.getnblike(chambre.getId());
                nblike++;
                cs.setlike(chambre.getId(), nblike);
                int nbdislike = cs.getnbdislike(chambre.getId());
                nbdislike--;
                cs.setdislike(chambre.getId(), nbdislike);
                //**********
                cs.deletedislike(cs.checkdislikeuser(AfficherChambreFrontController.iduser, chambre.getId()));
                System.out.println(chambre.getId());

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Like");
            alert.setHeaderText("Vous avez déjà aimé cette chambre! ");
            alert.setContentText(" Done!");
            alert.show();
        }

        labemnblike.setText(Integer.toString(cs.getnblike(chambre.getId())));
        labelnbdislike.setText(Integer.toString(cs.getnbdislike(chambre.getId())));
    }

    @FXML
    private void dislike(MouseEvent event) {
        Chambre chambre = new Chambre();
        chambre = cs.displayById(Integer.parseInt(id.getText()));
        labemnblike.setText(Integer.toString(cs.getnblike(chambre.getId())));
        labelnbdislike.setText(Integer.toString(cs.getnbdislike(chambre.getId())));
        if (cs.checkdislikeuser(AfficherChambreFrontController.iduser, chambre.getId()) == 0) {
            if (cs.checklikeuser(AfficherChambreFrontController.iduser, chambre.getId()) == 0) {
                cs.adddislike(AfficherChambreFrontController.iduser, chambre.getId());
                int nbdislike = cs.getnbdislike(chambre.getId());
                nbdislike++;
                cs.setdislike(chambre.getId(), nbdislike);

            } else {
                cs.adddislike(AfficherChambreFrontController.iduser, chambre.getId());
                int nbdislike = cs.getnbdislike(chambre.getId());
                nbdislike++;
                cs.setdislike(chambre.getId(), nbdislike);
                int nblike = cs.getnblike(chambre.getId());
                nblike--;
                cs.setlike(chambre.getId(), nblike);
                //***********
                cs.deletelike(cs.checklikeuser(AfficherChambreFrontController.iduser, chambre.getId()));

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Like");
            alert.setHeaderText("Vous n'avez déjà pas aimé cette chambre !");
            alert.setContentText(" Done!");
            alert.show();
        }
        labemnblike.setText(Integer.toString(cs.getnblike(chambre.getId())));
        labelnbdislike.setText(Integer.toString(cs.getnbdislike(chambre.getId())));
    }

    @FXML
    private void reservation(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FrontOffice/View/reserver.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ChambreFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
            ReserverController reservercontroller = loader.getController();
            reservercontroller.idchambre=Integer.parseInt(id.getText()); 
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            stage.setOnHiding(event2 -> {
                try {
                    Parent parent2 = FXMLLoader.load(getClass().getResource("/FrontOffice/View/AfficherChambreFront.fxml"));
                    Stage stage3 = (Stage) id.getScene().getWindow();
                    stage3.close();
                    Scene scene = new Scene(parent2);
                    Stage stage2 = new Stage();
                    stage2.setScene(scene);
                    stage2.initStyle(StageStyle.UTILITY);
                    stage2.show();

                } catch (IOException ex) {
                    Logger.getLogger(ChambreFrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });  
    }

}
