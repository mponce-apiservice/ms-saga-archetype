package ec.com.dinersclub.saga.events.models;

import java.util.Date;

public class Rollback {
	
	private String saga;
	private String method;
	private int id;
	private String date;
	
	public Rollback() {
		this.date = new Date().toString();
	}

	public String getSaga() {
		return saga;
	}
	public void setSaga(String saga) {
		this.saga = saga;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}

}
