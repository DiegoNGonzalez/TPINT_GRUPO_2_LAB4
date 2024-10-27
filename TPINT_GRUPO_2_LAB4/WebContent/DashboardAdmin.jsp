<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<jsp:include page="css\styles.css"></jsp:include>
</style>
<title>Panel Administrador </title>
</head>
<body>

<nav style="background-color: #3a7bd5; padding: 10px; color: white;">
    <div style="display: flex; justify-content: space-between; align-items: center;">

        <div>
            <a href="DashboardAdmin.jsp" style="color: white; text-decoration: none; font-size: 20px; font-weight: bold;">
                Banco XYZ - UTN
            </a>
        </div>
        
        <!-- Sección de usuario y cierre de sesión -->
        <div>
            <%-- Verificación de usuario logueado (para después) --%>
            <% if (session.getAttribute("usuario") != null) { %>
                <span>Bienvenido, <%= session.getAttribute("usuario") %></span>
                <a href="Logout.jsp" style="color: white; margin-left: 15px; text-decoration: none;">Cerrar Sesión</a>
            <% } else { %>
                <span>No hay usuario logueado</span>
            <% } %>
        </div>
    </div>
</nav>

    <div class="dashboard-header">
        <h2>Bienvenido, Administrador</h2>
        <p>Resumen del día y accesos rápidos a las secciones principales.</p>
    </div>

    <!-- Sección de resumen con tarjetas de acceso rápido -->
    <div class="dashboard-content">
        <!-- Tarjetas de acceso rápido -->
        <div class="dashboard-card" onclick="window.location.href='MenuClientes.jsp'">
            <h3>Menú de Clientes</h3>
            <p>Gestión de clientes y perfiles.</p>
        </div>
        <div class="dashboard-card" onclick="window.location.href='Cuenta.jsp'">
            <h3>Cuentas</h3>
            <p>Administración de cuentas bancarias.</p>
        </div>
        <div class="dashboard-card" onclick="window.location.href='GestionPrestamos.jsp'">
            <h3>Gestión de Préstamos</h3>
            <p>Control y seguimiento de préstamos.</p>
        </div>
        <div class="dashboard-card" onclick="window.location.href='InformeEstadistico.jsp'">
            <h3>Informe Estadístico</h3>
            <p>Estadísticas y reportes financieros.</p>
        </div>
    </div>

</body>
</html>