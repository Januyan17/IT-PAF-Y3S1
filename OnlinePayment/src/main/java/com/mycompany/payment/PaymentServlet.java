package com.mycompany.payment;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class PaymentServlet extends HttpServlet {
	Connection con = connect();
	
	
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electropayment", "root", "");

			// for testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.print("done");
		return con;
	}
	
	
	
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
			try {
				Statement stmt = con.createStatement();
				String sql = "select * from payments";
				ResultSet rs = stmt.executeQuery(sql);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");       
			    response.setCharacterEncoding("UTF-8");
			    JsonArray array = new JsonArray();

			    while(rs.next()) {
			    	   JsonObject record = new JsonObject();
			    	   record.add("id", new Gson().toJsonTree(rs.getInt("id")));
			    	   record.add("name", new Gson().toJsonTree(rs.getString("name")));
			    	   record.add("card_no", new Gson().toJsonTree(rs.getString("card_no")));
			    	   record.add("expiry_date",new Gson().toJsonTree( rs.getDate("expiry_date")));
			    	   record.add("cvc",new Gson().toJsonTree( rs.getString("cvc")));
			    	   array.add(record);
			    	}
                 out.print(array);
				
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
     
    }
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
	try {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");       
	    response.setCharacterEncoding("UTF-8");
		Connection con = connect();
		String requestData = request.getReader().lines().collect(Collectors.joining());
		  JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(requestData);
		  JsonObject rootObject = jsonElement.getAsJsonObject();
		  String name = rootObject.get("name").getAsString(); // get property "message"
		  String cvc = rootObject.get("cvc").getAsString();
		  String expiry_date = rootObject.get("expiry_date").getAsString();
		  String card_no = rootObject.get("card_no").getAsString();

		  String sql = "insert into payments (name,card_no,cvc,expiry_date) VALUE ('"+name+"','"+card_no+"','"+cvc+"','"+expiry_date+"')";
	      PreparedStatement preparedStmt = con.prepareStatement(sql);
	     
		if(preparedStmt.execute());

         out.print("successfully inserted the records");
		
		
		} catch (SQLException e) {
			PrintWriter out = response.getWriter();
			 out.print( e);
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


   }
    public void doPut(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
	try {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");       
	    response.setCharacterEncoding("UTF-8");
		Connection con = connect();
		String id = request.getPathInfo().split("/")[1];
		String requestData = request.getReader().lines().collect(Collectors.joining());
	    JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(requestData);
		 JsonObject rootObject = jsonElement.getAsJsonObject();
		  String name = rootObject.get("name").getAsString(); // get property "message"
		  String cvc = rootObject.get("cvc").getAsString();
		  String expiry_date = rootObject.get("expiry_date").getAsString();
		  String card_no = rootObject.get("card_no").getAsString();

		  String sql = "UPDATE  payments set name='"+name+"',card_no='"+card_no+"',cvc='"+cvc+"',expiry_date='"+expiry_date+"' where id ='"+id+"'";
	      PreparedStatement preparedStmt = con.prepareStatement(sql);
	     
		if(preparedStmt.execute());

         out.print("successfully updated the records");
		
		
		} catch (SQLException e) {
			PrintWriter out = response.getWriter();
			 out.print( e);
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

   }
    
    public void doDelete(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
	try {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");       
	    response.setCharacterEncoding("UTF-8");
		Connection con = connect();
		String id = request.getPathInfo().split("/")[1];
		
		  String sql = "delete from payments where id = "+id+"";
	      PreparedStatement preparedStmt = con.prepareStatement(sql);
	     
		if(preparedStmt.execute());

         out.print("successfully deleted the record");
		
		
		} catch (SQLException e) {
			PrintWriter out = response.getWriter();
			 out.print( e);
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

   }
}