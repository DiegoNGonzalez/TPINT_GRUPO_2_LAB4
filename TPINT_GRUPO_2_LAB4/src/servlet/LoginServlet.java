package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entidades.Usuario;
import negocio.UsuarioNegocio;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
        // Verificamos las credenciales a trav�s de la capa de negocio
        Usuario usuario = usuarioNegocio.verificarCredenciales(username, password);

        if (usuario != null) {
            if (usuario.isActivo()) {
                HttpSession session = request.getSession();
                session.setAttribute("userType", usuario.getTipoUsuario().getTipoUsuario());
                session.setAttribute("usuario", usuario.getNombreUsuario());
                System.out.println("Sesi�n configurada con userType: " + usuario.getTipoUsuario().getTipoUsuario());
                // Redirigir seg�n el tipo de usuario
                if ("Administrador".equals(usuario.getTipoUsuario().getTipoUsuario())) {
                    response.sendRedirect("DashboardAdmin.jsp");
                } else if ("Cliente".equals(usuario.getTipoUsuario().getTipoUsuario())) {
                    response.sendRedirect("DashboardCliente.jsp");
                }
            } else {
            	// Establece un mensaje de error en la sesi�n
                request.getSession().setAttribute("errorMsj", "Usuario inactivo. No tiene acceso.");
                // Redirige a la p�gina de error
                response.sendRedirect("Error.jsp");
            }
        } else {
        	 request.getSession().setAttribute("errorMsj", "Usuario o contrase�a incorrectos.");
             // Redirige a la p�gina de error
             response.sendRedirect("Error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");

        if ("logout".equals(action)) {
            // Invalidar la sesi�n si la acci�n es logout
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                System.out.println("Sesi�n invalidada");
            }
            // Redirigir al login despu�s de cerrar sesi�n
            response.sendRedirect("Login.jsp");
        } else {
            // Redirigir al login si no se especifica la acci�n
            response.sendRedirect("Login.jsp");
        }
    }
}

