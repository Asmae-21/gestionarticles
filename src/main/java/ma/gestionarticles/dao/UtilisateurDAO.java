package ma.gestionarticles.dao;

import ma.gestionarticles.model.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilisateurDAO {
    public Utilisateur getByUsernameAndPassword(String username, String password) {
        Utilisateur utilisateur = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("Recherche utilisateur : username=" + username + ", password=" + password);

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM utilisateur WHERE username=? AND password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setUsername(rs.getString("username"));
                utilisateur.setPassword(rs.getString("password"));
                System.out.println("Utilisateur trouvé : " + utilisateur.getUsername());
            } else {
                System.out.println("Aucun utilisateur correspondant");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close(); // Fermez la connexion ici
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return utilisateur;
    }
}
