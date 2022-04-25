<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Patient" %>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Employee" %>
<%@page import="entities.Appointment" %>
<!DOCTYPE html>
<html>
	<head>
		<% 
			String id = (String) request.getAttribute("id");
			String fname = (String) request.getAttribute("fname");
			String appointmentId = (String) request.getAttribute("appointmentId");
			ArrayList<Patient> patientsList = (ArrayList<Patient>) request.getAttribute("patientsList");
			ArrayList<Employee> dentistsList = (ArrayList<Employee>) request.getAttribute("dentistsList");
		%>
		<title>Home</title>
		<style>
			body {
				margin: 0;
			}
			.navbar {
				overflow: hidden;
				background-color: #333;
			}
			.navbar button {
				float: left;
				font-size: 16px;
				color: white;
				text-align: center;
				padding: 14px 16px;
				text-decoration: none;
				border-right: 1px solid #bbb;
				background-color: #333;
			}
			.navbar button:hover {
				background-color: #111;
			}
			.mainScreen {
				margin: 20px;
			}
			th, td {
				text-align: left;
				padding-bottom: 5px;
				padding-right: 30px;
			}
		</style>
	</head>
	<body>
		<div id="topNavBar">
			<form class='navbar' action='receptionistNavServlet' method='post'>
				<input type="hidden" name="id" id="id" value="<%=id %>"/>
				<input type="hidden" name="fname" value="<%=fname %>"/>
				<button type="submit" name="Home">Home</button>
				<button type="submit" name="Patients">Patients</button>
				<button type="submit" name="Employees">Employees</button>
				<button type="submit" name="Appointments">Appointments</button>
				<button type="submit" name="Procedure types">Procedure types</button>
				<button type="submit" name="Logout" style='float:right'>Logout</button>
			</form>
		</div>
		<div class="mainScreen">
			<% if (request.getAttribute("appointmentDetails") == null ) {
				String patientId = (String) request.getAttribute("patientId"); %>
				<h1>New appointment</h1>
				<form action="receptionistNavServlet" method="post">
					Patient: 
					<select name="patient">
						<% 
							for (Patient patient: patientsList) {%>
								<option value="<%=patient.getId() %>"<% if (patientId.equals(patient.getId())) {out.print(" selected");} %>><%=patient.getFullName() %></option>
							<%}
						%>
					</select>
					<br><br>
					Dentist:
					<select name="dentist">
						<% 
							for (Employee dentist: dentistsList) {%>
								<option value="<%=dentist.getId() %>"><%=dentist.getFullName() %></option>
							<%}
						%>
					</select>
					<br><br>
					Date: <input type="date" name="date"/>
					<br><br>
					Start time: <input type="time" name="starttime"/>
					<br><br>
					End time: <input type="time" name="endtime"/>
					<br><br>
					Type: <input type="text" name="type"/>
					<br><br>
					Status: <input type="radio" name="status" id="completed" value="completed"/><label for="completed">Completed</label>
					<input type="radio" name="status" id="upcoming" value="upcoming" checked/><label for="upcoming">Upcoming</label>
					<input type="radio" name="status" id="cancelled" value="cancelled"/><label for="cancelled">Cancelled</label>
					<br><br>
					Room: <input type="number" name="room"/>
					<br><br>
					<input type="hidden" name="appointmentId" value="<%=appointmentId %>"/>
					<input type="hidden" name="id" value="<%=id %>"/>
					<input type="hidden" name="fname" value="<%=fname %>"/>
					<input type="submit" name="submitAppointment" value="Save"/>
				</form>
			<%} else {%>
				<h1>Edit appointment</h1>
				<% Appointment appointment = (Appointment) request.getAttribute("appointmentDetails"); %>
				<form action="receptionistNavServlet" method="post">
					Patient: 
					<select name="patient">
						<% 
							for (Patient patient: patientsList) {%>
								<option value="<%=patient.getId() %>"<% if (appointment.getPatientId().equals(patient.getId())) {out.print(" selected");} %>><%=patient.getFullName() %></option>
							<%}
						%>
					</select>
					<br><br>
					Dentist:
					<select name="dentist">
						<% 
							for (Employee dentist: dentistsList) {%>
								<option value="<%=dentist.getId() %>"<% if (appointment.getDentistId().equals(dentist.getId())) {out.print(" selected");} %>><%=dentist.getFullName() %></option>
							<%}
						%>
					</select>
					<br><br>
					Date: <input type="date" name="date" value="<%=appointment.getDate() %>"/>
					<br><br>
					Start time: <input type="time" name="starttime" value="<%=appointment.getStartTime() %>"/>
					<br><br>
					End time: <input type="time" name="endtime" value="<%=appointment.getEndTime() %>"/>
					<br><br>
					Type: <input type="text" name="type" value="<%=appointment.getType() %>"/>
					<br><br>
					Status: <input type="radio" name="status" id="completed" value="completed"<% if (appointment.getStatus().equals("completed")) {out.print(" checked");} %>/><label for="completed">Completed</label>
					<input type="radio" name="status" id="upcoming" value="upcoming"<% if (appointment.getStatus().equals("upcoming")) {out.print(" checked");} %>/><label for="upcoming">Upcoming</label>
					<input type="radio" name="status" id="cancelled" value="cancelled"<% if (appointment.getStatus().equals("cancelled")) {out.print(" checked");} %>/><label for="cancelled">Cancelled</label>
					<br><br>
					Room: <input type="number" name="room" value="<%=appointment.getRoom() %>"/>
					<br><br>
					<input type="hidden" name="appointmentId" value="<%=appointmentId %>"/>
					<input type="hidden" name="id" value="<%=id %>"/>
					<input type="hidden" name="fname" value="<%=fname %>"/>
					<input type="submit" name="submitAppointment" value="Save"/>
				</form>
			<%} %>
	</body>
</html>