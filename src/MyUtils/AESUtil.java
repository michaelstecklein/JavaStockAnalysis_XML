package MyUtils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * The <code>AESUtil</code> takes advantage of Java AES APIs and encrypts and decrypts byte streams using a byte key. It is highly recommended
 * that all passwords are not stored in the form of a <code>String</code>, as strings are immutable and therefore cannot be zeroed out and will linger in memory.
 * For password collection, <code>JPasswordField</code> is a safe option which stores text as a char array instead of a <code>String</code>. Since AES is a
 * block cipher, all input text must be a multiple size of a block ({@link #BlockSize}) and all keys must be the size of a block. Keys which meet the domain requirements
 * ({@link #MinKeySize} and {@link #MaxKeySize}) will be extrapolated to a size of {@link #BlockSize}. All ciphertext or plaintext will be zero-padded to fit these multiple
 * sizes as well. Metadata will be embedded before encryption in order to remove zero-padding upon decryption.
 * 
 * @author michaelstecklein
 */

public class AESUtil {
	
	/**
	 * The size of a block in this block cipher.
	 */
	final public static int BlockSize = 16; // bytes
	
	/**
	 * The minimum length in bytes a key can be.
	 */
	final public static int MinKeySize = 8; // bytes, arbitrary decision
	
	/**
	 * The maximum length in bytes a key can be.
	 */
	final public static int MaxKeySize = BlockSize; // bytes
	
	/**
	 * Salt for the algorithm.
	 */
	final private static byte[] initializationVector = {-90, 1, 30, 16, 125, -37, -18, -79, 56, -119, -27, -20, -4, 32, -14, -81};
	
	/**
	 * The encryption algorithm.
	 */
	final private static String algorithm = "AES";
	
	/**
	 * The transformation
	 */
	final private static String transformation = algorithm + "/CBC/NoPadding";
	
	/**
	 * Error message used for unexpected exceptions.
	 */
	final private static String errorMessage = "ERROR: An exception was caught in AESUtil. Unexpected behavior will occur.";
	
	/**
	 * Will encrypt the given byte array of cleartext using the password
	 * given. It is recommended that the cleartext and key arrays are zeroed
	 * out when done decrypting.
	 * @param plaintext
	 * @param key
	 * @return the encrypted byte array
	 * @throws InvalidKeyException
	 */
	public static byte[] encrypt(byte[] plaintext, byte[] key) throws InvalidKeyException {
		try {
		    byte[] formattedText = zeroPadAndEmbedMetadata(plaintext);
		    
			Cipher cipher = Cipher.getInstance(transformation); // NoSuchAlgorithmException, NoSuchPaddingException
			SecretKeySpec keySpec = new SecretKeySpec(formatKey(key), algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(initializationVector)); // InvalidAlgorithmParameterException
		    byte[] retArr = cipher.doFinal(formattedText); // IllegalBlockSizeException, BadPaddingException
		    
		    ArrayUtil.zeroArray(formattedText);
		    return retArr;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			reportError(e);
			return null;
		}
	}

	/**
	 * Will decrypt the given byte array of ciphertext using the password
	 * given. It is recommended that the key array is zeroed out when done
	 * decrypting. The assumption is made that the ciphertext is always a
	 * multiple of {@link #BlockSize}, since anything being decrypted should
	 * have been encrypted by the provided sister function that guarantees
	 * this property.
	 * @param ciphertext
	 * @param key
	 * @return the decrypted byte array
	 * @throws InvalidKeyException
	 * @throws CorruptedFileException 
	 */
	public static byte[] decrypt(byte[] ciphertext, byte[] key) throws InvalidKeyException, CorruptedDataException {
		try {
			Cipher cipher = Cipher.getInstance(transformation); // NoSuchAlgorithmException, NoSuchPaddingException
		    SecretKeySpec keyspec = new SecretKeySpec(formatKey(key), algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, keyspec, new IvParameterSpec(initializationVector)); // InvalidAlgorithmParameterException
		    byte[] plaintext = cipher.doFinal(ciphertext); // IllegalBlockSizeException, BadPaddingException
		    
		    byte[] retArr = removePaddingAndMetadata(plaintext);
		    return retArr;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			if (e instanceof IllegalBlockSizeException) {
				throw new CorruptedDataException();
			}
			reportError(e);
			return null;
		}
	}
	
	
	/**
	 * This method formats the given key to work on a block of size BlockSize. If the
	 * key is not within the correct range defined by MinKeySize and MaxKeySize, or
	 * the key is null, an InvalidKeyException will be thrown. Keys smaller than BlockSize
	 * will be extrapolated and all extrapolated bytes will be transformed in order to
	 * cover a greater bandwidth of the byte's range, outside of the typical ASCII letter
	 * range.
	 * @param key
	 * @return the formatted key
	 */
	private static byte[] formatKey(byte[] key) throws InvalidKeyException  {
		if (key == null) {
			throw new InvalidKeyException("A null key was encountered.");
		}
		if (key.length < MinKeySize) {
			throw new InvalidKeyException("The key size was less than the minimum key size " + MinKeySize + ".");
		}
		if (key.length > MaxKeySize) {
			throw new InvalidKeyException("The key size was greater than the maximum key size " + MaxKeySize + ".");
		}
		
		int remainder = BlockSize - key.length;
		if (remainder == 0) {
			return key;
		}
		byte[] formattedKey = new byte[BlockSize];
		System.arraycopy(key, 0, formattedKey, 0, key.length);
		for (int i = 0; i < remainder; i++) {
			formattedKey[key.length + i] = transformByte(key[i]);
		}
		return formattedKey;
	}
	
	/**
	 * Linearly transforms a byte in order to spread key patterns across the full range of 
	 * a byte.
	 * @param b
	 * @return the transformed byte
	 */
	private static byte transformByte(byte b) {
		return (byte) ((-1 * b) - 1);
	}

	/**
	 * Reprots an exception by outputting error messages to the console.
	 * @param e
	 */
	private static void reportError(Exception e) {
		System.out.println(errorMessage);
		System.out.println("Error message: "+e.getMessage());
		System.out.println("Stack trace: ");
		e.printStackTrace();
	}
	
	
	
	
	// Metadata helper methods
	
	/**
	 * The size in bytes that the metadata will take up.
	 */
	final private static int metadataSize = 3;
	
	/**
	 * The values used as tags to surround the metadata and check for corruption.
	 * DO NOT CHANGE THIS, legacy files will not open if this is changed.
	 */
	final private static byte metadataTag = -128;
	
	/**
	 * The index at which the padding size can be found.
	 */
	final private static int metadataPaddingSizeIndex = 1;
	
	/**
	 * The indexes at which metadata tags should be found.
	 */
	final private static int[] metadataTagIndexes = {0, 2};
	
	/**
	 * Formats the text by zero padding to be a multiple of the block size and embeds the
	 * number of padded zeros as metadata at the beginning of the array, surrounded by tags
	 * to help identify corruption. The metadata value does not include the metadata itself,
	 * and the padding plus the plaintext plus the metadata must be divisible by the {@link #BlockSize}.
	 * @param plaintext
	 * @return the formatted text
	 */
	private static byte[] zeroPadAndEmbedMetadata(byte[] plaintext) {
		
		// calculate sizes and initialize
		int mod = (plaintext.length + metadataSize) % BlockSize;
		int padSize = (mod == 0) ? 0 : (BlockSize - mod);
		byte[] retArr = new byte[plaintext.length + metadataSize + padSize];
		
		// metadata
		retArr[metadataSize - 1] = metadataTag;
		retArr[metadataSize - 2] = (byte) padSize;
		retArr[metadataSize - 3] = metadataTag;
		
		// plaintext copy
		System.arraycopy(plaintext, 0, retArr, metadataSize, plaintext.length);
		
		// padding
		for (int i = metadataSize + plaintext.length; i < retArr.length; i++) {
			retArr[i] = 0;
		}
		
		return retArr;
	}

	
	/**
	 * Removes formatting of the text by reading the metadata which denotes the number of trailing
	 * zeroes to remove. The metadata and trailing zeroes are removed and the result is returned.
	 * @param decryptedText
	 * @param paddingSize
	 * @return the unformatted plaintext
	 */
	private static byte[] removePaddingAndMetadata(byte[] decryptedText) throws CorruptedDataException {
		// retrieve size of padding
		int paddingSize = decryptedText[metadataPaddingSizeIndex];
		
		// validate metadata
		if (paddingSize < 0 || paddingSize >= BlockSize) {
			throw new CorruptedDataException();
		}
		for (int index : metadataTagIndexes) {
			if (decryptedText[index] != metadataTag) {
				throw new CorruptedDataException();
			}
		}
		
		// remove metadata and zero padding
		byte[] retArr = new byte[decryptedText.length - paddingSize - metadataSize];
		System.arraycopy(decryptedText, metadataSize, retArr, 0, retArr.length);
		return retArr;
	}
	
	
	
	/**
	 * Exception case used to denote a byte stream without the correct metadata. Metadata may have either been
	 * corrupted or non-existant.
	 * @author michaelstecklein
	 */
	@SuppressWarnings("serial")
	public static class CorruptedDataException extends Exception {
		final private static String ErrorMessage = "The metadata for the encrypted byte stream has been corrupted.";
		
		public CorruptedDataException() {
			super(ErrorMessage);
		}
	}

}

