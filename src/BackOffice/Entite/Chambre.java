/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Entite;
import java.util.Objects;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author bouss
 */
public class Chambre {
    
    private SimpleIntegerProperty id ;
    private SimpleStringProperty type;
    private SimpleStringProperty vue;
    private SimpleDoubleProperty prix	;
    private SimpleStringProperty image;
    private SimpleStringProperty title;
    private SimpleIntegerProperty quantite;

    public Chambre() {
    }

    
    public Chambre(int id, String type, String vue, Double prix, String image, String title, int quantite) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.vue = new SimpleStringProperty(vue);
        this.prix = new SimpleDoubleProperty(prix);
        this.image = new SimpleStringProperty(image);
        this.title = new SimpleStringProperty(title);
        this.quantite = new SimpleIntegerProperty(quantite);
    }

    public Chambre(String type, String vue, Double prix, String image, String title, int quantite) {
        this.type = new SimpleStringProperty(type);
        this.vue = new SimpleStringProperty(vue);
        this.prix = new SimpleDoubleProperty(prix);
        this.image = new SimpleStringProperty(image);
        this.title = new SimpleStringProperty(title);
        this.quantite = new SimpleIntegerProperty(quantite);
    }

    
    
    
     
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public String getVue() {
        return vue.get();
    }

    public void setVue(String vue) {
        this.vue = new SimpleStringProperty(vue);
    }

    public Double getPrix() {
        return prix.get();
    }

    public void setPrix(Double prix) {
        this.prix = new SimpleDoubleProperty(prix);
    }

    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image = new SimpleStringProperty(image);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title =new SimpleStringProperty(title);
    }

    public int getQuantite() {
        return quantite.get();
    }

    public void setQuantite(int quantite) {
        this.quantite = new SimpleIntegerProperty(quantite);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chambre other = (Chambre) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Chambre{" + "id=" + id + ", type=" + type + ", vue=" + vue + ", prix=" + prix + ", image=" + image + ", title=" + title + ", quantite=" + quantite + '}';
    }
    
    
    
    
 

     
    
}
