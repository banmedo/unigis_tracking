package unigis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Broadcaster {
	private String updateurl;
	
	public Broadcaster(String updateurl) {
		this.updateurl = updateurl;
	}
	
	public String broadcastLocation(String nmeaData, long imei, int unixtime) {
		try {
			URL url = new URL(updateurl + "?action=inNMEA&data=" + nmeaData + "&imei=" + imei + "&unixtime=" + unixtime);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader content = new InputStreamReader((InputStream) conn.getContent());
			BufferedReader buff = new BufferedReader(content);
			String contentLine = buff.readLine();
			conn.disconnect();
			conn = null;
			return contentLine;
		} catch (Exception ex) {
			return "Error when opening URL";
		}
	}
}
