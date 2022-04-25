<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Patient"%>
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
			<form class='navbar' action='dentistNavServlet' method='post'>
				<input type="hidden" name="id" id="id" value="<%=id %>"/>
				<input type="hidden" name="fname" value="<%=fname %>"/>
				<button type="submit" name="Home">Home</button>
				<button type="submit" name="Appointments">Appointments</button>
				<button type="submit" name="Patients">Patients</button>
				<button type="submit" name="Procedure types">Procedure types</button>
				<button type="submit" name="Logout" style='float:right'>Logout</button>
			</form>
		</div>
		<div class="mainScreen">
			<h1>Patients</h1>
			<table>
				<tr>
					<th>Name</th>
					<th>Gender</th>
					<th>Birthday</th>
					<th>Address</th>
					<th>SSN</th>
					<th>View records?</th>
				</tr>
				<% 
					Object obj1 = request.getAttribute("patientsList");
					ArrayList<Patient> patientsList = null;
					if (obj1 instanceof ArrayList) {
						patientsList = (ArrayList<Patient>) obj1;
						for (Patient patient: patientsList) {
							String name = patient.getFullName();
							String gender = patient.getGender();
							String birthday = patient.getBirthday();
							String address = patient.getAddress();
							String ssn = patient.getSSN(); %>
							<tr>
								<td><%=name %></td>
								<td><%=gender %></td>
								<td><%=birthday %></td>
								<td><%=address %></td>
								<td><%=ssn %></td>
								<td>
									<form action='dentistNavServlet' method='post'>
										<input type="hidden" name="id" value="<%=id %>"/>
										<input type="hidden" name="fname" value="<%=fname %>"/>
										<input type="hidden" name="patientName" value="<%=name %>"/>
										<input type="hidden" name="patientId" value="<%=patient.getId() %>"/>
										<input type="submit" name="viewPatientData" value="View"/>
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