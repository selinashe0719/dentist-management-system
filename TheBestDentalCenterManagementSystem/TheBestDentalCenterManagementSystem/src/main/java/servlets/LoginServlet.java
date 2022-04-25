package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.PostgreSqlConn;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		PostgreSqlConn connection = new PostgreSqlConn();
		if (role.equals("Receptionist")) {
			String[] pwdfromdb = connection.getReceptionistLoginInfoByName(username);
			if (password.equals(pwdfromdb[1])) {
				request.setAttribute("id", pwdfromdb[0]);
				request.setAttribute("username", username);
				request.setAttribute("fname", pwdfromdb[2]);
				request.getRequestDispatcher("receptionistHome.jsp").forward(request, response);
				return;
			}
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Username or password is incorrect');");
			out.println("location='login.html?Receptionist';");
			out.println("</script>");
			return;
		}
		else if (role.equals("Dentist")) {
			String[] pwdfromdb = connection.getDentistLoginInfoByName(username);
			if (password.equals(pwdfromdb[1])) {
				request.setAttribute("id", pwdfromdb[0]);
				request.setAttribute("username", username);
				request.setAttribute("fname", pwdfromdb[2]);
				request.getRequestDispatcher("dentistHome.jsp").forward(request, response);
				return;
			}
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Username or password is incorrect');");
			out.println("location='login.html?Dentist';");
			out.println("</script>");
			return;
		}
		else if (role.equals("Patient")) {
			String[] pwdfromdb = connection.getPatientLoginInfoByName(username);
			if (password.equals(pwdfromdb[1])) {
				request.setAttribute("id", pwdfromdb[0]);
				request.setAttribute("username", username);
				request.setAttribute("fname", pwdfromdb[2]);
				request.getRequestDispatcher("patientHome.jsp").forward(request, response);
				return;
			}
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Username or password is incorrect');");
			out.println("location='login.html?Patient';");
			out.println("</script>");
			return;
		}
	}

}
