/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author GMI
 */
public class ResFlight {
    private int id_resflight ;
    private String Clientname,email;
    private Flight Flight;

    public ResFlight() {
    }

    public ResFlight(String Clientname, String email,Flight Flight) {
        this.Clientname = Clientname;
        this.email = email;
        this.Flight=Flight;
    }

    public ResFlight(int id, String Clientname, String email,Flight Flight) {
        this.id_resflight = id;
        this.Clientname = Clientname;
        this.email = email;
        this.Flight=Flight;

    }

    public int getId_resflight() {
        return id_resflight;
    }

    public void setId_resflight(int id_resflight) {
        this.id_resflight = id_resflight;
    }

   

    public String getClientname() {
        return Clientname;
    }

    public void setClientname(String Clientname) {
        this.Clientname = Clientname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Flight getFlight() {
        return Flight;
    }

    public void setFlight(Flight Flight) {
        this.Flight = Flight;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id_resflight=" + id_resflight + ", Clientname=" + Clientname + ", email=" + email + ", Flight=" + Flight + '}';
    }

    public void setId_flight(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
    
      
    
}
