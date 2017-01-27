package com.example.rodrigo.weatherapp.view.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerTouchListener.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

	private RecyclerViewListeners mClicklistener;
	private GestureDetector mGestureDetector;

	public RecyclerTouchListener(Context context, final RecyclerView recycleView, final RecyclerViewListeners clicklistener) {
		mClicklistener = clicklistener;
		mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				View child = recycleView.findChildViewUnder(e.getX(), e.getY());
				if (child != null && clicklistener != null) {
					clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
				}
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
		View child = rv.findChildViewUnder(e.getX(), e.getY());
		if (child != null && mClicklistener != null && mGestureDetector.onTouchEvent(e)) {
			mClicklistener.onClick(child,rv.getChildAdapterPosition(child));
		}
		return false;
	}

	@Override
	public void onTouchEvent(RecyclerView rv, MotionEvent e) {
	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
	}
}