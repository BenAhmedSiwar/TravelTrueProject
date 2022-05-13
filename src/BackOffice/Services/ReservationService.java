/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Services;

import BackOffice.Entite.Reservation;
import BackOffice.Utils.ConnexionSingleton;
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
 * @author bouss
 */
public class ReservationService implements IService<Reservation>{
        
        private Statement st;
        private ResultSet rs;
        Connection cnx;
         private static ReservationService instance;

    public ReservationService() {
         try {
                ConnexionSingleton cs = ConnexionSingleton.getInstance();
                st = cs.getCnx().createStatement();
                cnx = ConnexionSingleton.getInstance().getCnx();
            } catch (SQLException ex) {
                Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
      public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    @Override
    public boolean insert(Reservation o) {
         try {
            String req = "insert into reservation (chambre_id,utilisateur_id,quantite,datedebut,datefin) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setInt(1, o.getChambre_id());
            preparedStatement.setInt(2, o.getUtilisateur_id());
            preparedStatement.setInt(3, o.getQuantite());
            preparedStatement.setString(4, o.getDatedebut());
            preparedStatement.setString(5, o.getDatefin());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public void delete(Reservation o) {
            String req = "delete from reservation where id=" + o.getId();
            Reservation r = displayById(o.getId());

        if (r != null) {
            try {

                st.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("not found");
        }    
    }

    @Override
    public List<Reservation> displayAll() {
           String req = "select * from reservation";

        List<Reservation> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setChambre_id(rs.getInt("chambre_id"));
                r.setUtilisateur_id(rs.getInt("utilisateur_id"));
                r.setQuantite(rs.getInt("quantite"));
                r.setDatedebut(rs.getString("datedebut"));
                r.setDatefin(rs.getString("datefin"));
                list.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public List<Reservation> displayAllFX() {
        String req = "select * from reservation";
        ObservableList<Reservation> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setChambre_id(rs.getInt("chambre_id"));
                r.setUtilisateur_id(rs.getInt("utilisateur_id"));
                r.setQuantite(rs.getInt("quantite"));
                r.setDatedebut(rs.getString("datedebut"));
                r.setDatefin(rs.getString("datefin"));
                list.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Reservation displayById(int id) {
        String req = "select * from reservation where id =" + id;
        Reservation r = new Reservation();
        try {
            rs = st.executeQuery(req);
            // while(rs.next()){
            rs.next();
                r.setId(rs.getInt("id"));
                r.setChambre_id(rs.getInt("chambre_id"));
                r.setUtilisateur_id(rs.getInt("utilisateur_id"));
                r.setQuantite(rs.getInt("quantite"));
                r.setDatedebut(rs.getString("datedebut"));
                r.setDatefin(rs.getString("datefin"));

            //}  
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r; 
    }

    @Override
    public boolean update(Reservation os) {
        try {
            String qry = "UPDATE reservation SET quantite =?,datedebut = ?," + "datefin =? WHERE id =?";
            PreparedStatement preparedStatement = cnx.prepareStatement(qry);
            preparedStatement.setInt(1, os.getQuantite());
            preparedStatement.setString(2, os.getDatedebut());
            preparedStatement.setString(3, os.getDatefin());
            preparedStatement.setInt(4, os.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }
       public ObservableList<Reservation>  displayByIdUser(int iduser) {
       String req = "select * from reservation r inner join chambre c on r.chambre_id=c.id where r.utilisateur_id="+iduser;
        ObservableList<Reservation> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation s = new Reservation();
                s.setId(rs.getInt("id"));
                s.setDatedebut(rs.getString("datedebut"));
                s.setDatefin(rs.getString("datefin"));
                s.setQuantite(rs.getInt("quantite"));
                list.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
