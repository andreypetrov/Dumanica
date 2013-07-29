package com.petrovdevelopment.dumanica.threads;

/**
 * Interface which all client activities of the LoadModelTask should implement
 * @author andrey
 *
 */
public interface LoadModelCaller {
	/**
	 * do something after the model has been loaded from the db
	 */
	void onPostLoadModelExecute();
}
