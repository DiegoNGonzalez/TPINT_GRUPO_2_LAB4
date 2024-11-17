package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Cliente;
import entidades.Usuario;
import exceptions.UsuarioNegocioException;
import negocio.UsuarioNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String username = request.getParameter("username");
	    	String password = request.getParameter("password");
	
	    	UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
	    
	        try {
	            // Verificamos las credenciales a trav�s de la capa de negocio
	            Usuario usuario = usuarioNegocio.verificarCredenciales(username, password);	                       
	            
	            if (usuario != null && usuario.isActivo()) {
	            	ClienteNegocioImpl clienteNegocio = new ClienteNegocioImpl();
	            	//busca dos veces al usuario, modificar dsp. dejar solo cliente en session que contiene a usuario? tb se podria poner en el if de abajo.
	            	Cliente cliente = clienteNegocio.obtenerClientePorIdUsuario(usuario.getId());
	                HttpSession session = request.getSession();
	                session.setAttribute("userType", usuario.getTipoUsuario().getTipoUsuario());
	                session.setAttribute("usuario", usuario.getNombreUsuario());
	                session.setAttribute("cliente", cliente);
	                System.out.println("Sesi�n configurada con userType: " + usuario.getTipoUsuario().getTipoUsuario());
	
	                // Redirigir seg�n el tipo de usuario
	                if ("Administrador".equals(usuario.getTipoUsuario().getTipoUsuario())) {
	                    response.sendRedirect("DashboardAdmin.jsp");
	                } else if ("Cliente".equals(usuario.getTipoUsuario().getTipoUsuario())) {
	                	request.setAttribute("cliente", cliente);
	                    request.getRequestDispatcher("DashboardCliente.jsp").forward(request, response);
	                	//response.sendRedirect("DashboardCliente?cliente=" + cliente);
	                    //response.sendRedirect("DashboardCliente.jsp");
	                }
	            } else {
	                // Usuario no activo o no encontrado
	                throw new UsuarioNegocioException("Usuario inactivo o credenciales incorrectas.");
	            }
	        } catch (UsuarioNegocioException e) {
	            // Manejo de la excepci�n de negocio
	            request.getSession().setAttribute("errorMsj", e.getMessage());
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


