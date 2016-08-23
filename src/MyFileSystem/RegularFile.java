package MyFileSystem;

import java.io.File;
import java.io.IOException;

/**
 * The <code>RegularFile</code> class extends the java.io.File class and explicitly separates the file properties of
 * the generic java.io.File type, which can be both a directory or a file. It adds functionality that can be overridden
 * in order to initialize a file with certain characteristics or content via the {@link #create()} method.
 * 
 * @see java.io.File
 * @see Directory
 * @author michaelstecklein
 *
 */
@SuppressWarnings("serial")
public class RegularFile extends File implements FileType {
	
	private Directory parent;

	public RegularFile(String fileName) {
		super(fileName);
	}
	
	@Override
	public boolean create() {
		if (parent == null) {
			return false;
		}
		try {
			return this.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Sets the parent directory of the regular file. Without this, the
	 * regular file cannot be created.
	 * @param parent
	 */
	protected void setParent(Directory parent) {
		this.parent = parent;
		this.renameTo(new File(parent.getPath().concat(this.getName())));
	}

}
