/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetf;

import Entities.Flight;
import Services.FlightService;
import java.sql.SQLException;


/**
 *
 * @author GMI
 */
public class Projetf {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        Flight  f;
        f = new Flight("rome",100);
        
        
       
        FlightService fs= new FlightService();
        fs.ajoutFlight(f);
         System.out.println(fs.AfficherFlight());
      fs.supprimer(22);
     // fs.modifer(new Flight("rome",200.f));
    }
    
}
