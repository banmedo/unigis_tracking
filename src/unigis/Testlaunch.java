package unigis;

public class Testlaunch {
	  private static String gpsdata = "GPRMC,090248.6,A,4745.012742,N,01304.143827,E,0.0,,310710,,,A*48$900000000000000";
	  private static String updateurl = "http://unigis.primebird.com/lesson11/bridge.php";

	  public static void main (String[] args) {
	    Controller ctl  = new Controller(updateurl);
	    System.out.println(ctl.processStreamResult(gpsdata));
	  }
	}

