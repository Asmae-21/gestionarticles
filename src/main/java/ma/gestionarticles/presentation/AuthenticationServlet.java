package ma.gestionarticles.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.gestionarticles.dao.UtilisateurDAO;
import ma.gestionarticles.model.Utilisateur;

import java.io.IOException;

@WebServlet("/login")
public class AuthenticationServlet extends HttpServlet {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Tentative de connexion avec username: '" + username + "' et password: '" + password + "'");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("erreur", "Veuillez saisir le nom d'utilisateur et le mot de passe.");
            request.getRequestDispatcher("view/login.jsp").forward(request, response);
            return;
        }

        Utilisateur utilisateur = utilisateurDAO.getByUsernameAndPassword(username.trim(), password.trim());

        if (utilisateur != null) {
            System.out.println("Authentification réussie pour l'utilisateur: " + utilisateur.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            response.sendRedirect("articles");
        } else {
            System.out.println("Authentification échouée pour username: " + username);
            request.setAttribute("erreur", "Identifiants invalides !");
            request.getRequestDispatcher("view/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("view/login.jsp");
    }
}
