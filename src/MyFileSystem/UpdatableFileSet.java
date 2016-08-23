package MyFileSystem;


/**
 * The <code>UpdatableFileSet</code> combines the functionalities of the FileSet and Updatable interfaces to provide for functionality
 * for updates across sets of similar updatable files. It is not enforced that the contained files are Updatable, and any files which
 * are not Updatable will be ignored during updates.
 * @author michaelstecklein
 *
 */
public abstract class UpdatableFileSet implements FileSet, Updatable {

	@Override
	public boolean update() {
		boolean success = true;
		for (FileType f : getFileSet()) {
			if (f instanceof Updatable) {
				success &= ((Updatable) f).update();
			}
		}
		return success;
	}

	@Override
	public boolean requiresUpdate() {
		for (FileType f : getFileSet()) {
			if (f instanceof Updatable) {
				if (((Updatable) f).requiresUpdate()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean create() {
		boolean success = true;
		for (FileType f : this.getFileSet()) {
			success &= f.create();
		}
		return success;
	}
	
}
