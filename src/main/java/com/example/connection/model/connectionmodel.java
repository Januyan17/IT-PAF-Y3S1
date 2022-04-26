package com.example.connection.model;

public class connectionmodel {
	private int id;
	private String cid;
	private String cname;
	private String connectivity;
	
	public connectionmodel() {
		
	}

	public connectionmodel(int id, String cid, String cname, String connectivity) {
		super();
		this.id = id;
		this.cid = cid;
		this.cname = cname;
		this.connectivity = connectivity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getConnectivity() {
		return connectivity;
	}

	public void setConnectivity(String connectivity) {
		this.connectivity = connectivity;
	}
	
	
	
	
}
