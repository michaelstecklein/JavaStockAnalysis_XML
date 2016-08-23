package MyFileSystem;

import java.util.Set;

/**
 * The <code>FileSet</code> interface outlines encapsulates functionality that allows files to be operated on as sets.
 * @author michaelstecklein
 *
 */
public interface FileSet extends FileType {
	
	/**
	 * Returns the set of files contained by this FileSet.
	 * @return the set of files
	 */
	public Set<FileType> getFileSet();

}
