package com.example.rodrigo.weatherapp.controller.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.rodrigo.weatherapp.R;

/**
 * DialogHelper.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class DialogHelper {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param message
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String message) {
		return showProgressDialog(context, message, false);
	}
	
	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param message
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
	}
	
	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 */
	public static void showSimpleAlert(Context context, int titleResource, int messageResource) {
		showSimpleAlert(context, titleResource, messageResource, null);
	}

	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param listener
	 */
	public static void showSimpleAlert(Context context, int titleResource, int messageResource, OnClickListener listener) {
		String dialogTitle = titleResource > 0 ? context.getString(titleResource) : null;
		String dialogMessage = messageResource > 0 ? context.getString(messageResource) : null;
		showSimpleAlert(context, dialogTitle, dialogMessage, listener);
	}
	
	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param listener
	 */
	public static void showSimpleAlert(Context context, String title, String message, OnClickListener listener) {
		if (context instanceof Activity && ((Activity)context).isFinishing()) {
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton(R.string.dialog_ok, listener);
		builder.show();
	}
	
	/**
	 * Shows a custom {@link Dialog}.
	 * 
	 * @param context
	 * @param layoutId
	 * @param titleResource
	 *
	 * @return
	 */
	public static void showCustomDialog(final Context context, int layoutId, int titleResource,
		final OnClickListenerCustomDialog callback) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    
	    LayoutInflater inflater = LayoutInflater.from(context);            
	    View inflator = inflater.inflate(layoutId, null);
	    builder.setView(inflator);
	    final EditText editText = (EditText)inflator.findViewById(R.id.id_city_adapter__city_edit_text);
	    
	    builder.setTitle(titleResource);
	    builder.setNegativeButton(R.string.dialog_cancel, null);
	    builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String city = editText.getText().toString();
				callback.onClickCallback(context, city);
			}
		});
	    
	    builder.create();
	    builder.show();
	}
}