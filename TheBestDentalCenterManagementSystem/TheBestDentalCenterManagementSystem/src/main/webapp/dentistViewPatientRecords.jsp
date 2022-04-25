<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Record"%>
<!DOCTYPE html>
<html>
	<head>
		<% 
			String id = (String) request.getAttribute("id");
			String fname = (String) request.getAttribute("fname");
			String patientName = (String) request.getAttribute("patientName");
			String patientId = (String) request.getAttribute("patientId");
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
			<h1><%=patientName %>'s dental records</h1>
			<table>
				<tr>
					<th>Treatment type</th>
					<th>Medications</th>
					<th>Symptoms</th>
					<th>Tooth</th>
					<th>Comments</th>
					<th>Dentist</th>
				</tr>
				<% 
					Object obj1 = request.getAttribute("patientRecords");
					ArrayList<Record> recordsList = null;
					if (obj1 instanceof ArrayList) {
						recordsList = (ArrayList<Record>) obj1;
						for (Record record: recordsList) {
							String type = record.getType();
							String medications = record.getMedications();
							String symptoms = record.getSymptoms();
							String tooth = record.getTooth();
							String comments = record.getComments();
							String dentist = record.getEmployee(); %>
							<tr>
								<td><%=type %></td>
								<td><%=medications %></td>
								<td><%=symptoms %></td>
								<td><%=tooth %></td>
								<td><%=comments %></td>
								<td><%=dentist %></td>
							</tr>
							<%
						}
					}
				%>
			</table>
			<form action="dentistNavServlet" method="post">
				<input type="hidden" name="id" value="<%=id %>"/>
				<input type="hidden" name="fname" value="<%=fname %>"/>
				<input type="hidden" name="patientName" value="<%=patientName %>"/>
				<input type="hidden" name="patientId" value="<%=patientId %>"/>
				<input type="submit" name="addPatientRecord" value="Add patient record"/>
			</form>
		</div>
	</body>
</html>