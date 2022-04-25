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
			String type = (String) request.getAttribute("type");
			String medications = (String) request.getAttribute("medications");
			String symptoms = (String) request.getAttribute("symptoms");
			String tooth = (String) request.getAttribute("tooth");
			String comments = (String) request.getAttribute("comments");
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
			<h1>New record for <%=patientName %></h1>
			<form action="dentistNavServlet" method="post">
				<input type="hidden" name="id" value="<%=id %>"/>
				<input type="hidden" name="fname" value="<%=fname %>"/>
				<input type="hidden" name="patientId" value="<%=patientId %>"/>
				<input type="hidden" name="patientName" value="<%=patientName %>"/>
				Treatment type: <input type="text" name="type" value="<%=type %>"/><br><br>
				Medications: <textarea name="medications" rows="5" columns="50" value="<%=medications %>"></textarea><br><br>
				Symptoms: <textarea name="symptoms" row="5" columns="50" value="<%=symptoms %>"></textarea><br><br>
				Tooth: <input type="text" name="tooth" value="<%=tooth %>"/><br><br>
				Comments: <textarea name="comments" row="5" columns="50" value="<%=comments %>"></textarea><br><br>
				<input type="submit" name="submitNewRecord" value="Save"/>
			</form>
		</div>
	</body>
</html>