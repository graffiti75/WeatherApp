package br.android.weather_app.tasks;

/**
 * Notifiable class.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public interface Notifiable {
	
	/**
	 * Called when a task is finished.
	 * 
	 * @param type The type of the task.
	 * @param result The OperationResult object.
	 */
	public void taskFinished(int type, Object result);
}