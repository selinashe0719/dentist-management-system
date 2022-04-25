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
import entities.Branch;
import entities.Employee;
import entities.Patient;
import entities.ProcedureType;

/**
 * Servlet implementation class ReceptionistNavServlet
 */
@WebServlet("/receptionistNavServlet")
public class ReceptionistNavServlet extends HttpServlet {
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
			
			request.getRequestDispatcher("receptionistHome.jsp").forward(request, response);
		
		// If the user pressed the Menu->Patients button
		} else if (request.getParameter("Patients") != null) {
			
			ArrayList<Patient> patients = connection.getAllPatients();
			
			request.setAttribute("patientsList", patients);
			
			request.getRequestDispatcher("receptionistPatients.jsp").forward(request, response);
		
		// If the user pressed the Menu->Employees button
		} else if (request.getParameter("Employees") != null) {
			
			ArrayList<Branch> branches = connection.getAllBranches();
			ArrayList<Employee> employees = connection.getAllEmployees();
			
			request.setAttribute("branchesList", branches);
			request.setAttribute("selectedBranch", "All");
			request.setAttribute("employeesList", employees);
			
			request.getRequestDispatcher("receptionistEmployees.jsp").forward(request, response);
		
		// If the user selected a filter for the employee branch
		} else if (request.getParameter("selectBranch") != null) {
			
			String selectedBranch = request.getParameter("branches");
			
			ArrayList<Branch> branches = connection.getAllBranches();
			ArrayList<Employee> employees;
			if (selectedBranch.equals("All")){
				employees = connection.getAllEmployees();
			} else {
				employees = connection.getEmployeesFromBranch(selectedBranch);
			}
			
			request.setAttribute("branchesList", branches);
			request.setAttribute("selectedBranch", selectedBranch);
			request.setAttribute("employeesList", employees);
			
			request.getRequestDispatcher("receptionistEmployees.jsp").forward(request, response);
			
		// If the user pressed the Menu->Appointments button
		} else if (request.getParameter("Appointments") != null) {
			
			ArrayList<Appointment> appointments = connection.getAllAppointments();
			
			request.setAttribute("appointments", appointments);
			
			request.getRequestDispatcher("receptionistAppointments.jsp").forward(request, response);
			
		// If the user pressed the Menu->Procedure types button
		} else if (request.getParameter("Procedure types") != null) {
			
			ArrayList<ProcedureType> procedureTypes = connection.getAllProcedureTypes();
			
			request.setAttribute("procedureTypes", procedureTypes);
			
			request.getRequestDispatcher("receptionistProcedureTypes.jsp").forward(request, response);
		
		// If the user pressed the Menu->Logout button
		} else if (request.getParameter("Logout") != null) {
			
			request.getRequestDispatcher("index.html").forward(request, response);
			
		// If the user pressed any "Edit" button on the patients page
		} else if (request.getParameter("editPatientData") != null) {

			String patientId = request.getParameter("patientId");
			
			Patient patient = connection.getPatient(patientId);
			
			request.setAttribute("patientId", Integer.parseInt(patientId));
			request.setAttribute("patientDetails", patient);
			
			request.getRequestDispatcher("receptionistNewPatient.jsp").forward(request, response);
			
		} else if (request.getParameter("newPatient") != null) {
			
			request.setAttribute("patientId",-1);
			
			request.getRequestDispatcher("receptionistNewPatient.jsp").forward(request, response);
			
		} else if (request.getParameter("submitPatient") != null) {
		
			String pid = request.getParameter("patientId");
			String pusername = request.getParameter("username");
			String ppassword = request.getParameter("password");
			String pfname = request.getParameter("pfname");
			String pmname = request.getParameter("mname");
			String plname = request.getParameter("lname");
			String pssn = request.getParameter("ssn");
			String pgender = request.getParameter("gender");
			String pbirthday = request.getParameter("birthday");
			String phouseNumber = request.getParameter("houseNumber");
			String pstreet = request.getParameter("street");
			String pcity = request.getParameter("city");
			String pprovince = request.getParameter("province");
			
			if ( !(pusername.equals("") || ppassword.equals("") || pfname.equals("") || plname.equals("") || pssn.length() != 9 || 
					pgender.equals("") || pbirthday.equals("") || phouseNumber.equals("") || pstreet.equals("") || pcity.equals("") ||
					pprovince.equals(""))) {
				
				if (Integer.parseInt(pid) == -1) {
					
					connection.addNewPatient(pusername, ppassword, pfname, pmname, plname, pssn, pgender, pbirthday, phouseNumber, pstreet, pcity, pprovince);
					
					ArrayList<Patient> patients = connection.getAllPatients();
					
					request.setAttribute("patientsList", patients);
					
					request.getRequestDispatcher("receptionistPatients.jsp").forward(request, response);
					
				} else {
					
					connection.updatePatient(pid, pusername, ppassword, pfname, pmname, plname, pssn, pgender, pbirthday, phouseNumber, pstreet, pcity, pprovince);
					
					ArrayList<Patient> patients = connection.getAllPatients();
					
					request.setAttribute("patientsList", patients);
					
					request.getRequestDispatcher("receptionistPatients.jsp").forward(request, response);
					
				}
				
			} else {
				
				Patient patient = new Patient(pid, pusername, ppassword, pfname, pmname, plname, pgender, pbirthday, phouseNumber, pstreet, pcity, pprovince, pssn);
				
				request.setAttribute("patientId", Integer.parseInt(pid));
				request.setAttribute("patientDetails", patient);
				
				request.getRequestDispatcher("receptionistNewPatient.jsp").forward(request, response);
				
			}
			
		// If the user pressed any "Edit" button on the employees page
		} else if (request.getParameter("editDentistData") != null) {

			String dentistId = request.getParameter("dentistId");
			
			Employee dentist = connection.getDentist(dentistId);
			ArrayList<Branch> branches = connection.getAllBranches();		
			
			request.setAttribute("employeeId", Integer.parseInt(dentistId));
			request.setAttribute("dentistDetails", dentist);
			request.setAttribute("branchesList", branches);
			
			request.getRequestDispatcher("receptionistNewEmployee.jsp").forward(request, response);
						
		} else if (request.getParameter("newEmployee") != null) {
						
			ArrayList<Branch> branches = connection.getAllBranches();
			
			request.setAttribute("employeeId",-1);
			request.setAttribute("branchesList", branches);		
						
			request.getRequestDispatcher("receptionistNewEmployee.jsp").forward(request, response);
						
		} else if (request.getParameter("submitEmployee") != null) {
					
			String pid = request.getParameter("employeeId");
			String pusername = request.getParameter("username");
			String ppassword = request.getParameter("password");
			String pfname = request.getParameter("efname");
			String pmname = request.getParameter("mname");
			String plname = request.getParameter("lname");
			String pssn = request.getParameter("ssn");
			String prole = request.getParameter("role");
			String psalary = request.getParameter("salary");
			String phouseNumber = request.getParameter("houseNumber");
			String pstreet = request.getParameter("street");
			String pcity = request.getParameter("city");
			String pprovince = request.getParameter("province");
			String branch = request.getParameter("branches");
						
			if ( !(pusername.equals("") || ppassword.equals("") || pfname.equals("") || plname.equals("") || pssn.length() != 9 || 
					prole.equals("") || psalary.equals("") || phouseNumber.equals("") || pstreet.equals("") || pcity.equals("") ||
					pprovince.equals(""))) {
							
				if (Integer.parseInt(pid) == -1) {
								
					connection.addNewEmployee(pusername, ppassword, pfname, pmname, plname, pssn, prole, psalary, phouseNumber, pstreet, pcity, pprovince, branch);
								
					ArrayList<Branch> branches = connection.getAllBranches();
					ArrayList<Employee> employees = connection.getAllEmployees();
					
					request.setAttribute("branchesList", branches);
					request.setAttribute("selectedBranch", "All");
					request.setAttribute("employeesList", employees);
					
					request.getRequestDispatcher("receptionistEmployees.jsp").forward(request, response);
								
				} else {
								
					connection.updateEmployee(pid, pusername, ppassword, pfname, pmname, plname, pssn, prole, psalary, phouseNumber, pstreet, pcity, pprovince, branch);
								
					ArrayList<Branch> branches = connection.getAllBranches();
					ArrayList<Employee> employees = connection.getAllEmployees();
					
					request.setAttribute("branchesList", branches);
					request.setAttribute("selectedBranch", "All");
					request.setAttribute("employeesList", employees);
					
					request.getRequestDispatcher("receptionistEmployees.jsp").forward(request, response);
								
				}
							
			} else {
							
				Employee employee = new Employee(pid, pusername, ppassword, pfname, pmname, plname, prole, "dentist", phouseNumber, pstreet, pcity, pprovince, pssn, psalary, branch);
				ArrayList<Branch> branches = connection.getAllBranches();
				
				request.setAttribute("employeeId", Integer.parseInt(pid));
				request.setAttribute("employeeDetails", employee);
				request.setAttribute("branchesList", branches);		
							
				request.getRequestDispatcher("receptionistNewEmployee.jsp").forward(request, response);
							
			}
			
		} else if (request.getParameter("editAppointment") != null) {

			String appointmentId = request.getParameter("appointmentId");
			
			ArrayList<Patient> patients = connection.getAllPatients();
			ArrayList<Employee> dentists = connection.getAllEmployees();
			Appointment appointment = connection.getAppointment(appointmentId);
			
			request.setAttribute("appointmentId", appointmentId);
			request.setAttribute("appointmentDetails", appointment);
			request.setAttribute("patientsList", patients);
			request.setAttribute("dentistsList", dentists);
						
			request.getRequestDispatcher("receptionistNewAppointment.jsp").forward(request, response);
									
		} else if (request.getParameter("schedulePatientAppointment") != null) {
									
			String patientId = request.getParameter("patientId");
			
			ArrayList<Patient> patients = connection.getAllPatients();
			ArrayList<Employee> dentists = connection.getAllEmployees();
						
			request.setAttribute("appointmentId","-1");
			request.setAttribute("patientId", patientId);
			request.setAttribute("patientsList", patients);
			request.setAttribute("dentistsList", dentists);
									
			request.getRequestDispatcher("receptionistNewAppointment.jsp").forward(request, response);
									
		} else if (request.getParameter("submitAppointment") != null) {
								
			String aid = request.getParameter("appointmentId");
			String apid = request.getParameter("patient");
			String adid = request.getParameter("dentist");
			String adate = request.getParameter("date");
			String astarttime = request.getParameter("starttime");
			String aendtime = request.getParameter("endtime");
			String atype = request.getParameter("type");
			String astatus = request.getParameter("status");
			String aroom = request.getParameter("room");
									
			if ( !(adate.equals("") || astarttime.equals("") || aendtime.equals("") || atype.equals("") || 
					aroom.equals(""))) {
										
				if (Integer.parseInt(aid) == -1) {
											
					connection.addNewAppointment(apid, adid, adate, astarttime, aendtime, atype, astatus, aroom);
											
					ArrayList<Appointment> appointments = connection.getAllAppointments();
					
					request.setAttribute("appointments", appointments);
					
					request.getRequestDispatcher("receptionistAppointments.jsp").forward(request, response);
											
				} else {
											
					connection.updateAppointment(aid, apid, adid, adate, astarttime, aendtime, atype, astatus, aroom);
											
					ArrayList<Appointment> appointments = connection.getAllAppointments();
					
					request.setAttribute("appointments", appointments);
					
					request.getRequestDispatcher("receptionistAppointments.jsp").forward(request, response);
											
				}
										
			} else {
										
				Appointment appointment = new Appointment(aid, adate, astarttime, aendtime, adid, "", apid, "", aroom, atype, astatus);
				
				String appointmentId = request.getParameter("appointmentId");
				
				ArrayList<Patient> patients = connection.getAllPatients();
				ArrayList<Employee> dentists = connection.getAllEmployees();
				
				request.setAttribute("appointmentId", appointmentId);
				request.setAttribute("appointmentDetails", appointment);
				request.setAttribute("patientsList", patients);
				request.setAttribute("dentistsList", dentists);
							
				request.getRequestDispatcher("receptionistNewAppointment.jsp").forward(request, response);
										
			}
		}
		return;
	}

}
