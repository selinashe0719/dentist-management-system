package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.PostgreSqlConn;
import entities.Appointment;
import entities.Patient;
import entities.ProcedureType;
import entities.Record;

/**
 * Servlet implementation class PatientNavServlet
 */
@WebServlet("/dentistNavServlet")
public class DentistNavServlet extends HttpServlet {
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
			
			request.getRequestDispatcher("dentistHome.jsp").forward(request, response);
			
		// If the user pressed the Menu->Appointments button
		} else if (request.getParameter("Appointments") != null) {
			
			ArrayList<Appointment> futureAppointments = connection.getDentistFutureAppointments(id);
			
			request.setAttribute("futureAppointments", futureAppointments);
			
			request.getRequestDispatcher("dentistAppointments.jsp").forward(request, response);
			
		// If the user pressed the Menu->Patients button
		} else if (request.getParameter("Patients") != null) {
			
			ArrayList<Patient> patientsList = connection.getAllPatients();
			
			request.setAttribute("patientsList", patientsList);
			
			request.getRequestDispatcher("dentistPatients.jsp").forward(request, response);
			
		// If the user pressed the Menu->Procedure types button
		} else if (request.getParameter("Procedure types") != null){
			
			ArrayList<ProcedureType> procedureTypes = connection.getAllProcedureTypes();
			
			request.setAttribute("procedureTypes", procedureTypes);
			
			request.getRequestDispatcher("dentistProcedureTypes.jsp").forward(request, response);
			
		// If the user pressed the Menu->Logout button
		} else if (request.getParameter("Logout") != null) {
			
			request.getRequestDispatcher("index.html").forward(request, response);
			
		// If the user pressed the "View data" button for a particular patient
		} else if (request.getParameter("viewPatientData") != null) {
			
			String patientId = request.getParameter("patientId");
			String patientName = request.getParameter("patientName");
			
			ArrayList<Record> patientRecords = connection.getPatientRecords(patientId);
			
			request.setAttribute("patientRecords", patientRecords);
			request.setAttribute("patientName", patientName);
			request.setAttribute("patientId", patientId);
			
			request.getRequestDispatcher("dentistViewPatientRecords.jsp").forward(request, response);
			
		// If the user pressed the "Add patient record" button at the bottom of a patient's records table
		} else if (request.getParameter("addPatientRecord") != null) {
			
			String patientName = request.getParameter("patientName");
			String patientId = request.getParameter("patientId");
			
			request.setAttribute("patientName", patientName);
			request.setAttribute("patientId", patientId);
			request.setAttribute("type","");
			request.setAttribute("medications","");
			request.setAttribute("symptoms","");
			request.setAttribute("tooth","");
			request.setAttribute("comments","");
			
			request.getRequestDispatcher("dentistAddPatientRecord.jsp").forward(request, response);
			
		} else if (request.getParameter("submitNewRecord") != null) {
			
			String patientName = request.getParameter("patientName");
			String patientId = request.getParameter("patientId");
			String treatmentType = request.getParameter("type");
			String medications = request.getParameter("medications");
			String symptoms = request.getParameter("symptoms");
			String tooth = request.getParameter("tooth");
			String comments = request.getParameter("comments");
			
			request.setAttribute("patientName", patientName);
			request.setAttribute("patientId", patientId);
			
			if (!(treatmentType.equals("") || medications.equals("") || symptoms.equals("") || tooth.equals("") || comments.equals(""))) {
				
				connection.addNewPatientRecord(treatmentType, medications, symptoms, tooth, comments, patientId, id);
				
				ArrayList<Record> patientRecords = connection.getPatientRecords(patientId);
				
				request.setAttribute("patientRecords", patientRecords);
				
				request.getRequestDispatcher("dentistViewPatientRecords.jsp").forward(request, response);
				
			} else {
				
				request.setAttribute("patientName", patientName);
				request.setAttribute("patientId", patientId);
				request.setAttribute("type",treatmentType);
				request.setAttribute("medications",medications);
				request.setAttribute("symptoms",symptoms);
				request.setAttribute("tooth",tooth);
				request.setAttribute("comments",comments);
				
				request.getRequestDispatcher("dentistAddPatientRecord.jsp").forward(request, response);
				
			}
			
		}
		return;
	}

}
