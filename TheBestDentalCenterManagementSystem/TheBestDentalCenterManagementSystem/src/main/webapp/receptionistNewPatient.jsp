<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<% 
			String id = (String) request.getAttribute("id");
			String fname = (String) request.getAttribute("fname");
			Integer patientId = (Integer) request.getAttribute("patientId");
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
			<% if (request.getAttribute("patientDetails") == null ) { %>
				<h1>New patient</h1>
				<form action="receptionistNavServlet" method="post">
					Login information: <input type="text" name="username" placeholder="Username"/>
					<input type="text" name="password" placeholder="password"/>
					<br><br>
					Name: <input type="text" name="pfname" placeholder="First name"/>
					<input type="text" name="mname" placeholder="Middle name"/>
					<input type="text" name="lname" placeholder="Last name"/>
					<br><br>
					SSN: <input type="text" name="ssn" />
					<br><br>
					Gender: <input type="radio" name="gender" id="M" value="M" checked/><label for="M">M</label>
					<input type="radio" name="gender" id="F" value="F"/><label for="F">F</label>
					<br><br>
					Birthday: <input type="date" name="birthday"/>
					<br><br>
					Address: <input type="number" name="houseNumber" placeholder="House number"/>
					<input type="text" name="street" placeholder="Street"/>
					<input type="text" name="city" placeholder="City"/>
					<input type="text" name="province" placeholder="Province"/>
					<br><br>
					<input type="hidden" name="patientId" value=-1 />
					<input type="hidden" name="id" value="<%=id %>"/>
					<input type="hidden" name="fname" value="<%=fname %>"/>
					<input type="submit" name="submitPatient" value="Save"/>
				</form>
			<%} else { %>
				<h1>Edit patient</h1>
				<% Patient patient = (Patient) request.getAttribute("patientDetails");%>
				<form action="receptionistNavServlet" method="post">
					Login information: <input type="text" name="username" placeholder="Username" value="<%=patient.getUsername() %>"/>
					<input type="text" name="password" placeholder="password" value="<%=patient.getPassword() %>"/>
					<br><br>
					Name: <input type="text" name="pfname" placeholder="First name" value="<%=patient.getFirstName() %>"/>
					<input type="text" name="mname" placeholder="Middle name" value="<%=patient.getMiddleName() %>"/>
					<input type="text" name="lname" placeholder="Last name" value="<%=patient.getLastName() %>"/>
					<br><br>
					SSN: <input type="text" name="ssn" value="<%=patient.getSSN() %>"/>
					<br><br>
					Gender: <input type="radio" name="gender" id="M" value="M"<% if (patient.getGender().equals("M")) out.print(" checked"); %>><label for="M">M</label>
					<input type="radio" name="gender" id="F" value="F"<% if (patient.getGender().equals("F")) out.print(" checked"); %>/><label for="F">F</label>
					<br><br>
					Birthday: <input type="date" name="birthday" value="<%=patient.getBirthday() %>"/>
					<br><br>
					Address: <input type="number" name="houseNumber" placeholder="House number" value="<%=patient.getHouseNumber() %>"/>
					<input type="text" name="street" placeholder="Street" value="<%=patient.getStreet() %>"/>
					<input type="text" name="city" placeholder="City" value="<%=patient.getCity() %>"/>
					<input type="text" name="province" placeholder="Province" value="<%=patient.getProvince() %>"/>
					<br><br>
					<input type="hidden" name="patientId" value="<%=patientId %>" />
					<input type="hidden" name="id" value="<%=id %>"/>
					<input type="hidden" name="fname" value="<%=fname %>"/>
					<input type="submit" name="submitPatient" value="Save"/>
				</form>
			<%} %>
	</body>
</html>