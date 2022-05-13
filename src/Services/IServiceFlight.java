/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Flight;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author GMI
 */
public interface IServiceFlight <T>{
      void ajoutFlight(T t);
       List<Flight> AfficherFlight();
      void supprimer(int id_flight);
      void modifier(Flight F )throws SQLException;

 
    
}
