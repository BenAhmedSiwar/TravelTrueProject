/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Services;

import BackOffice.Entite.Chambre;
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
public class ChambreService implements IService<Chambre> {

    private Statement st;
    private ResultSet rs;
    Connection cnx;
    private static ChambreService instance;

    public ChambreService() {
        try {
            ConnexionSingleton cs = ConnexionSingleton.getInstance();
            st = cs.getCnx().createStatement();
            cnx = ConnexionSingleton.getInstance().getCnx();
        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ChambreService getInstance() {
        if (instance == null) {
            instance = new ChambreService();
        }
        return instance;
    }

    @Override
    public boolean insert(Chambre ch) {
        try {
            String req = "insert into chambre (type,vue,prix,image,title,quantite) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, ch.getType());
            preparedStatement.setString(2, ch.getVue());
            preparedStatement.setDouble(3, ch.getPrix());
            preparedStatement.setString(4, ch.getImage());
            preparedStatement.setString(5, ch.getTitle());
            preparedStatement.setInt(6, ch.getQuantite());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public void delete(Chambre ch) {
        String req = "delete from chambre where id=" + ch.getId();
        Chambre s = displayById(ch.getId());

        if (s != null) {
            try {

                st.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("not found");
        }
    }

    @Override
    public List<Chambre> displayAll() {
        String req = "select * from chambre";

        List<Chambre> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Chambre s = new Chambre();
                s.setId(rs.getInt("id"));
                s.setType(rs.getString("type"));
                s.setVue(rs.getString("vue"));
                s.setPrix(rs.getDouble("prix"));
                s.setImage(rs.getString("image"));
                s.setTitle(rs.getString("title"));
                s.setQuantite(rs.getInt("quantite"));
                list.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ObservableList<Chambre> displayAllFX() {
        String req = "select * from chambre";
        ObservableList<Chambre> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Chambre s = new Chambre();
                s.setId(rs.getInt("id"));
                s.setType(rs.getString("type"));
                s.setVue(rs.getString("vue"));
                s.setPrix(rs.getDouble("prix"));
                s.setImage(rs.getString("image"));
                s.setTitle(rs.getString("title"));
                s.setQuantite(rs.getInt("quantite"));
                list.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Chambre displayById(int id) {
        String req = "select * from chambre where id =" + id;
        Chambre s = new Chambre();
        try {
            rs = st.executeQuery(req);
            // while(rs.next()){
            rs.next();
            s.setId(rs.getInt("id"));
            s.setType(rs.getString("type"));
            s.setVue(rs.getString("vue"));
            s.setPrix(rs.getDouble("prix"));
            s.setImage(rs.getString("image"));
            s.setTitle(rs.getString("title"));
            s.setQuantite(rs.getInt("quantite"));

            //}  
        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    @Override
    public boolean update(Chambre ch) {
        try {
            String qry = "UPDATE chambre SET type =?,vue = ?," + "prix =?,image = ?,title= ?,quantite= ?  WHERE id =?";
            PreparedStatement preparedStatement = cnx.prepareStatement(qry);
            preparedStatement.setString(1, ch.getType());
            preparedStatement.setString(2, ch.getVue());
            preparedStatement.setDouble(3, ch.getPrix());
            preparedStatement.setString(4, ch.getImage());
            preparedStatement.setString(5, ch.getTitle());
            preparedStatement.setInt(6, ch.getQuantite());
            preparedStatement.setInt(7, ch.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public ObservableList<Chambre> SearchTitleDynamic(String titlechambre) {
        ObservableList<Chambre> chambreList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM chambre WHERE ( title LIKE '" + titlechambre + "%')";
            //statement.executeQuery(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                Chambre chambre = new Chambre(rs.getInt("id"), rs.getString("type"), rs.getString("vue"), rs.getDouble("prix"), rs.getString("image"), rs.getString("title"), rs.getInt("quantite"));
                chambreList.add(chambre);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return chambreList;
    }

    public void setlike(int idcha, int nb) {
        try {
            String req = "update chambre set nblikee=?  where id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, nb);
            ps.setInt(2, idcha);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getnblike(int id) {
        int s = 0;
        try {

            String req = "select * from chambre where id=" + id;
            rs = st.executeQuery(req);
            while (rs.next()) {
                s = rs.getInt(8);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public void setdislike(int idcha, int nb) {
        try {
            String req = "update chambre set nbdislikee=?  where id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, nb);
            ps.setInt(2, idcha);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getnbdislike(int id) {
        int s = 0;
        try {

            String req = "select * from chambre where id=" + id;
            rs = st.executeQuery(req);
            while (rs.next()) {
                s = rs.getInt(9);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public int checklikeuser(int iduser, int idc) {
        int s = 0;
        try {

            String req = "select * from likee where userId =" + iduser + " and chambreId =" + idc;
            rs = st.executeQuery(req);
            while (rs.next()) {
                s = rs.getInt(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public boolean addlike(int iduser, int idc) {
        try {
            String req = "insert into likee (userId ,chambreId ) values " + "('" + iduser + "','" + idc + "')";
            Statement st = cnx.createStatement();
            try {
                st.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ChambreService.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    public int checkdislikeuser(int iduser, int idc) {
        int s = 0;
        try {

            String req = "select * from dislikee where userId =" + iduser + " and chambreId  =" + idc;
            rs = st.executeQuery(req);
            while (rs.next()) {
                s = rs.getInt(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public boolean adddislike(int iduser, int idc) {
        try {
            String req = "insert into dislikee (userId ,chambreId ) values " + "('" + iduser + "','" + idc + "')";
            Statement st = cnx.createStatement();
            try {
                st.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ChambreService.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    public boolean deletelike(int id) {
        try {
            String req = "delete from likee where likeId =?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    public boolean deletedislike(int id) {
        try {
            String req = "delete from dislikee where dislikeId =?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }
       public void setquantite(int idcha, int nb) {
        try {
            String req = "update chambre set quantite=?  where id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, nb);
            ps.setInt(2, idcha);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getquantite(int id) {
        int s = 0;
        try {

            String req = "select * from chambre where id=" + id;
            rs = st.executeQuery(req);
            while (rs.next()) {
                s = rs.getInt(7);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
     public ObservableList<Chambre>  displayByIdUser(int iduser) {
       String req = "select * from chambre c inner join reservation r on c.id=r.chambre_id where r.utilisateur_id="+iduser;
        ObservableList<Chambre> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Chambre s = new Chambre();
                s.setId(rs.getInt("id"));
                s.setType(rs.getString("type"));
                s.setVue(rs.getString("vue"));
                s.setPrix(rs.getDouble("prix"));
                s.setImage(rs.getString("image"));
                s.setTitle(rs.getString("title"));
                s.setQuantite(rs.getInt("quantite"));
                list.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
     

  
}
