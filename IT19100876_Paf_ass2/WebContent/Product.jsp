<%@page import="com.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/products.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Products Management</h1>
				<form id="formItem" name="formItem">
					Product code: <input id="productCode" name="productCode"
						type="text" class="form-control form-control-sm"> <br>
					Product name: <input id="productName" name="productName"
						type="text" class="form-control form-control-sm"> <br>
					Product version: <input id="version" name="version" type="text"
						class="form-control form-control-sm"> <br> Product
					description: <input id="description" name="description" type="text"
						class="form-control form-control-sm"> <br> Product
					Price <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> <br> User NIC <input
						id="nic" name="nic" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
						Product itemObj = new Product();
					out.print(itemObj.readProduct());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>