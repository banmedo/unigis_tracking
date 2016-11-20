package unigis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Controller {
	private Broadcaster bc;
	private static String regexpattern = "(\\$GPRMC)\\,(\\d{6}\\.?\\d*)\\,([AV]{1})\\,(\\d*\\.?\\d*)\\,([NS]{1})\\,(\\d*\\.?\\d*)\\,([EW]{1})\\,([0-9]*\\.?[0-9]*)\\,([0-9]*\\.?[0-9]*)\\,([0-9]{6})\\,([0-9]*\\.?[0-9]*)\\,([EW]?)\\,([ADEMSN]{1}.*.[0-9A-F]{2})";
	private Pattern p = Pattern.compile(regexpattern);
	private Matcher m;
	private long imei;
	
	private DateTime dt;
	private DateTimeFormatter dateparser = DateTimeFormat.forPattern("ddMMYY,HHmmss");
	 
	public Controller (String updateurl) {
		bc = new Broadcaster(updateurl);
	}
	
	public String processStreamResult(String gpsdata) {
		if (gpsdata.length() > 2) {
			try {
				String[] dataparts = gpsdata.split("\\$");
				String nmeaData = "$" + dataparts[0];
				m = p.matcher(nmeaData);
				if (m.matches() == true) {
					dt = dateparser.parseDateTime(m.group(10) + "," + m.group(2).substring(0, 5));
					int unixtime = (int) Math.floor(dt.getMillis() / 1000);
					if (dataparts.length == 2) {
						imei = Long.parseLong(dataparts[1]);
					} else {
						imei = 0;
					}
					return triggerStorageProcess(nmeaData,unixtime,imei);
				} else {
					return "gps-data is not a valid GPRMC sentence";
				}
			} catch (Exception ex) {
				return "An error occured" + ex.toString();
			}
		} else {
			return "asfd";
		}
	}

	private String triggerStorageProcess(String nmeaData, int unixtime, long imei) {
	    return bc.broadcastLocation(nmeaData, imei, unixtime);
	}

}
