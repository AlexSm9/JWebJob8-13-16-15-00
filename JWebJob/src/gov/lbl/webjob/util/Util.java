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
	
	public static String genStringObjectId(){
		return new ObjectId().toString();
	}
	
}
