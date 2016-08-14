package gov.lbl.webjob.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.bson.types.ObjectId;

/**
 * @author Alex
 *
 */
public class Util {

	/**
	 * Creates a unique UUID.
	 * @return randomUUID().toString()
	 * @see Java Documentation: <a target="_top" href="http://docs.oracle.com/javase/7/docs/api/java/util/UUID.html">UUID<a>
	 */
	public static String genStringUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Generates String timestamp with current time.
	 * @return Date in format <b>YYYY-MM-DD-HH:MM:SS</b> Timezone
	 */
	public static String genTimestamp(){
		Calendar currentDate = Calendar.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD-HH:mm:ss z");
	    String dateNow = formatter.format(currentDate.getTime());
	    return dateNow;
	}
	
	/**
	 * Creates a String representation of a unique ObjectId.
	 * @return ObjectId.toString()
	 * @see MongoDB Reference: <a target="_top" href="https://docs.mongodb.com/manual/reference/method/ObjectId/">ObjectId</a>
	 */
	public static String genStringObjectId(){
		return new ObjectId().toString();
	}
	
}
