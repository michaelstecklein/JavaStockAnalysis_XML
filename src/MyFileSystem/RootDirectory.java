package MyFileSystem;

import java.io.File;

/**
 * The <code>Directory</code> class extends the java.io.File class and explicitly separates the directory properties of
 * the generic java.io.File type, which can be both a directory or a file. A directory is a container for other files,
 * (including other directories).
 * @see java.io.File
 * @see RegularFile
 * @see Directory
 * @author michaelstecklein
 *
 */
@SuppressWarnings("serial")
public class RootDirectory extends Directory {

	public RootDirectory(String pathname) {
		super(pathname);
	}
	
	/**
	 * Since a root directory will have no parent file from the perspective of the MyFileSystem package, this file
	 * has been deprecated. This function should only be used to get the directory which a root file resides in.
	 */
	@Deprecated
	public String getParent() {
		return null;
	}

	/**
	 * Since a root directory will have no parent file from the perspective of the MyFileSystem package, this file
	 * has been deprecated. This function should only be used to get the directory which a root file resides in.
	 */
	@Deprecated
	public File getParentFile() {
		return null;
	}

}
