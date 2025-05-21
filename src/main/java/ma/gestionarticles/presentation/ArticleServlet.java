package ma.gestionarticles.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.gestionarticles.dao.ArticleDAO;
import ma.gestionarticles.model.Article;

import java.io.IOException;
import java.util.List;

@WebServlet("/articles")
public class ArticleServlet extends HttpServlet {

    private final ArticleDAO articleDAO = new ArticleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Article articleToEdit = articleDAO.getArticleById(idEdit);
                request.setAttribute("article", articleToEdit);
                request.getRequestDispatcher("view/form.jsp").forward(request, response);
                break;

            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                articleDAO.deleteArticle(idDelete);
                response.sendRedirect("articles");
                break;

            case "search":
                String keyword = request.getParameter("keyword");
                List<Article> searchResults = articleDAO.searchArticles(keyword);
                request.setAttribute("articles", searchResults);
                request.getRequestDispatcher("view/articles.jsp").forward(request, response);
                break;

            default:
                List<Article> articles = articleDAO.getAllArticles();
                request.setAttribute("articles", articles);
                request.getRequestDispatcher("view/articles.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        String description = request.getParameter("description");
        String prixStr = request.getParameter("prix");
        String quantiteStr = request.getParameter("quantite_stock");

        double prix = 0;
        int quantite = 0;

        try {
            prix = Double.parseDouble(prixStr);
            quantite = Integer.parseInt(quantiteStr);
        } catch (NumberFormatException e) {
            request.setAttribute("erreur", "Prix ou quantité invalide !");
            request.getRequestDispatcher("view/form.jsp").forward(request, response);
            return;
        }

        Article article = new Article();
        article.setDescription(description);
        article.setPrix(prix);
        article.setQuantiteStock(quantite);

        if ("update".equals(action)) {
            // Récupérer l'id de l'article à modifier
            int id = Integer.parseInt(request.getParameter("id"));
            article.setId(id);
            articleDAO.updateArticle(article);
        } else {
            // Ajouter nouvel article
            articleDAO.insertArticle(article);
        }

        response.sendRedirect("articles");
    }
}
