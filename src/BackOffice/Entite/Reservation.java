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
public class Reservation {
    private SimpleIntegerProperty id ;
    private SimpleIntegerProperty chambre_id ;
    private SimpleIntegerProperty utilisateur_id ;
    private SimpleIntegerProperty quantite	;
    private SimpleStringProperty datedebut;
    private SimpleStringProperty datefin;

    public Reservation() {
    }

    public Reservation(int id, int chambre_id, int utilisateur_id, int quantite, String datedebut, String datefin) {
        this.id = new SimpleIntegerProperty(id);
        this.chambre_id = new SimpleIntegerProperty(chambre_id);
        this.utilisateur_id = new SimpleIntegerProperty(utilisateur_id);
        this.quantite = new SimpleIntegerProperty(quantite);
        this.datedebut = new SimpleStringProperty(datedebut);
        this.datefin = new SimpleStringProperty(datefin);
    }

    public Reservation(int chambre_id, int utilisateur_id, int quantite, String datedebut, String datefin) {
        this.chambre_id = new SimpleIntegerProperty(chambre_id);
        this.utilisateur_id = new SimpleIntegerProperty(utilisateur_id);
        this.quantite = new SimpleIntegerProperty(quantite);
        this.datedebut = new SimpleStringProperty(datedebut);
        this.datefin = new SimpleStringProperty(datefin);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id =  new SimpleIntegerProperty(id);
    }

    public int getChambre_id() {
        return chambre_id.get();
    }

    public void setChambre_id(int chambre_id) {
        this.chambre_id =  new SimpleIntegerProperty(chambre_id);
    }

    public int getUtilisateur_id() {
        return utilisateur_id.get();
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id =  new SimpleIntegerProperty(utilisateur_id);
    }

    public int getQuantite() {
        return quantite.get();
    }

    public void setQuantite(int quantite) {
        this.quantite =  new SimpleIntegerProperty(quantite);
    }

    public String getDatedebut() {
        return datedebut.get();
    }

    public void setDatedebut(String datedebut) {
        this.datedebut =  new SimpleStringProperty(datedebut);
    }

    public String getDatefin() {
        return datefin.get();
    }

    public void setDatefin(String datefin) {
        this.datefin =  new SimpleStringProperty(datefin);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", chambre_id=" + chambre_id + ", utilisateur_id=" + utilisateur_id + ", quantite=" + quantite + ", datedebut=" + datedebut + ", datefin=" + datefin + '}';
    }
    
    
    
    

    
}
