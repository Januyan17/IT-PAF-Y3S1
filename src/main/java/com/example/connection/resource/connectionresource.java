package com.example.connection.resource;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.connection.model.connectionmodel;
import com.example.connection.service.connectionservice;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/connection")
public class connectionresource {
connectionservice service = new connectionservice();
	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public connectionmodel addConnection(connectionmodel connection) {
		return service.insertConnection(connection);
	}
	
	@Path("/retrieve")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<connectionmodel>  getConnection() throws SQLException {
		 return service.getConnection();
		
	}
	
	@Path("/retrieveById/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<connectionmodel>  getConnection(@PathParam("id") int id) throws SQLException {
		return service.getConnectionById(id);
		
	}
	

	
	@Path("/updateConnection")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public connectionmodel updateConnection(connectionmodel connection) {
		 return service.updatetConnection(connection);
		
	}
	
	@Path("/deleteConnectionById/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public int deleteConnection(@PathParam("id") int id) {
		return service.deletetConnection(id);
		
	}
}
