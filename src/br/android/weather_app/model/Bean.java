package br.android.weather_app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Bean.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
@DatabaseTable
public class Bean extends Entity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
	private String name;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public Bean() {}

	public Bean(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "Bean [id: " + id + ", name: " + name + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}