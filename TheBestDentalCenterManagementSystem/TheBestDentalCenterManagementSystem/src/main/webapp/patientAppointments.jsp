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
			<form class='navbar' action='patientNavServlet' method='post'>
				<input type="hidden" name="id" value="<%=id%>"/>
				<input type="hidden" name="fname" value="<%=fname %>"/>
				<button type="submit" name="Home">Home</button>
				<button type="submit" name="Appointments">Appointments</button>
				<button type="submit" name="Dental records">Dental records</button>
				<button type="submit" name="Logout" style='float:right'>Logout</button>
			</form>
		</div>
		<div class="mainScreen">
			<h1><%=fname %>'s Scheduled Appointments</h1>
			<table>
				<tr>
					<th>Date</th>
					<th>Start time</th>
					<th>End time</th>
					<th>Dentist</th>
					<th>Type</th>
					<th>Room</th>
				</tr>
				<% 
					Object obj1 = request.getAttribute("futureAppointments");
					ArrayList<Appointment> futureAppointmentList = null;
					if (obj1 instanceof ArrayList) {
						futureAppointmentList = (ArrayList<Appointment>) obj1;
						for (Appointment appointment: futureAppointmentList) {
							String aDate = appointment.getDate();
							String aStartTime = appointment.getStartTime();
							String aEndTime = appointment.getEndTime();
							String aDentist = appointment.getDentistName();
							String aType = appointment.getType();
							String room = appointment.getRoom(); %>
							<tr>
								<td><%=aDate %></td>
								<td><%=aStartTime %></td>
								<td><%=aEndTime %></td>
								<td><%=aDentist %></td>
								<td><%=aType %></td>
								<td><%=room %></td>
							</tr>
							<%
						}
					}
				%>
			</table>
			<h1><%=fname %>'s Past Appointments</h1>
			<table>
				<tr>
					<th>Date</th>
					<th>Start time</th>
					<th>End time</th>
					<th>Dentist</th>
					<th>Type</th>
					<th>Status</th>
				</tr>
				<% 
					Object obj2 = request.getAttribute("pastAppointments");
					ArrayList<Appointment> pastAppointmentList = null;
					if (obj2 instanceof ArrayList) {
						pastAppointmentList = (ArrayList<Appointment>) obj2;
						for (Appointment appointment: pastAppointmentList) {
							String aDate = appointment.getDate();
							String aStartTime = appointment.getStartTime();
							String aEndTime = appointment.getEndTime();
							String aDentist = appointment.getDentistName();
							String aType = appointment.getType();
							String aStatus = appointment.getStatus(); %>
							<tr>
								<td><%=aDate %></td>
								<td><%=aStartTime %></td>
								<td><%=aEndTime %></td>
								<td><%=aDentist %></td>
								<td><%=aType %></td>
								<td><%=aStatus %></td>
							</tr>
							<%
						}
					}
				%>
			</table>
		</div>
	</body>
</html>