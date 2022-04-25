package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.PostgreSqlConn;
import entities.Appointment;
import entities.Record;

/**
 * Servlet implementation class PatientNavServlet
 */
@WebServlet("/patientNavServlet")
public class PatientNavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Open connection to database
		PostgreSqlConn connection = new PostgreSqlConn();
				
		// Collect and send variables that are the same for every page
		String id = request.getParameter("id");
		String fname = request.getParameter("fname");
		request.setAttribute("id", id);
		request.setAttribute("fname", fname);
				
		// If the user pressed the Menu->Home button
		if (request.getParameter("Home") != null) {
			
			request.getRequestDispatcher("patientHome.jsp").forward(request, response);
			
		// If the user pressed the Menu->Appointments button
		} else if (request.getParameter("Appointments") != null) {
			
			ArrayList<Appointment> futureAppointments = connection.getPatientFutureAppointments(id);
			ArrayList<Appointment> pastAppointments = connection.getPatientPastAppointments(id);
			
			request.setAttribute("futureAppointments", futureAppointments);
			request.setAttribute("pastAppointments", pastAppointments);
			
			request.getRequestDispatcher("patientAppointments.jsp").forward(request, response);
			
		// If the user pressed the Menu->Dental records button
		} else if (request.getParameter("Dental records") != null) {
			
			ArrayList<Record> records = connection.getPatientRecords(id);
			
			request.setAttribute("records", records);
			
			request.getRequestDispatcher("patientRecords.jsp").forward(request, response);
			
		// If the user pressed the Menu->Logout button
		} else if (request.getParameter("Logout") != null) {
			
			request.getRequestDispatcher("index.html").forward(request, response);
			
		}
		return;
	}

}
