package com.example.rodrigo.weatherapp.view.listeners;

import android.view.View;

/**
 * RecyclerViewListeners.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public interface RecyclerViewListeners {
	void onClick(View view, int position);
	void onLongClick(View view, int position);
}