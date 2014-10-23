package br.android.weather_app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * AppInfo class.
 * 
 * @author Rodrigo Cericatto
 * @since Jun 26 2013
 */
@DatabaseTable
public class AppInfo extends Entity {

	//----------------------------------------------
	// Constants
	//----------------------------------------------
	
	// A constant that holds the first AppInfo id.
	public static final int ID = 0;
	
	//----------------------------------------------
	// Class Members
	//----------------------------------------------

	@DatabaseField(id = true, defaultValue = "0")
	public int id;
	
	@DatabaseField
	public long lastUpdate;
	
	//----------------------------------------------
	// Class Functions
	//----------------------------------------------
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AppInfo [id: " + id + ", lastUpdate: " + lastUpdate + "]";
	}
}