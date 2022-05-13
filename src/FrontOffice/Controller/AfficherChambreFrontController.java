/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontOffice.Controller;

import BackOffice.Services.ChambreService;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
public class AfficherChambreFrontController implements Initializable {

    ChambreService cs = new ChambreService();
    public static int iduser=1;

    @FXML
    private FlowPane FP;
    @FXML
    private TextField tfSearch;
    @FXML
    private JFXButton mesreservation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeChambres();
         tfSearch.textProperty().addListener((obj, old, ne) -> {
            if (ne != null) {
                initializechambreByTitle(ne);

            } else {
                initializeChambres();
            }
        });
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
                Parent commentFXML = FXMLLoader.load(getClass().getResource("/FrontOffice/View/ChambreFront.fxml"));
                ChambreFrontController.ChambreType2.setText(cs.displayAllFX().get(i).getType());
                ChambreFrontController.id2.setText(cs.displayAllFX().get(i).getId() + "");
                ChambreFrontController.chambreView2.setText(cs.displayAllFX().get(i).getVue());
                ChambreFrontController.chambrePrix2.setText(cs.displayAllFX().get(i).getPrix() + "");
                try {
                    String path = cs.displayAllFX().get(i).getImage();
                    if (path != null) {
                        InputStream stream = new FileInputStream(path);
                        Image image = new Image(stream);
                        ChambreFrontController.chambreImage2.setImage(image);
                    } else {
                        System.out.println("no image");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("no changes ");
                }
                ChambreFrontController.chambreTitre2.setText(cs.displayAllFX().get(i).getTitle());
                ChambreFrontController.ChambreQuantite2.setText(cs.displayAllFX().get(i).getQuantite() + "");

                ChambreFrontController.ChambreType2.setEditable(false);
                ChambreFrontController.id2.setVisible(false);
                ChambreFrontController.chambreTitre2.setEditable(false);
                ChambreFrontController.chambreView2.setEditable(false);
                ChambreFrontController.chambrePrix2.setEditable(false);

                FP.getChildren().add(commentFXML);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
     public void initializechambreByTitle(String s) {

        FP.getChildren().clear();

        int count = cs.SearchTitleDynamic(s).size();
        System.out.println(count);

        for (int i = 0; i < count; i++) {
            try {
                Parent commentFXML = FXMLLoader.load(getClass().getResource("/FrontOffice/View/ChambreFront.fxml"));
                ChambreFrontController.ChambreType2.setText(cs.SearchTitleDynamic(s).get(i).getType());
                ChambreFrontController.id2.setText(cs.SearchTitleDynamic(s).get(i).getId()+ "");
                ChambreFrontController.chambreView2.setText(cs.SearchTitleDynamic(s).get(i).getVue());
                ChambreFrontController.chambrePrix2.setText(cs.SearchTitleDynamic(s).get(i).getPrix()+"");
                 try {
                    String path = cs.SearchTitleDynamic(s).get(i).getImage();
                    if (path != null) {
                        InputStream stream = new FileInputStream(path);
                        Image image = new Image(stream);
                        ChambreFrontController.chambreImage2.setImage(image);
                    } else {
                        System.out.println("no image");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("no changes ");
                }
                ChambreFrontController.chambreTitre2.setText(cs.SearchTitleDynamic(s).get(i).getTitle());
                ChambreFrontController.ChambreQuantite2.setEditable(false);
                 ChambreFrontController.ChambreType2.setEditable(false);
                ChambreFrontController.id2.setVisible(false);
                ChambreFrontController.chambreTitre2.setEditable(false);
                ChambreFrontController.chambreView2.setEditable(false);
                ChambreFrontController.chambrePrix2.setEditable(false);


                FP.getChildren().add(commentFXML);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
     
    

    @FXML
    private void mesreservatios(MouseEvent event) {
          try {
            Parent parent = FXMLLoader.load(getClass().getResource("/FrontOffice/View/MesReservation.fxml"));
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
            Logger.getLogger(AfficherChambreFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
