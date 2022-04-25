<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.ProcedureType"%>
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
			<h1>Procedure types</h1>
			<table>
				<tr>
					<th>Code</th>
					<th>Name</th>
					<th>Description</th>
				</tr>
				<% 
					Object obj1 = request.getAttribute("procedureTypes");
					ArrayList<ProcedureType> procedureTypes = null;
					if (obj1 instanceof ArrayList) {
						procedureTypes = (ArrayList<ProcedureType>) obj1;
						for (ProcedureType procedureType: procedureTypes) {
							String code = procedureType.getCode();
							String name = procedureType.getName();
							String description = procedureType.getDescription(); %>
							<tr>
								<td><%=code %></td>
								<td><%=name %></td>
								<td><%=description %></td>
							</tr>
							<%
						}
					}
				%>
			</table>
		</div>
	</body>
</html>