package MyFileSystem;

/**
 * The <code>Updatable</code> interfaces outlines functionality for an entity of a file system which can be updated. In the case of a
 * directory or regular file, updating would prevent cleaning and re-making and would therefore be much more efficient.
 * @author michaelstecklein
 *
 */
public interface Updatable {
	
	/**
	 * Updates the implementing entity and returns whether the update was successful.
	 * @return whether the update was successful
	 */
	public boolean update();
	
	/**
	 * Returns whether the updatable object requires an update or is already up to date.
	 * @return whether an update is required
	 */
	public boolean requiresUpdate();

}
