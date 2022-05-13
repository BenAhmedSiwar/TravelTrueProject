/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Controller;

import BackOffice.Entite.Chambre;
import BackOffice.Services.ChambreService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class AfficherChambreController implements Initializable {

    ChambreService cs = new ChambreService();
    public static int iduser;

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
    private void AjouterChambre(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/BackOffice/View/AjoutChambre.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            stage.setOnHiding(event2 -> {
                //  topictable.setItems(ts.displayAllFX());
                initializeChambres();
            });

        } catch (IOException ex) {
            Logger.getLogger(AfficherChambreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshTable(MouseEvent event) {
        initializeChambres();
    }

    public void initializeChambres() {

        FP.getChildren().clear();

        int count = cs.displayAllFX().size();
        System.out.println(count);

        for (int i = 0; i < count; i++) {
            try {
                Parent commentFXML = FXMLLoader.load(getClass().getResource("/BackOffice/View/Chambre.fxml"));
                ChambreController.ChambreType2.setText(cs.displayAllFX().get(i).getType());
                ChambreController.id2.setText(cs.displayAllFX().get(i).getId() + "");
                ChambreController.chambreView2.setText(cs.displayAllFX().get(i).getVue());
                ChambreController.chambrePrix2.setText(cs.displayAllFX().get(i).getPrix() + "");
                try {
                    String path = cs.displayAllFX().get(i).getImage();
                    if (path != null) {
                        InputStream stream = new FileInputStream(path);
                        Image image = new Image(stream);
                        ChambreController.chambreImage2.setImage(image);
                    } else {
                        System.out.println("no image");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("no changes ");
                }
                ChambreController.chambreTitre2.setText(cs.displayAllFX().get(i).getTitle());
                ChambreController.ChambreQuantite2.setText(cs.displayAllFX().get(i).getQuantite() + "");

                ChambreController.ChambreType2.setEditable(false);
                ChambreController.id2.setVisible(false);
                ChambreController.chambreTitre2.setEditable(false);
                ChambreController.chambreView2.setEditable(false);
                ChambreController.chambrePrix2.setEditable(false);

                FP.getChildren().add(commentFXML);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
