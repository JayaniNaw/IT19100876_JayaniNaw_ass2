package com;

import java.sql.*;

public class Product {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/productservice", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readProduct() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>product Code</th> <th>product Name</th><th>product version</th>"
					+ "<th>product Description</th> <th>product price</th><th>User NIC</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from products";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String productId = Integer.toString(rs.getInt("productId"));
				String productCode = rs.getString("productCode");
				String productName = rs.getString("productName");
				String version = rs.getString("version");
				String description = rs.getString("description");
				String amount = Double.toString(rs.getDouble("amount"));
				String nic = rs.getString("nic");
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + productId
						+ "'>" + productCode + "</td>";
				output += "<td>" + productName + "</td>";
				output += "<td>" + version + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + nic + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"
						+ productId + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the products.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertProduct(String productCode, String productName, String version, String description,
			String amount, String nic) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into products (productId,productCode,productName,version,description,amount,nic)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, productCode);
			preparedStmt.setString(3, productName);
			preparedStmt.setString(4, version);
			preparedStmt.setString(5, description);
			preparedStmt.setDouble(6, Double.parseDouble(amount));
			preparedStmt.setString(7, nic);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readProduct();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the product.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateProduct(String productId, String productCode, String productName, String version,
			String description, String amount, String nic) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE products SET productCode=?,productName=?,version=?,description=?, amount=?, nic=? WHERE productId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, productCode);
			preparedStmt.setString(2, productName);
			preparedStmt.setString(3, version);
			preparedStmt.setString(4, description);
			preparedStmt.setDouble(5, Double.parseDouble(amount));
			preparedStmt.setString(6, nic);
			preparedStmt.setInt(7, Integer.parseInt(productId));
//execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readProduct();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the products.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteProduct(String productId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
// create a prepared statement
			String query = "delete from products where productId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, Integer.parseInt(productId));
// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readProduct();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the product.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}