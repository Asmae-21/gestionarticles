package ma.gestionarticles.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBUtil {
    // Ne gardez pas de connexion statique

    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Chargement explicite du pilote
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();
            InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("database.properties");
            if (input == null) {
                System.out.println("Fichier database.properties non trouvé !");
                return null;
            }
            props.load(input);

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            // Ajout de paramètres pour gérer les caractères spéciaux
            if (!url.contains("?")) {
                url += "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            }

            System.out.println("URL : " + url);
            System.out.println("Utilisateur : " + user);

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion établie avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    // Ajoutez une méthode pour fermer la connexion
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connexion fermée avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}