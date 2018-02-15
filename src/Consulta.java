

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/consulta")
public class Consulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Consulta() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Recogemos el parametro autor del formulario.
		
		String autor=request.getParameter("autor");
		
		//Preparamos la salida del servlet
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//Objetos para la conexion y consulta a la 
		//base de datos
		Connection con = null;
		PreparedStatement stmt = null;
		
		//Cargar el driver de MySQL
		try {
			Class.forName("com.mysql.jbdc.Driver");
			
			//Credenciales para la base de datos
			String url = "jbdc:mysql://localhost/TiendaLibros";
			
			String usuario = "librero";
			String password = "Ageofempires2";
			
			//Ejecutamos una consulta SQL.
			con = DriverManager.getConnection(url, usuario, password);
			String sql = "SELECT * FROM libros WHERE autor = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, autor);
			
			ResultSet resultados = stmt.executeQuery();
			
			//Mostramos los resultados
			
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("<tittle>TITULO</tittle>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Cunsulta de libros por autor</h1>");
			out.println("<p>Libros escritos por "+ autor + "</p>");
			while(resultados.next()){
				out.println("Titulo: "+ resultados.getString("titulo")+ "<br>");
				out.println("Precio: "+ resultados.getString("precio")+ "<br>");
				out.println("Cantidad: "+ resultados.getString("cantidad")+ "<br>");
			}
			out.println("</body>");
			out.println("</html>");
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS.");
			e.printStackTrace();
		}finally{
			try {
				con.close();
				stmt.close();

			} catch (SQLException e) {
				System.out.println("ERROR AL CERRAR LA BASE DE DATOS.");
				e.printStackTrace();
			}
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
