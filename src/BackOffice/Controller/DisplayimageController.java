/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author bouss
 */
public class DisplayimageController implements Initializable {

    public static String displayimage2;
   
    @FXML
    private ImageView displayimage;
    
    public static ImageView viewimage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewimage=displayimage;
        
        try {
            String path = displayimage2;
            if (path != null) {
                InputStream stream = new FileInputStream(path);
                Image image = new Image(stream);
               DisplayimageController.viewimage.setImage(image);
            } else {
                System.out.println("no image");
            }
        } catch (FileNotFoundException e) {
            System.out.println("no changes ");
        }

    }

}
