package MyFileSystem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * The <code>Directory</code> class extends the java.io.File class and explicitly separates the directory properties of
 * the generic java.io.File type, which can be both a directory or a file. A directory is a container for other files,
 * (including other directories).
 * @see java.io.File
 * @see RegularFile
 * @author michaelstecklein
 *
 */
@SuppressWarnings("serial")
public class Directory extends File implements FileType {

	/**
	 * If the parent does not contain this object as a child, it is added to the parent directory.
	 * @param parent
	 * @param child
	 */
	public Directory(Directory parent, String child) {
		super(parent, child);
		parent.add(this);
	}

	public Directory(String pathname) {
		super(pathname);
	}
	
	private List<FileType> files = new ArrayList<FileType>();
	
	/**
	 * Adds the parameter file to the list of files contained by this directory. If the file is
	 * already a child of the directory, the file is not added.
	 * @param file
	 * @return whether the file was successfully added
	 */
	public boolean add(FileType file) {
		if (files.contains(file)) {
			return false;
		}
		if (file instanceof RegularFile) {
			((RegularFile)file).setParent(this);
		}
		return files.add(file);
	}
	
	/**
	 * Add the parameter list of files to the list of files contained by this directory. If any file
	 * is already a child of the directory, the file is not added. If any one file is not added, this
	 * function returns false.
	 * @param files
	 * @return if all files were successfully added
	 */
	public boolean add(List<FileType> files) {
		boolean success = true;
		for (FileType f : files) {
			success &= this.add(f);
		}
		return success;
	}
	
	/**
	 * Removes the parameter file from the list of files contained by this directory.
	 * @param file
	 * @return if the file was a child of the director before removal
	 */
	public boolean remove(FileType file) {
		return files.remove(file);
	}
	
	/**
	 * Returns the list of files contained within this directory.
	 * @return the list of files contained within this directory
	 */
	public List<FileType> getChildren() {
		return files;
	}
	
	/**
	 * Sets the list of files to be contained by this directory. The parameter list overrides
	 * the list of any previously contained files. Therefore, all files contained by the directory
	 * before this function call. To maintain these files as well as append a new list, see
	 * {@link #add(List)}.
	 * @param files
	 */
	public void setFiles(List<FileType> files) {
		this.files = files;
	}
	
	@Override
	public boolean create() {
		try {
			return this.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
