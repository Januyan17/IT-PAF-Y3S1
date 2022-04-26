package com.example.connection.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.connection.model.connectionmodel;

public class connectionservice {
Connection con;
	
	public connectionservice(){
		try {
			String url = String.format("jdbc:mysql://localhost:3306/connection?useSSL=false");
			String uname = "root";
			String pwd = "root";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,uname,pwd);
		} catch(Exception e) {
			System.out.println(e+"data insert was unsuccessful");
		}
	}
	
	public connectionmodel insertConnection(connectionmodel connection) {
		String insert = "insert into connection(cid,cname,connectivity) values(?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setString(1, connection.getCid());
			ps.setString(2, connection.getCname());
			ps.setString(3, connection.getConnectivity());
			ps.execute();
			
			
		}catch(Exception e){
			System.out.println(e+"data insert was unsuccessful");
		}
		
		return connection;  
	}
	
public ArrayList<connectionmodel> getConnection() throws SQLException{
		
		ArrayList<connectionmodel> data = new ArrayList<connectionmodel>();
		
		String select = "select * from connection";
		PreparedStatement ps = con.prepareStatement(select);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			connectionmodel model = new connectionmodel();
			
			model.setCid(rs.getString("cid")); // column name
			model.setCname(rs.getString("cname"));
			model.setConnectivity(rs.getString("connectivity"));
			
			data.add(model);
			
		}
		
		return data;
		
	}
	
	
	public ArrayList<connectionmodel> getConnectionById(int id) throws SQLException{
		
		ArrayList<connectionmodel> data = new ArrayList<connectionmodel>();
		String select = "select * from connection where id =?";
		PreparedStatement ps = con.prepareStatement(select);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			connectionmodel model = new connectionmodel();
			
			model.setCid(rs.getString("cid")); // column name
			model.setCname(rs.getString("cname"));
			model.setConnectivity(rs.getString("connectivity"));
			data.add(model);		
		}		
		return data;	
	}
	
	public connectionmodel updatetConnection(connectionmodel connection) {
		String insert = "update connection set name=? , age=? where id =?";
		
		try {
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setString(1, connection.getCid());
			ps.setString(2, connection.getCname());
			ps.setString(3, connection.getConnectivity());
			ps.setInt(4, connection.getId());
			
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e +"data insert unsuccess.");
		}
		
		return connection;
		
	}
	

	public int deletetConnection(int id) {
		String insert = "delete from connection where id =?";
		
		try {
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setInt(1,id);
			
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e +"data insert unsuccess.");
		}
		
		return id;
		
	}
}
