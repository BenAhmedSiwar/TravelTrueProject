/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author GMI
 */
public class Flight {
    private int id_flight;
    private String villedepart;
    private String villearrive;
    private Date datedepart;
    private Date datearrive;
    private String numplane;
    private String compagnie;
    private int prix;
    private int quantite;
    private String image;

    public Flight() {
    }

    public Flight(String villedepart, String villearrive,String numplane, String compagnie, int prix, int quantite) {
        this.villedepart = villedepart;
        this.villearrive = villearrive;
         this.prix = prix;
         this.numplane = numplane;
        this.compagnie = compagnie;
        this.quantite = quantite;
     
    }

    public Flight(int id_flight, String villedepart, String villearrive, Date datedepart, Date datearrive, String numplane, String compagnie, int prix, int quantite, String image) {
        this.id_flight = id_flight;
        this.villedepart = villedepart;
        this.villearrive = villearrive;
        this.datedepart = datedepart;
        this.datearrive = datearrive;
        this.numplane = numplane;
        this.compagnie = compagnie;
        this.prix = prix;
        this.quantite = quantite;
        this.image = image;
    }

    public Flight(String villedepart, int prix) {
                this.villedepart = villedepart;
               this.prix = prix;

    }

   

    public int getId_flight() {
        return id_flight;
    }

    public void setId_flight(int id_flight) {
        this.id_flight = id_flight;
    }

   

    public String getVilledepart() {
        return villedepart;
    }

    public void setVilledepart(String villedepart) {
        this.villedepart = villedepart;
    }

    public String getVillearrive() {
        return villearrive;
    }

    public void setVillearrive(String villearrive) {
        this.villearrive = villearrive;
    }

    public Date getDatedepart() {
        return datedepart;
    }

    public void setDatedepart(Date datedepart) {
        this.datedepart = datedepart;
    }

    public Date getDatearrive() {
        return datearrive;
    }

    public void setDatearrive(Date datearrive) {
        this.datearrive = datearrive;
    }

    public String getNumplane() {
        return numplane;
    }

    public void setNumplane(String numplane) {
        this.numplane = numplane;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Flight{" + "id_flight=" + id_flight + ", villedepart=" + villedepart + ", villearrive=" + villearrive + ", datedepart=" + datedepart + ", datearrive=" + datearrive + ", numplane=" + numplane + ", compagnie=" + compagnie + ", prix=" + prix + ", quantite=" + quantite + ", image=" + image + '}';
    }

    

}
