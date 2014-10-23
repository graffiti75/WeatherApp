package br.android.weather_app.helper;

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
import br.android.weather_app.R;

/**
 * DialogHelper.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 18/10/2014
 */
public class DialogHelper {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Creates a list dialog.
	 * 
	 * @param context The context to show the dialog.
	 * @param items The list of items.
	 * @param title The title text.
	 * @param listener The click listener.
	 */
	public static void showListDialog(Context context, CharSequence[] items, CharSequence title, OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(items, listener);
		builder.show();
	}

	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param messageResource
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int messageResource) {
		return showProgressDialog(context, messageResource, false);
	}
	
	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param messageResource
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String message) {
		return showProgressDialog(context, message, false);
	}
	
	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param messageResource
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int messageResource, boolean cancelable) {
		String message = context.getString(messageResource);
		return showProgressDialog(context, message, cancelable);
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
	public static void showSimpleAlert(Context context, String titleResource, String messageResource) {
		showSimpleAlert(context, titleResource, messageResource, null);
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
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmLabelResource
	 * @param confirmListener
	 */
	public static void showConfirmDialog(Context context, int titleResource, int messageResource, int confirmLabelResource, OnClickListener confirmListener) {
		showConfirmDialog(context, titleResource, messageResource, confirmLabelResource, confirmListener, 0, null);
	}
	
	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmListener
	 * @param cancelListener
	 */
	public static void showConfirmDialog(Context context, int titleResource, int messageResource, OnClickListener confirmListener, OnClickListener cancelListener) {
		showConfirmDialog(context, titleResource, messageResource, 0, confirmListener, 0, cancelListener);
	}
	
	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmListener
	 * @param cancelListener
	 */
	public static void showConfirmDialog(Context context, String title, String message,
		OnClickListener confirmListener, OnClickListener cancelListener) {
		showConfirmDialog(context, title, message, 0, confirmListener, 0, cancelListener);
	}

	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmLabelResource
	 * @param confirmListener
	 * @param cancelLabelResource
	 * @param cancelListener
	 */
    public static void showConfirmDialog(Context context, int titleResource, int messageResource,
    	int confirmLabelResource, OnClickListener confirmListener, 
    	int cancelLabelResource, OnClickListener cancelListener) {
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
            builder.setNegativeButton(cancelLabelResource, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }
    
	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmLabelResource
	 * @param confirmListener
	 * @param cancelLabelResource
	 * @param cancelListener
	 */
    public static void showConfirmDialog(Context context, String title, String message, int confirmLabelResource,
    	OnClickListener confirmListener, int cancelLabelResource, OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setIcon(android.R.drawable.ic_dialog_alert);

        if (title != null && !title.equals("")) builder.setTitle(title);
        if (message != null && !message.equals("")) builder.setMessage(message);
        if (confirmLabelResource == 0) confirmLabelResource = R.string.dialog_ok;
        if (cancelLabelResource == 0) cancelLabelResource = R.string.dialog_cancel;

        builder.setPositiveButton(confirmLabelResource, confirmListener);
        if (cancelListener != null) {
            builder.setNegativeButton(cancelLabelResource, cancelListener);
        } else {
            builder.setNegativeButton(cancelLabelResource, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }
    
	/**
	 * Shows a custom {@link Dialog}.
	 * 
	 * @param context
	 * @param layoutId
	 */
	public static void showCustomDialog(final Context context, int layoutId) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    LayoutInflater inflater = ((Activity) context).getLayoutInflater();

	    builder.setView(inflater.inflate(layoutId, null))
	    	.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					
				}
	    	})
	    	.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			
	    		}
	    	});      
	    builder.create();
	    builder.show();
	}
	
	/**
	 * Shows a custom {@link Dialog}.
	 * 
	 * @param context
	 * @param layoutId
	 * @param titleResource
	 * @param messageResource
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