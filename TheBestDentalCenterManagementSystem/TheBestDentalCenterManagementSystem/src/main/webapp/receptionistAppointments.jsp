<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Appointment"%>
<!DOCTYPE html>
<html>
	<head>
		<% 
			String id = (String) request.getAttribute("id");
			String fname = (String) request.getAttribute("fname");
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
			<h1>All Scheduled Appointments</h1>
			<table>
				<tr>
					<th>Date</th>
					<th>Start time</th>
					<th>End time</th>
					<th>Patient</th>
					<th>Dentist</th>
					<th>Type</th>
					<th>Status</th>
					<th>Room</th>
					<th>Edit?</th>
				</tr>
				<% 
					Object obj1 = request.getAttribute("appointments");
					ArrayList<Appointment> futureAppointmentList = null;
					if (obj1 instanceof ArrayList) {
						futureAppointmentList = (ArrayList<Appointment>) obj1;
						for (Appointment appointment: futureAppointmentList) {
							String aDate = appointment.getDate();
							String aStartTime = appointment.getStartTime();
							String aEndTime = appointment.getEndTime();
							String aPatient = appointment.getPatientName();
							String aDentist = appointment.getDentistName();
							String aType = appointment.getType();
							String aPatientId = appointment.getPatientId();
							String status = appointment.getStatus();
							String room = appointment.getRoom(); %>
							<tr>
								<td><%=aDate %></td>
								<td><%=aStartTime %></td>
								<td><%=aEndTime %></td>
								<td><%=aPatient %></td>
								<td><%=aDentist %></td>
								<td><%=aType %></td>
								<td><%=status %></td>
								<td><%=room %></td>
								<td>
									<form action='receptionistNavServlet' method='post'>
										<input type="hidden" name="id" value="<%=id %>"/>
										<input type="hidden" name="fname" value="<%=fname %>"/>
										<input type="hidden" name="appointmentId" value="<%=appointment.getId() %>"/>
										<input type="submit" name="editAppointment" value="Edit"/>
									</form>
								</td>
							</tr>
							<%
						}
					}
				%>
			</table>
		</div>
	</body>
</html>