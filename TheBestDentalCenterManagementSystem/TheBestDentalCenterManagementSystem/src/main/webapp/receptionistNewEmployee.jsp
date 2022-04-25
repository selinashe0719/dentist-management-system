<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Employee" %>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Branch"%>
<!DOCTYPE html>
<html>
	<head>
		<% 
			String id = (String) request.getAttribute("id");
			String fname = (String) request.getAttribute("fname");
			Integer employeeId = (Integer) request.getAttribute("employeeId");
			
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
			<% if (request.getAttribute("dentistDetails") == null ) { %>
				<h1>New employee</h1>
				<form action="receptionistNavServlet" method="post">
					Login information: <input type="text" name="username" placeholder="Username"/>
					<input type="text" name="password" placeholder="password"/>
					<br><br>
					Name: <input type="text" name="efname" placeholder="First name"/>
					<input type="text" name="mname" placeholder="Middle name"/>
					<input type="text" name="lname" placeholder="Last name"/>
					<br><br>
					SSN: <input type="text" name="ssn" />
					<br><br>
					Role: <input type="text" name="role" />
					<br><br>
					Salary: <input type="number" name="salary" />
					<br><br>
					Address: <input type="number" name="houseNumber" placeholder="House number"/>
					<input type="text" name="street" placeholder="Street"/>
					<input type="text" name="city" placeholder="City"/>
					<input type="text" name="province" placeholder="Province"/>
					<br><br>
					Branch: 
					<select name="branches">
						<% 
							ArrayList<Branch> branches = (ArrayList<Branch>) request.getAttribute("branchesList");
							for (Branch branch: branches) {%>
								<option value="<%=branch.getCity() %>"><%=branch.getCity() %></option>
							<%}
						%>
					</select>
					<br><br>
					<input type="hidden" name="employeeId" value=-1 />
					<input type="hidden" name="id" value="<%=id %>"/>
					<input type="hidden" name="fname" value="<%=fname %>"/>
					<input type="submit" name="submitEmployee" value="Save"/>
				</form>
			<%} else { %>
				<h1>Edit employee</h1>
				<% Employee employee = (Employee) request.getAttribute("dentistDetails");%>
				<form action="receptionistNavServlet" method="post">
					Login information: <input type="text" name="username" placeholder="Username" value="<%=employee.getUsername() %>"/>
					<input type="text" name="password" placeholder="password" value="<%=employee.getPassword() %>"/>
					<br><br>
					Name: <input type="text" name="efname" placeholder="First name" value="<%=employee.getFirstName() %>"/>
					<input type="text" name="mname" placeholder="Middle name" value="<%=employee.getMiddleName() %>"/>
					<input type="text" name="lname" placeholder="Last name" value="<%=employee.getLastName() %>"/>
					<br><br>
					SSN: <input type="text" name="ssn" value="<%=employee.getSSN() %>"/>
					<br><br>
					Role: <input type="text" name="role" value="<%=employee.getRole() %>"/>
					<br><br>
					Salary: <input type="number" name="salary" value="<%=employee.getSalary() %>"/>
					<br><br>
					Address: <input type="number" name="houseNumber" placeholder="House number" value="<%=employee.getHouseNumber() %>"/>
					<input type="text" name="street" placeholder="Street" value="<%=employee.getStreet() %>"/>
					<input type="text" name="city" placeholder="City" value="<%=employee.getCity() %>"/>
					<input type="text" name="province" placeholder="Province" value="<%=employee.getProvince() %>"/>
					<br><br>
					Branch: 
					<select name="branches">
						<% 
							ArrayList<Branch> branches = (ArrayList<Branch>) request.getAttribute("branchesList");
							for (Branch branch: branches) {%>
								<option value="<%=branch.getCity() %>"<% if (employee.getBranch().equals(branch.getCity())) {out.print(" selected");} %>><%=branch.getCity() %></option>
							<%}
						%>
					</select>
					<br><br>
					<input type="hidden" name="employeeId" value="<%=employeeId %>" />
					<input type="hidden" name="id" value="<%=id %>"/>
					<input type="hidden" name="fname" value="<%=fname %>"/>
					<input type="submit" name="submitEmployee" value="Save"/>
				</form>
			<%} %>
	</body>
</html>