package MyUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.filechooser.FileSystemView;

/**
 * The <code>FileUtil</code> class acts as a static utility for various file operations.
 * The provided operations are intended for ease of use, not optimal performance. Large files
 * may require more specialized implementations of these methods. Better performance may be achieved
 * by copying and altering the provided implementations with optimal buffer sizes for the intended data size.
 * File modifications such as appends or insertions are most efficient to implement outside of this class,
 * since details such as file opening or closing need to be catered to each specific use case.
 * 
 * @author michaelstecklein
 *
 */
public class FileUtil {

	/**
	 * Opens the file specified by the parameter file path in the form of a byte
	 * array. A null array will be returned if an error occurs when opening.
	 * @param filePath
	 * @return the byte array of the file, or null if an error occurs
	 */
	public static byte[] open(String filePath) {
		return open(new File(filePath));
	}
	
	/**
	 * Opens the parameter file object in the form of a byte array. A null array will
	 * be returned if an error occurs when opening.
	 * @param file
	 * @return the byte array of the file, or null if an errory occurs
	 */
	public static byte[] open(File file) {
		if (file.length() > Integer.MAX_VALUE) {
			System.out.println("File " + file.getName() + " is too large to open.");
			return null;
		}
		
		byte[] returnArr = new byte[(int) file.length()];
		int returnArrCounter = 0;
		
		try {
			FileInputStream fis = new FileInputStream(file);
			byte buffer[] = new byte[2048];
			int numBytes;
			while ((numBytes = fis.read(buffer)) != -1) {
				for (int i = 0; i < numBytes; i++) {
					returnArr[returnArrCounter] = buffer[i];
					returnArrCounter++;
				}
			}
			fis.close();
		} catch (IOException e) {
			System.err.println(e);
			return null;
		}
		
		return returnArr;
	}
	
	/**
	 * Writes the provided byte array to the file specified by the file path. Any original
	 * contents of the file will be overwritten by this write, which writes to the
	 * beginning of the specified file. The function returns whether the write was
	 * successful or not.
	 * This write implementation uses a BufferedOutputStream, which is optimal for file
	 * sizes smaller than the buffer. For larger files, see {@link #writeLargeFile(String, byte[])}.
	 * @param filePath
	 * @param contents
	 * @return whether the write was successful
	 */
	public static boolean write(String filePath, byte[] contents) {
		return write(new File(filePath), contents);
	}
	
	/**
	 * Writes the provided byte array to the parameter file object. Any original
	 * contents of the file will be overwritten by this write, which writes to the
	 * beginning of the specified file. The function returns whether the write was
	 * successful or not.
	 * This write implementation uses a BufferedOutputStream, which is optimal for file
	 * sizes smaller than the buffer. For larger files, see {@link #writeLargeFile(File, byte[])}.
	 * @param file
	 * @param contents
	 * @return whether the write was successful
	 */
	public static boolean write(File file, byte[] contents) {
		try {
			OutputStream bos = createOutputStream(file);
			bos.write(contents);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Appends the byte array parameter to the end of the given file. This
	 * function is only intended to append once. To optimize for multiple appends,
	 * (or similarly insertions), keep the OutputStream open, as follows:
	 * <br><br>
	 * OutputStream out = createAppendOutputStream(file);<br>
	 * \\ Append or Insert Here, such as: out.write(content);<br>
	 * out.close();<br>
	 * 
	 * @param file
	 * @param content
	 * @return whether the append was successful
	 */
	public static boolean appendOnce(File file, byte[] content) {
		OutputStream out = createAppendOutputStream(file);
		try {
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Creates a BufferedOutputStream from a FileOutputStream for use with
	 * file manipulation. The FileOutputStream is set to append to the
	 * parameter file, not write from the beginning. Null is returned if an
	 * error occurs when creating the stream.
	 * @param file
	 * @return the created OutputStream
	 */
	public static OutputStream createAppendOutputStream(File file) {
		try {
			return new BufferedOutputStream(new FileOutputStream(file, true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates a BufferedOutputStream from a FileOutputStream for use with
	 * file manipulation. The FileOutputStream is set to write from the beginning
	 * of the parameter file. Null is returned if an error occurs when creating the stream.
	 * @param file
	 * @return the created OutputStream
	 */
	public static OutputStream createOutputStream(File file) {
		try {
			return new BufferedOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates a file at the given path. If the files is not created,
	 * whether due to an error or because the file already exists,
	 * the function will return false.
	 * @param path
	 * @return if the file was successfully created
	 */
	public static boolean create(String path) {
		File f = new File(path);
		try {
			return f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Static method that determines the existence of a file at the
	 * provided path.
	 * @param path
	 * @return if a file exists at the provided path
	 */
	public static boolean exists(String path) {
		File f = new File(path);
		return f.exists();
	}
	
	/**
	 * Deletes the file provided as a parameter. Returns whether the
	 * deletion was successful or not.
	 * @param file
	 * @return whether the deletion was successful
	 */
	public static boolean delete(File file) {
		return file.delete();
	}
	
	/**
	 * Deletes the file at the path provided as a parameter. Returns whether
	 * the deletion was successful or not.
	 * @param path
	 * @return whether the deletion was successful
	 */
	public static boolean delete(String path) {
		return delete(new File(path));
	}
 	
 	/**
 	 * Returns the String of the path of the 
 	 * current directory of the operating program. Is useful for storing
 	 * files locally as well as opening file browsing dialogs locally.
 	 * @return the path of the working directory
 	 */
 	public static String getCurrentDirectory() {
 		return System.getProperty("user.dir");
 	}


 	/**
 	 * Returns the String of the path of the 
 	 * default directory provided by the system.
 	 * @return the path of the default directory
 	 * @see FileSystemView
 	 */
 	public static String getDefaultSystemDirectoryPath() {
 		return FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
 	}
 	
 	/**
 	 * Returns the extension of the given file.
 	 * @param file
 	 * @return the String of the file's extension
 	 */
    public static String getExtension(File file) {
        String extension = null;
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');

        if (i > 0 &&  i < fileName.length() - 1) {
        	extension = fileName.substring(i+1).toLowerCase();
        }
        return extension;
    }
	
}
