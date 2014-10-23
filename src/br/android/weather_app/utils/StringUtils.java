package br.android.weather_app.utils;

/**
 * StringUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public class StringUtils {
	
	/**
	 * Return true if string is null or has a length equal 0.
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(CharSequence string) {
		return string == null || string.length() == 0;
	}

    /**
     * Converts UTF-8 to ISO-8859-1.
     * 
     * @param text The text to be converted.
     * @return The converted text.
     * @throws Exception
     */
    public static String toISO88591(String text) throws Exception { 
    	byte p[] = text.getBytes("UTF-8"); 
    	return new String(p, 0, p.length, "ISO-8859-1"); 
    } 
    
    /**
     * Converts ISO-8859-1 to UTF-8.
     * 
     * @param text The text to be converted.
     * @return The converted text.
     * @throws Exception
     */
    public static String toUTF8(String text) throws Exception { 
    	byte p[] = text.getBytes("ISO-8859-1"); 
    	return new String(p, 0, p.length, "UTF-8"); 
    }
    
	/**
	 * Return the integer value of a char.
	 * 
	 * @param ch The char to be converted.
	 * @return The converted char, as a string. 
	 */
	public static Integer charToInteger(Character ch) {
		return Integer.valueOf(Character.toString(ch));
	}
}