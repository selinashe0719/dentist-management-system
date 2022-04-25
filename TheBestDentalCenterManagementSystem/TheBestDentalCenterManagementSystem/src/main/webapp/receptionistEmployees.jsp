<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Branch"%>
<%@page import="entities.Employee"%>
<!DOCTYPE html>
<html>
	<head>
		<% 
			String id = (String) request.getAttribute("id");
			String fname = (String) request.getAttribute("fname");
			String selectedBranch = (String) request.getAttribute("selectedBranch");
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
			<h1>Employees</h1>
			<form action="receptionistNavServlet" method="post">
				<label for="branches">Branch: </label>
				<select name="branches">
					<option value="All"<% if (selectedBranch.equals("All")) {out.print(" selected");} %>>All</option>
					<% 
						ArrayList<Branch> branches = (ArrayList<Branch>) request.getAttribute("branchesList");
						for (Branch branch: branches) {%>
							<option value="<%=branch.getCity() %>"<% if (selectedBranch.equals(branch.getCity())) {out.print(" selected");} %>><%=branch.getCity() %></option>
						<%}
					%>
				</select>
				<input type="submit" name="selectBranch" value="Search"/>
			</form>
			<table>
				<tr>
					<th>Name</th>
					<th>Role</th>
					<th>Type</th>
					<th>Address</th>
					<th>SSN</th>
					<th>Salary</th>
					<th>Branch</th>
					<th>Edit?</th>
				</tr>
				<% 
					Object obj1 = request.getAttribute("employeesList");
					ArrayList<Employee> employeesList = null;
					if (obj1 instanceof ArrayList) {
						employeesList = (ArrayList<Employee>) obj1;
						for (Employee employee: employeesList) {
							String name = employee.getFullName();
							String role = employee.getRole();
							String type = employee.getType();
							String address = employee.getAddress();
							String ssn = employee.getSSN();
							String salary = employee.getSalary();
							String branch = employee.getBranch(); %>
							<tr>
								<td><%=name %></td>
								<td><%=role %></td>
								<td><%=type %></td>
								<td><%=address %></td>
								<td><%=ssn %></td>
								<td style="text-align: right;">$<%=salary %></td>
								<td><%=branch %></td>
								<td>
									<form action="receptionistNavServlet" method="post">
										<input type="hidden" name="id" value="<%=id %>"/>
										<input type="hidden" name="fname" value="<%=fname %>"/>
										<input type="hidden" name="dentistId" value="<%=employee.getId() %>"/>
										<input type="submit" name="editDentistData" value="Edit"/>
									</form>
								</td>
							</tr>
						<%
						}
					}
				%>
			</table>
			<form action="receptionistNavServlet" method="post">
				<input type="hidden" name="id" value="<%=id %>"/>
				<input type="hidden" name="fname" value="<%=fname %>"/>
				<input type="submit" name="newEmployee" value="Add new dentist"/>
			</form>
		</div>
	</body>
</html>