package MyFileSystem;

import java.io.File;

import MyUtils.FileUtil;

/**
 * The <code>CustomFile</code> class extends the RegularFile class in order to abstract the extension concatinating and
 * content writing from subclasses.
 * @author michaelstecklein
 *
 */
@SuppressWarnings("serial")
public abstract class CustomFile extends RegularFile {
	
	/**
	 * Returns the extension associated with the customized file type.
	 * @return the file extension
	 */
	abstract public String getExtension();
	
	/**
	 * Returns the contents of the customized file in the form of a string object.
	 * @return the files contents
	 */
	abstract public String getContents();

	/**
	 * Constructs a custom file object given a parent directory to contain the file and a file
	 * name to add the file extension to.
	 * @param parent
	 * @param fileName
	 */
	public CustomFile(String fileName) {
		super(fileName);
		this.renameTo(new File(this.getName().concat(getExtension())));
	}
	
	/**
	 * Creates a custom file with the default contents provided.
	 */
	@Override
	public boolean create() {
		boolean success = super.create();
		return success && FileUtil.write(this, getContents().getBytes());
	}

}
