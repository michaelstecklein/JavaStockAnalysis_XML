package MyFileSystem;

import java.io.File;

public class FileSystemManager {
	
	/**
	 * Creates the entirety of the file system pointed to by the parameter root directory. This method
	 * only updates files which do not exist. If a file within the file system already exists, it is left
	 * untouched. However, any children of an existing file may be created if needed.
	 * @param root
	 * @return whether the file system was made successfully
	 */
	public static boolean makeFileSystem(MyFileSystem fileSystem) {
		return make(fileSystem.getRootDirectory());
	}
	
	/**
	 * Creates the entirety of the file system pointed to by the parameter directory. This method only
	 * updates files which do not exist. If a file within the file system already exists, it is left
	 * untouched. However, any children of an existing file may be created if needed.
	 * @param directory
	 * @return whether the file system was made successfully
	 */
	public static boolean make(Directory directory) {
		boolean success = true;
		for (FileType f : directory.getChildren()) {
			if (f instanceof Directory) {
				success &= make((Directory)f);
			} else {
				success &= make((RegularFile)f);
			}
		}
		return success;
	}
	
	/**
	 * Creates the regular file parameter.
	 * @param file
	 * @return whether the regular file was created successfully
	 */
	public static boolean make(FileType file) {
		return file.create();
	}
	
	/**
	 * Executes a clean on the parameter file system's root directory, then the file system is remade.
	 * @see #clean(Directory)
	 * @see #make(Directory)
	 * @param directory
	 * @return whether the clean and make were successful
	 */
	public static boolean makeCleanFileSystem(MyFileSystem fileSystem) {
		return makeClean(fileSystem.getRootDirectory());
	}
	
	/**
	 * Executes a clean on the parameter directory, then the file system is remade.
	 * @see #clean(Directory)
	 * @see #make(Directory)
	 * @param directory
	 * @return whether the clean and make were successful
	 */
	public static boolean makeClean(Directory directory) {
		boolean	cleanSuccess =		clean(directory);
		return	cleanSuccess &&		make(directory);
	}
	
	/**
	 * Cleans the parameter file system's root directory by deleting it and all its sub-directories and children.
	 * @param root
	 * @return whether the clean was successful
	 */
	public static boolean cleanFileSystem(MyFileSystem fileSystem) {
		return clean(fileSystem.getRootDirectory());
	}
	
	/**
	 * Cleans the parameter directory by deleting it and all its sub-directories and children.
	 * @param directory
	 * @return whether the clean was successful
	 */
	public static boolean clean(Directory directory) {
		boolean success = true;
		for (FileType f : directory.getChildren()) {
			if (f instanceof Directory) {
				success &= clean((Directory)f);
			} else if (f instanceof File) {
				success &= ((File)f).delete();
			}
		}
		return success;
	}
	
	/**
	 * Updates the parameter file system's root directory and any of it's contents that implement the updatable
	 * interface.
	 * @see Updatable
	 * @param fileSystem
	 * @return whether all updates were successful
	 */
	public static boolean updateFileSystem(MyFileSystem fileSystem) {
		return update(fileSystem.getRootDirectory());
	}
	
	/**
	 * Updates the parameter directory and any of it's contents that implement the updatable interface.
	 * @param directory
	 * @return whether all updates were successful
	 */
	public static boolean update(Directory directory) {
		boolean success = true;
		for (FileType f : directory.getChildren()) {
			if (f instanceof Directory) {
				success &= update((Directory)f);
			} else if (f instanceof Updatable) {
				success &= update((Updatable)f);
			}
		}
		if (directory instanceof Updatable) {
			return success && update(directory);
		}
		return success;
	}
	
	/**
	 * Updates the parameter updatable if an update is required.
	 * @param updatable
	 * @return whether the update was successful
	 */
	public static boolean update(Updatable updatable) {
		if (updatable.requiresUpdate()) {
			return updatable.update();
		}
		return true;
	}

}
