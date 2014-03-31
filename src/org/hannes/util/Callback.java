package org.hannes.util;

/**
 * 
 * 
 * @author Hannes
 *
 * @param <T>
 */
public interface Callback<T> {
	
	/**
	 * 
	 * @param result
	 */
	public void call(T result) throws Exception;

	/**
	 * 
	 * @param ex
	 */
	public void exceptionCaught(Exception ex);

	/**
	 * Called when completed
	 */
	public void completed();
	
}