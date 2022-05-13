/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Flight;
import Utils.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GMI
 */
public class FlightService implements IServiceFlight<Flight>{
    
      Connection cnx;
      Statement stm;
    public FlightService(){
    cnx=MyDB.getInstance().getCnx();
    }

   

    @Override
    public void ajoutFlight(Flight F) {
         try {
            String req="INSERT INTO `flight`( `villearrivee`, `villedepart`,`prix`, `numplane`, `compagnie`, `quantite`) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstm=  cnx.prepareStatement(req);
            pstm.setString(1, F.getVillearrive());
            pstm.setString(2, F.getVilledepart());
            pstm.setFloat(3, F.getPrix());
            pstm.setString(4, F.getNumplane());
           // pstm.setString(5, F.getCompagnie());
            //pstm.setInt(6, F.getQuantite());
             pstm.executeUpdate();
                System.out.println("ajout Flight succes ! ");

          } catch (SQLException ex) {
                System.out.println(ex.getMessage());

          }

    }

    @Override
    public List<Flight> AfficherFlight() {
        List<Flight> flights = new ArrayList<>();
        
        try {
            String req="SELECT * FROM `flight`";     
            stm=cnx.createStatement();
            ResultSet res = stm.executeQuery(req);
            while(res.next()){
                Flight f = new Flight();
                f.setId_flight(res.getInt("id_flight"));
                f.setVilledepart(res.getString("Villedepart"));
                f.setVillearrive(res.getString("Villearrivee"));
                f.setNumplane(res.getString("Numplane"));  
                f.setCompagnie(res.getString("Compagnie"));  
                f.setPrix(res.getInt("prix"));  

                
               
                flights.add(f);
            }
        } catch (SQLException ex) {
       System.out.println(ex.getMessage());
        }
         
        return flights;
    }

   
   
     @Override
    public void supprimer(int id_flight) {
         try {
        String req1 ="Delete from flight where id_flight=? ;";
        PreparedStatement ps = cnx.prepareStatement(req1);
    
        ps.setInt(1, id_flight);
        if (ps.executeUpdate() != 0) {
            System.out.println("flight Deleted");      
        }else
        System.out.println("id flight not found!!!");
        } catch (SQLException ex) {
                        System.out.println(ex.getMessage());

            
        }    }

    @Override
    public void modifier(Flight F) throws SQLException {
 String req = "UPDATE `flight` SET `villedepart`='"
                + F.getPrix()+ "',`prix`='"
                + F.getNumplane()+ "', `numplane`='"+
           
                "WHERE `id_flight`='"+ F.getId_flight()+ "'";
         
       
         try {
            stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Flight update");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }
    
    
}
    
    
    
   
  

