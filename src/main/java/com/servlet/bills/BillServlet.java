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
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/egrid", "root", "");

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	// Get All Bills
	
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
			try {
				Statement stmt = con.createStatement();
				String query= "select * from ebills";
				ResultSet rs = stmt.executeQuery(query);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");       
			    response.setCharacterEncoding("UTF-8");
			    JsonArray array = new JsonArray();

			    while(rs.next()) {
			    	   JsonObject data= new JsonObject();
			    	   record.add("bid", new Gson().toJsonTree(rs.getInt("bid")));
			    	   record.add("account", new Gson().toJsonTree(rs.getString("account")));
			    	   record.add("paid", new Gson().toJsonTree(rs.getString("paid")));
			    	   record.add("topay",new Gson().toJsonTree( rs.getDate("topay")));
			    	  
			    	   array.add(data);
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
		  
		  String account = rootObject.get("cvc").getAsString();
		  String paid = rootObject.get("expiry_date").getAsString();
		  String topay= rootObject.get("card_no").getAsString();

		  String sql = "insert into ebills(name,card_no,cvc,expiry_date) VALUE ('"+account +"','"+paid+"','"+topay+"')";
	      PreparedStatement preparedStmt = con.prepareStatement(sql);
	     
		if(preparedStmt.execute());
		
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
		String account= rootObject.get("account").getAsString(); 
		String paid= rootObject.get("paid").getAsString();
		String topay= rootObject.get("topay").getAsString();
		  
		  String sql = "UPDATE  ebills set account='"+account+"',paid='"+paid+"',topay='"+topay+"' where id ='"+id+"'";
	      PreparedStatement preparedStmt = con.prepareStatement(sql);
	     
		if(preparedStmt.execute());
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
		
		  String sql = "delete from ebills where id = "+id+"";
	      PreparedStatement preparedStmt = con.prepareStatement(sql);
	     
		if(preparedStmt.execute());
		
		} catch (SQLException e) {
			PrintWriter out = response.getWriter();
			 out.print( e);
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

   }
}