package br.android.weather_app.api.model;

/**
 * Request.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class Request {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String query;
	private String type;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public Request() {}
	
	public Request(String query, String type) {
		super();
		this.query = query;
		this.type = type;
	}
	
	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "Request [query=" + query + ", type=" + type + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}