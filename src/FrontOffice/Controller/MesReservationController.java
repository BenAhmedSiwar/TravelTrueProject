/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontOffice.Controller;

import BackOffice.Services.ChambreService;
import BackOffice.Services.ReservationService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class MesReservationController implements Initializable {

    ChambreService cs = new ChambreService();
    ReservationService rs = ReservationService.getInstance();
    @FXML
    private FlowPane FP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeChambres();
    }

    @FXML
    private void refreshTable(MouseEvent event) {
        initializeChambres();
    }
     public void initializeChambres() {

        FP.getChildren().clear();

        int count = cs.displayByIdUser(AfficherChambreFrontController.iduser).size();
        System.out.println(count);

        for (int i = 0; i < count; i++) {
            try {
                Parent commentFXML = FXMLLoader.load(getClass().getResource("/FrontOffice/View/reservation.fxml"));
                ReservationController.id2.setText(rs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getId() + "");
                ReservationController.chambrePrix2.setText(cs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getPrix() + "");
                try {
                    String path = cs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getImage();
                    if (path != null) {
                        InputStream stream = new FileInputStream(path);
                        Image image = new Image(stream);
                        ReservationController.chambreImage2.setImage(image);
                    } else {
                        System.out.println("no image");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("no changes ");
                }
                ReservationController.chambreTitre2.setText(cs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getTitle());
                ReservationController.ChambreQuantite2.setText(rs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getQuantite() + "");
                ReservationController.datedebut2.setText(rs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getDatedebut());
                ReservationController.datefin2.setText(rs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getDatefin());
                Double prix = cs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getPrix();
                int quan = rs.displayByIdUser(AfficherChambreFrontController.iduser).get(i).getQuantite() ; 
                ReservationController.totale2.setText(prix*quan+"");

                ReservationController.id2.setVisible(false);
                ReservationController.chambreTitre2.setEditable(false);
                ReservationController.chambrePrix2.setEditable(false);

                FP.getChildren().add(commentFXML);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
