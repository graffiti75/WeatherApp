package br.android.weather_app.data;

import java.util.ArrayList;
import java.util.List;

import br.android.weather_app.model.City;

/**
 * ListItems.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class ListItems {
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the city list.
	 * 
	 * @return
	 */
	public static List<City> getCityList() {
		List<City> cityList = new ArrayList<City>();
		cityList.add(new City(1, "Dublin, Ireland"));
		cityList.add(new City(2, "London, United Kingdom"));
		cityList.add(new City(3, "New York, United States Of America"));
		cityList.add(new City(4, "Barcelona, Spain"));
		
		return cityList;
	}
}