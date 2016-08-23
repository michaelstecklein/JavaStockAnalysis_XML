package MyFileSystem;

/**
 * The <code>FileType</code> interface encapsulates all types which are in some way a file. This includes directories,
 * sets of files, and regular files themselves. 
 * @author michaelstecklein
 *
 */
public interface FileType {

	/**
	 * Creates a new instance of the file type. Should be overridden in order to provide default initialization
	 * to files of a certain type.
	 * @return whether a new file was successfully created
	 */
	public boolean create();
	
}
