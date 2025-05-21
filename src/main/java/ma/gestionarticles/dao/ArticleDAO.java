package ma.gestionarticles.dao;

import ma.gestionarticles.model.Article;
import java.math.BigDecimal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    private Connection getConnection() throws SQLException {
        return DBUtil.getConnection();
    }

    public void insertArticle(Article article) {
        String sql = "INSERT INTO article (description, prix, quantite_stock) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, article.getDescription());
            stmt.setDouble(2, article.getPrix());
            stmt.setInt(3, article.getQuantiteStock());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Pour le debug
        }
    }
    public void updateArticle(Article article) {
        String sql = "UPDATE article SET description=?, prix=?, quantite_stock=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, article.getDescription());
            stmt.setBigDecimal(2, BigDecimal.valueOf(article.getPrix())); // conversion ici
            stmt.setInt(3, article.getQuantiteStock());
            stmt.setInt(4, article.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteArticle(int id) {
        String sql = "DELETE FROM article WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Article> searchArticles(String keyword) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM article WHERE description LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Article a = new Article();
                a.setId(rs.getInt("id"));
                a.setDescription(rs.getString("description"));
                a.setPrix(rs.getDouble("prix"));
                a.setQuantiteStock(rs.getInt("quantite_stock"));
                articles.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }
    public Article getArticleById(int id) {
        Article a = null;
        String sql = "SELECT * FROM article WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                a = new Article();
                a.setId(rs.getInt("id"));
                a.setDescription(rs.getString("description"));
                a.setPrix(rs.getDouble("prix"));
                a.setQuantiteStock(rs.getInt("quantite_stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }



    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM article";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setDescription(rs.getString("description"));
                article.setPrix(rs.getDouble("prix"));
                article.setQuantiteStock(rs.getInt("quantite_stock"));
                articles.add(article);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
