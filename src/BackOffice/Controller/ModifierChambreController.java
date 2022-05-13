/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Controller;

import BackOffice.Entite.Chambre;
import BackOffice.Services.ChambreService;
import static BackOffice.Utils.ImageIO.makeDirectoryIfNotExist;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class ModifierChambreController implements Initializable {
  private int idchambre;
    @FXML
    private JFXTextField inputType;
    @FXML
    private TextArea inputView;
    @FXML
    private TextArea inputPrix;
    @FXML
    private TextArea inputTitle;
    @FXML
    private TextArea inputQuantite;
    @FXML
    private TextField tfimage;
    @FXML
    private Button modifierimage;
    @FXML
    private JFXButton modifierbtn;
    
      void setTextField(Chambre c) {
        idchambre = c.getId();
        inputType.setText(c.getType());
        inputView.setText(c.getVue());
        inputPrix.setText(c.getPrix()+"");
        inputQuantite.setText(c.getQuantite()+"");
        tfimage.setText(c.getImage());
        inputTitle.setText(c.getTitle());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InsertImage(modifierimage, IMAGE_DIRECTORY, inputTitle, tfimage);
    }    

    @FXML
    private void save(MouseEvent event) {
         String title = inputTitle.getText();
        String type = inputType.getText();
        String image = tfimage.getText();
        String view = inputView.getText();
        
        if (title.isEmpty() || type.isEmpty() || view.isEmpty() || type.isEmpty() || inputPrix.getText().isEmpty() || inputQuantite.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir toutes les données ! ");
            alert.showAndWait();

        } else {
 
            Double prix = Double.parseDouble(inputPrix.getText());
            int quantite =  Integer.parseInt(inputQuantite.getText());
      
            Chambre c = new Chambre();
            c.setType(type);
            c.setVue(view);
            c.setPrix(prix);
            c.setImage(image);
            c.setTitle(title);
            c.setQuantite(quantite);
            c.setId(idchambre);
            ChambreService cs = new ChambreService();
            if (cs.update(c)) {
                Stage stage = (Stage) modifierbtn.getScene().getWindow();
                stage.close();

                Notifications notificationbuilder;
                notificationbuilder = Notifications.create()
                        .title("Alert").text("Chambre modifier ! ").graphic(null).hideAfter(javafx.util.Duration.seconds(5)).position(Pos.TOP_CENTER)
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("clicked on");
                            }
                        });
                notificationbuilder.darkStyle();
                notificationbuilder.showInformation();

            } else {
                System.err.println("Error !!!");
            }
        }
    }

    @FXML
    private void clean(MouseEvent event) {
        inputPrix.clear();
        inputQuantite.clear();
        inputType.clear();
        inputTitle.clear();
        inputView.clear();
        tfimage.clear();
    }
      private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/images/";

    public static void InsertImage(Button btnImageInsert, String imageDirectory, TextArea tfAagencyPhoneNumber, TextField tfImageAgency) {
        btnImageInsert.setOnAction(event -> {
            makeDirectoryIfNotExist(imageDirectory);
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            fileChooser.getExtensionFilters().addAll(extensionFilterJPG);
            File file = fileChooser.showOpenDialog(null);
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileName = tfAagencyPhoneNumber.getText() + "_" + file.getName();
            Path fileNamePath = Paths.get(imageDirectory, fileName);
            tfImageAgency.setText(fileNamePath.toString());
            System.out.println(tfImageAgency.getText());
            try {
                ImageIO.write(Objects.requireNonNull(bufferedImage), "JPG", new File(String.valueOf(fileNamePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
}
