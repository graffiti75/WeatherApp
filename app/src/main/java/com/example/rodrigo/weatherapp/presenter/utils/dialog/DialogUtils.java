package com.example.rodrigo.weatherapp.presenter.utils.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.rodrigo.weatherapp.R;

/**
 * DialogUtils.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class DialogUtils {

	public static ProgressDialog showProgressDialog(Context context, String message) {
		return showProgressDialog(context, message, false);
	}
	
	private static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
	}
	
	public static void showSimpleAlert(Context context, int titleResource, int messageResource) {
		showSimpleAlert(context, titleResource, messageResource, null);
	}

	public static void showSimpleAlert(Context context, int titleResource, int messageResource, OnClickListener listener) {
		String dialogTitle = titleResource > 0 ? context.getString(titleResource) : null;
		String dialogMessage = messageResource > 0 ? context.getString(messageResource) : null;
		showSimpleAlert(context, dialogTitle, dialogMessage, listener);
	}
	
	private static void showSimpleAlert(Context context, String title, String message, OnClickListener listener) {
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

	public static void showNoConnectionDialog(Activity activity) {
		showSimpleAlert(activity, R.string.network_error_dialog_title,
			R.string.network_error_dialog_message, (dialog, which) -> dialog.cancel());
	}

	public static void showConfirmDialog(Context context, int titleResource, int messageResource,
		OnClickListener confirmListener, OnClickListener cancelListener) {
		showConfirmDialog(context, titleResource, messageResource, 0, confirmListener, 0, cancelListener);
	}

	public static void showConfirmDialog(Context context, int titleResource, int messageResource,
		int confirmLabelResource, OnClickListener confirmListener, int cancelLabelResource, OnClickListener cancelListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setIcon(android.R.drawable.ic_dialog_alert);

		if (titleResource != 0) builder.setTitle(titleResource);
		if (messageResource != 0) builder.setMessage(messageResource);
		if (confirmLabelResource == 0) confirmLabelResource = R.string.dialog_ok;
		if (cancelLabelResource == 0) cancelLabelResource = R.string.dialog_cancel;

		builder.setPositiveButton(confirmLabelResource, confirmListener);
		if (cancelListener != null) {
			builder.setNegativeButton(cancelLabelResource, cancelListener);
		} else {
			builder.setNegativeButton(cancelLabelResource, (dialog, which) -> dialog.dismiss());
		}
		builder.show();
	}

	public static void showCustomDialog(final Context context, int layoutId, int titleResource,
		final OnClickListenerCustomDialog callback) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    
	    LayoutInflater inflater = LayoutInflater.from(context);            
	    View inflator = inflater.inflate(layoutId, null);
	    builder.setView(inflator);

		final EditText editText = (EditText)inflator.findViewById(R.id.id_city_adapter__city_edit_text);
	    builder.setTitle(titleResource);
	    builder.setNegativeButton(R.string.dialog_cancel, null);
	    builder.setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
            String city = editText.getText().toString();
            callback.onClickCallback(context, city);
        });
	    
	    builder.create();
	    builder.show();
	}
}