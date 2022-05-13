/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Utils;

import java.io.File;

/**
 *
 * @author bouss
 */
public abstract class ImageIO {

    public static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/images/";

    public static void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

}
