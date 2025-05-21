<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des articles</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Articles</h2>

    <form method="get" action="articles" class="form-inline mb-3">
        <input type="hidden" name="action" value="search" />
        <input type="text" name="keyword" placeholder="Rechercher description..." class="form-control mr-2" />
        <button type="submit" class="btn btn-primary">Rechercher</button>
        <a href="articles" class="btn btn-secondary ml-2">Réinitialiser</a>
    </form>

    <a href="${pageContext.request.contextPath}/form" class="btn btn-success mb-3">Ajouter un article</a>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Description</th>
            <th>Prix</th>
            <th>Quantité en stock</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}">
            <tr>
                <td>${article.description}</td>
                <td>${article.prix}</td>
                <td>${article.quantiteStock}</td>
                <td>
                    <a href="articles?action=edit&id=${article.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <a href="articles?action=delete&id=${article.id}"
                       onclick="return confirm('Confirmer la suppression ?');"
                       class="btn btn-danger btn-sm">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
