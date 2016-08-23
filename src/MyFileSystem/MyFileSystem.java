package MyFileSystem;

/**
 * The <code>MyFileSystem</code> interface outlines the functionality of the MyFileSystem package, which is simply
 * the creating of file systems given a root directory. All directories and files outside of the system are abstracted
 * from this package, including the directory which the root directly actually resides in. All implementing file systems
 * only need to provide a root directory properly constructed to match the needs of the given file system, and then the
 * static methods within the <code>FileSystemManager</code> class can be used to construct and manipulate the file system.
 * @author michaelstecklein
 *
 */
public interface MyFileSystem {
	
	/**
	 * Returns the root directory of the file system, already properly constructed to contain all files and sub-directories
	 * required of the file system.
	 * @return the root directory of the file system
	 */
	public RootDirectory getRootDirectory();

}
