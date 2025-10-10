<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body text-center">
                        <i class="fas fa-exclamation-triangle text-danger" style="font-size: 4rem;"></i>
                        <h1 class="mt-3">¡Oops! Algo salió mal</h1>
                        <p class="lead">Ha ocurrido un error inesperado.</p>
                        
                        <div class="alert alert-info">
                            <strong>Código de Error:</strong> 
                            <%= response.getStatus() %>
                        </div>
                        
                        <div class="mt-4">
                            <a href="index.jsp" class="btn btn-primary">
                                <i class="fas fa-home"></i> Volver al Inicio
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

