<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:choose>
        <c:when test="${not empty article}">Modifier</c:when>
        <c:otherwise>Ajouter</c:otherwise>
    </c:choose> un article</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2><c:choose>
        <c:when test="${not empty article}">Modifier</c:when>
        <c:otherwise>Ajouter</c:otherwise>
    </c:choose> un article</h2>

    <c:if test="${not empty erreur}">
        <div class="alert alert-danger">${erreur}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/articles">
        <input type="hidden" name="action" value="${not empty article ? 'update' : 'insert'}" />
        <c:if test="${not empty article}">
            <input type="hidden" name="id" value="${article.id}" />
        </c:if>

        <div class="form-group">
            <label>Description</label>
            <input type="text" name="description" class="form-control" required
                   value="${article.description}" />
        </div>
        <div class="form-group">
            <label>Prix</label>
            <input type="number" step="0.01" name="prix" class="form-control" required
                   value="${article.prix}" />
        </div>
        <div class="form-group">
            <label>Quantité en stock</label>
            <input type="number" name="quantite_stock" class="form-control" required
                   value="${article.quantiteStock}" />
        </div>

        <button type="submit" class="btn btn-primary">
            <c:choose>
                <c:when test="${not empty article}">Modifier</c:when>
                <c:otherwise>Ajouter</c:otherwise>
            </c:choose>
        </button>
        <a href="articles" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html>
