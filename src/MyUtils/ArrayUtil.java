package MyUtils;

import java.util.Arrays;


public class ArrayUtil {
    
    /**
     * Helper that converts a character array into an array of bytes.
     * It is recommended that the parameter array is zeroed when finish
     * for security reasons.
     * @param charArr
     * @return the converted byte array
     */
    public static byte[] convertCharArrToByteArr(char[] charArr) {
    	if (charArr == null) {
    		return null;
    	}
    	
    	byte[] byteArr = new byte[charArr.length];
    	for (int i = 0; i < charArr.length; i++) {
    		byteArr[i] = (byte)charArr[i];
    	}
    	
    	return byteArr;
    }
	
	/**
	 * Will fill the parameter array with zeroes.
	 * 
	 * @param ar
	 */
	public static void zeroArray(byte[] ar) {
		if (ar != null) {
			Arrays.fill(ar, (byte)0);
		}
	}

	
	/**
	 * Will fill the parameter array with zeroes.
	 * 
	 * @param ar
	 */
	public static void zeroArray(char[] ar) {
		if (ar != null) {
			Arrays.fill(ar, (char)0);
		}
	}

	
	/**
	 * Will fill the parameter array with zeroes.
	 * 
	 * @param ar
	 */
	public static void zeroArray(int[] ar) {
		if (ar != null) {
			Arrays.fill(ar, 0);
		}
	}

	
	/**
	 * Will fill the parameter array with zeroes.
	 * 
	 * @param ar
	 */
	public static void zeroArray(long[] ar) {
		if (ar != null) {
			Arrays.fill(ar, 0);
		}
	}
	
}
