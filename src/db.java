import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class db {
	public static final String[] USER_NAME = {
		"GS1", "GS2", "GS3", "GS4", "GS5", "GS6", "GS7", 
		"GS8", "GS9", "GS10", "GS11", "GS12" 
	};
	
	public static final String[] DB_FILE = {
		"LifeMap_GS1.db", "LifeMap_GS2.db", "LifeMap_GS3.db", "LifeMap_GS4.db", "LifeMap_GS5.db", 
		"LifeMap_GS6.db", "LifeMap_GS7.db", "LifeMap_GS8.db", "LifeMap_GS9.db", "LifeMap_GS10.db", 
		"LifeMap_GS11.db", "LifeMap_GS12.db"
	};

	String[][] USER_SKIP_DAY = {
			// GS1
			{"20110308","20110309","20110312","20110313","20110314","20110322","20110325","20110329","20110330","20110331",
				"20110402","20110403","20110407","20110417","20110502","20110525","20110607","20110618","20110619","20110621",
				"20110630","20110701","20110711","20110719"},
			// GS2
			{"20110502","20110504","20110505","20110723","20110724","20110725","20110726","20110817","20111003","20111005",
				"20111015","20111016","20111027","20111115"},
			// GS3
			{"20101117","20101211","20101212","20101224","20110112","20110113","20110124","20110125","20110126","20110304",
				"20110305","20110306","20110307","20110615","20110620","20110629","20110630","20110701","20110702","20110703",
				"20110704","20110814"},
			// GS4
			{"20110507","20110619","20110927","20111009","20111012","20111013","20111014","20111015","20111016","20111017",
				"20111115"},
			// GS7
			{"20110310","20110311","20110318","20110319","20110401","20110402","20110620","20110719"},
			// GS8
			{"20110310","20110407","20110408","20110712","20110713","20110714","20110716","20110717","20110818","20110819",
				"20110824","20110825","20110826","20110910","20110911","20110915","20111114"},
			// GS9
			{"20110326","20110329","20110620","20110621","20110705","20110706","20110707","20110708","20110709","20110710",
				"20110711","20110712","20110714","20110827","20110828","20110829","20110830","20110831","20110901","20110902",
				"20110911","20110912","20111004"},
			// GS12
			{"20101114","20110125","20110126","20110305","20110306","20110324","20110325","20110411","20110412","20110413",
				"20110414","20110415","20110416","20110417","20110418","20110419","20110420","20110421","20110422","20110613",
				"20110614","20110616","20110617","20110627","20110628","20110629","20110630","20110701","20110702","20110703",
				"20111010","20111011","20111012","20111019","20111020","20111021","20111022","20111023","20111031","20111101",
				"20111102","20111103","20111104","20111105","20111106","20111111"},
	};
	
	String[] HOLIDAY = {
			"20101225", "20110101", "20110202", "20110203", "20110204", "20110301", "20110505", "20110510", "20110606", "20110815", 
			"20110912", "20110913", "20111003"	
		};

	// DB Version 15 for 3.1x Release
	// DB Version 17 for 3.2x Release
	// DB Version 22 for v1.0 Release
	// DB Version 25 for v1.1 Release
	// DB Version 26 for v1.2 Release. Add Battery Table
	// DB Version 27 for v1.2 Release. Add Cell Sequence Table
	// DB Version 29 for v1.2 Release. Add Battery Log, Cell Transition Log to Configure Table
	// DB Version 30 for v2.0 Release.
	public static final int DATABASE_VERSION = 30;

	// Version 22 for v1.0 Release
	public static final String LOCATION_TABLE = "locationTable";
	public static final String AP_TABLE = "apTable";
	public static final String CELL_TABLE = "cellTable";

	public static final String EDGE_TABLE = "edgeTable";

	public static final String CATEGORY_TABLE = "categoryTable";
	public static final String CATEGORY_SET_TABLE = "categorySetTable";
	public static final String CONFIGURE_TABLE = "configureTable";

	public static final String STAY_TABLE = "stayTable";
	public static final String STAY_COMMENT_TABLE = "stayCommentTable";

	public static final String SENSOR_USAGE_TABLE = "sensorUsageTable";
	public static final String NO_RADIO_TABLE = "noRadioTable";

	// Version 26 for v1.2 Release
	public static final String BATTERY_TABLE = "batteryTable";
	
	// Version 27 for v1.3 Release
	public static final String CELL_NODE_TABLE = "cellNodeTable";
	public static final String CELL_EDGE_TABLE = "cellEdgeTable";
	
	// Deprecated
	public static final String EDGE_EXTRA_TABLE = "edgeExtraTable";
	public static final String USER_TABLE = "userTable";

	public static final String LOCATION_KEY_ID = "_node_id";
	public static final int LOCATION_COLUMN_ID = 1;
	public static final String LOCATION_KEY_LATITUDE = "_latitude";
	public static final int LOCATION_COLUMN_LATITUDE = 2;
	public static final String LOCATION_KEY_LONGITUDE = "_longitude";
	public static final int LOCATION_COLUMN_LONGITUDE = 3;
	public static final String LOCATION_KEY_LATITUDE_GPS = "_latitude_gps";
	public static final int LOCATION_COLUMN_LATITUDE_GPS = 4;
	public static final String LOCATION_KEY_LONGITUDE_GPS = "_longitude_gps";
	public static final int LOCATION_COLUMN_LONGITUDE_GPS = 5;
	public static final String LOCATION_KEY_LATITUDE_WIFI = "_latitude_wifi";
	public static final int LOCATION_COLUMN_LATITUDE_WIFI = 6;
	public static final String LOCATION_KEY_LONGITUDE_WIFI = "_longitude_wifi";
	public static final int LOCATION_COLUMN_LONGITUDE_WIFI = 7;
	public static final String LOCATION_KEY_ALTITUDE = "_altitude";
	public static final int LOCATION_COLUMN_ALTITUDE = 8;
	public static final String LOCATION_KEY_ACCURACY = "_accuracy";
	public static final int LOCATION_COLUMN_ACCURACY = 9;
	public static final String LOCATION_KEY_ACCURACY_GPS = "_accuracy_gps";
	public static final int LOCATION_COLUMN_ACCURACY_GPS = 10;
	public static final String LOCATION_KEY_ACCURACY_WIFI = "_accuracy_wifi";
	public static final int LOCATION_COLUMN_ACCURACY_WIFI = 11;
	public static final String LOCATION_KEY_ACTIVITY = "_activity";
	public static final int LOCATION_COLUMN_ACTIVITY = 12;
	public static final String LOCATION_KEY_PLACE_NAME = "_place_name";
	public static final int LOCATION_COLUMN_PLACE_NAME = 13;
	public static final String LOCATION_KEY_PLACE_COMMENT = "_place_comment";
	public static final int LOCATION_COLUMN_PLACE_COMMENT = 14;
	public static final String LOCATION_KEY_TIME = "_time_location";
	public static final int LOCATION_COLUMN_TIME = 15;

	public static final String AP_KEY_ID = "_ap_id";
	public static final int AP_COLUMN_ID = 1;
	public static final String AP_KEY_NODE_ID = LOCATION_KEY_ID;
	public static final int AP_COLUMN_NODE_ID = 2;
	public static final String AP_KEY_BSSID = "_bssid";
	public static final int AP_COLUMN_BSSID = 3;
	public static final String AP_KEY_SSID = "_ssid";
	public static final int AP_COLUMN_SSID = 4;
	public static final String AP_KEY_OPEN = "_open";
	public static final int AP_COLUMN_OPEN = 5;
	public static final String AP_KEY_SIGNAL = "_signal";
	public static final int AP_COLUMN_SIGNAL = 6;
	public static final String AP_KEY_SIGNAL_DEVIATION = "_signal_deviation";
	public static final int AP_COLUMN_SIGNAL_DEVIATION = 7;
	public static final String AP_KEY_SAMPLE_COUNT = "_sample_count";
	public static final int AP_COLUMN_SAMPLE_COUNT = 8;
	public static final String AP_KEY_TIME = "_time_ap";
	public static final int AP_COLUMN_TIME = 9;

	public static final String CELL_KEY_ID = "_cell_id";
	public static final int CELL_COLUMN_ID = 1;
	public static final String CELL_KEY_NODE_ID = LOCATION_KEY_ID;
	public static final int CELL_COLUMN_NODE_ID = 2;
	public static final String CELL_KEY_CELL_TYPE = "_cell_type";
	public static final int CELL_COLUMN_CELL_TYPE = 3;
	public static final String CELL_KEY_CID = "_cid";
	public static final int CELL_COLUMN_CID = 4;
	public static final String CELL_KEY_LAC = "_lac";
	public static final int CELL_COLUMN_LAC = 5;
	public static final String CELL_KEY_CONNECT_TIME = "_connect_time";
	public static final int CELL_COLUMN_CONNECT_TIME = 6;
	public static final String CELL_KEY_TIME = "_time_cell";
	public static final int CELL_COLUMN_TIME = 7;
	
	public static final String CELL_NODE_KEY_ID = "_cell_node_id";
	public static final int CELL_NODE_COLUMN_ID = 1;
	public static final String CELL_NODE_KEY_CELL_TYPE = "_cell_node_type";
	public static final int CELL_NODE_COLUMN_CELL_TYPE = 2;
	public static final String CELL_NODE_KEY_CID = "_node_cid";
	public static final int CELL_NODE_COLUMN_CID = 3;
	public static final String CELL_NODE_KEY_LAC = "_node_lac";
	public static final int CELL_NODE_COLUMN_LAC = 4;
	public static final String CELL_NODE_KEY_CONNECT_TIME = "_node_connect_time";
	public static final int CELL_NODE_COLUMN_CONNECT_TIME = 5;
	public static final String CELL_NODE_KEY_TIME = "_time_cell_node";
	public static final int CELL_NODE_COLUMN_TIME = 6;
	
	public static final String CELL_EDGE_KEY_ID = "_cell_edge_id";
	public static final int CELL_EDGE_COLUMN_ID = 1;
	public static final String CELL_EDGE_KEY_CELL_NODE_ID = CELL_NODE_KEY_ID;
	public static final int CELL_EDGE_COLUMN_CELL_NODE_ID = 2;
	public static final String CELL_EDGE_KEY_CELL_DESTINATION_ID = "_cell_edge_destination_cell_id";
	public static final int CELL_EDGE_COLUMN_CELL_DESTINATION_ID = 3;
	public static final String CELL_EDGE_KEY_TIME = "_time_cell_edge";
	public static final int CELL_EDGE_COLUMN_TIME = 4;
	
	public static final String EDGE_KEY_ID = "_edge_id";
	public static final int EDGE_COLUMN_ID = 1;
	public static final String EDGE_KEY_NODE_ID = LOCATION_KEY_ID;
	public static final int EDGE_COLUMN_NODE_ID = 2;
	public static final String EDGE_KEY_DES_NODE_ID = "_destination_node_id";
	public static final int EDGE_COLUMN_DES_NODE_ID = 3;
	public static final String EDGE_KEY_TIME = "_time_edge";
	public static final int EDGE_COLUMN_TIME = 4;
	
	public static final String CATEGORY_KEY_ID = "_category_id";
	public static final int CATEGORY_COLUMN_ID = 1;
	public static final String CATEGORY_KEY_PLACE_NAME = "_category_place_name";
	public static final int CATEGORY_COLUMN_PLACE_NAME = 2;
	public static final String CATEGORY_KEY_ACTIVE = "_category_active";
	public static final int CATEGORY_COLUMN_ACTIVE = 3;
	public static final String CATEGORY_KEY_EDITABLE = "_category_editable";
	public static final int CATEGORY_COLUMN_EDITABLE = 4;
	public static final String CATEGORY_KEY_RES_ID = "_category_res_id";
	public static final int CATEGORY_COLUMN_RES_ID = 5;
	public static final String CATEGORY_KEY_TIME = "_time_category";
	public static final int CATEGORY_COLUMN_TIME = 6;

	public static final String CATEGORY_SET_KEY_ID = "_category_set_id";
	public static final int CATEGORY_SET_COLUMN_ID = 1;
	public static final String CATEGORY_SET_KEY_CATEGORY_ID = CATEGORY_KEY_ID;
	public static final int CATEGORY_SET_COLUMN_CATEGORY_ID = 2;
	public static final String CATEGORY_SET_KEY_LOCATION_ID = LOCATION_KEY_ID;
	public static final int CATEGORY_SET_COLUMN_LOCATION_ID = 3;
	public static final String CATEGORY_SET_KEY_HUMAN_ID = "_category_set_human_id";
	public static final int CATEGORY_SET_COLUMN_HUMAN_ID = 4;
	public static final String CATEGORY_SET_KEY_TIME = "_time_category_set";
	public static final int CATEGORY_SET_COLUMN_TIME = 5;

	public static final String CONFIGURE_KEY_ID = "_configure_id";
	public static final int CONFIGURE_COLUMN_ID = 1;
	public static final String CONFIGURE_KEY_TIME_INTERVAL = "_configure_time_interval";
	public static final int CONFIGURE_COLUMN_TIME_INTERVAL = 2;
	public static final String CONFIGURE_KEY_UNCATEGORIZED_VIEW = "_configure_uncategorized_view";
	public static final int CONFIGURE_COLUMN_UNCATEGORIZED_VIEW = 3;
	public static final String CONFIGURE_KEY_SENSOR_USAGE_LOG = "_configure_sensor_usage_log";
	public static final int CONFIGURE_COLUMN_SENSOR_USAGE_LOG = 4;
	public static final String CONFIGURE_KEY_BATTERY_LOG = "_configure_battery_log";
	public static final int CONFIGURE_COLUMN_BATTERY_LOG = 5;
	public static final String CONFIGURE_KEY_CELL_LOG = "_configure_cell_log";
	public static final int CONFIGURE_COLUMN_CELL_LOG = 6;
	public static final String CONFIGURE_KEY_LAST_NODE_ID = "_configure_last_node_id";
	public static final int CONFIGURE_COLUMN_LAST_NODE_ID = 7;
	public static final String CONFIGURE_KEY_LAST_ACTIVITY = "_configure_last_activity";
	public static final int CONFIGURE_COLUMN_LAST_ACTIVITY = 8;
	public static final String CONFIGURE_KEY_NICKNAME = "_configure_nickname";
	public static final int CONFIGURE_COLUMN_NICKNAME = 9;
	public static final String CONFIGURE_KEY_TWITTER_TOKEN = "_configure_twitter_token";
	public static final int CONFIGURE_COLUMN_TWITTER_TOKEN = 10;
	public static final String CONFIGURE_KEY_TWITTER_TOKEN_SECRET = "_configure_twitter_token_secret";
	public static final int CONFIGURE_COLUMN_TWITTER_TOKEN_SECRET = 11;
	public static final String CONFIGURE_KEY_TIME = "_time_configure";
	public static final int CONFIGURE_COLUMN_TIME = 12;

	public static final String STAY_KEY_ID = "_stay_id";
	public static final int STAY_COLUMN_ID = 1;
	public static final String STAY_KEY_NODE_ID = LOCATION_KEY_ID;
	public static final int STAY_COLUMN_NODE_ID = 2;
	public static final String STAY_KEY_STAY_TIME = "_stay_time";
	public static final int STAY_COLUMN_STAY_TIME = 4;
	public static final String STAY_KEY_STAY_START_TIME = "_stay_start_time";
	public static final int STAY_COLUMN_STAY_START_TIME = 3;
	public static final String STAY_KEY_TIME = "_time_stay";
	public static final int STAY_COLUMN_TIME = 5;

	public static final String STAY_COMMENT_KEY_ID = "_stay_comment_id";
	public static final int STAY_COMMENT_COLUMN_ID = 1;
	public static final String STAY_COMMENT_KEY_STAY_ID = STAY_KEY_ID;
	public static final int STAY_COMMENT_COLUMN_STAY_ID = 2;
	public static final String STAY_COMMENT_KEY_TYPE = "_stay_comment_type";
	public static final int STAY_COMMENT_COLUMN_TYPE = 3;
	public static final String STAY_COMMENT_KEY_CONTENT = "_stay_comment_content";
	public static final int STAY_COMMENT_COLUMN_CONTENT = 4;
	public static final String STAY_COMMENT_KEY_IMAGE = "_stay_comment_image";
	public static final int STAY_COMMENT_COLUMN_IMAGE = 5;
	public static final String STAY_COMMENT_KEY_TIME = "_time_stay_comment";
	public static final int STAY_COMMENT_COLUMN_TIME = 6;

	public static final String SENSOR_USAGE_KEY_ID = "_sensor_usage_id";
	public static final int SENSOR_USAGE_COLUMN_ID = 1;
	public static final String SENSOR_USAGE_KEY_TYPE = "_sensor_type";
	public static final int SENSOR_USAGE_COLUMN_TYPE = 2;
	public static final String SENSOR_USAGE_KEY_CYCLE = "_sensor_cycle";
	public static final int SENSOR_USAGE_COLUMN_CYCLE = 3;
	public static final String SENSOR_USAGE_KEY_USAGE_TIME = "_sensor_usage_time";
	public static final int SENSOR_USAGE_COLUMN_USAGE_TIME = 4;
	public static final String SENSOR_USAGE_KEY_START_TIME = "_sensor_start_time";
	public static final int SENSOR_USAGE_COLUMN_START_TIME = 5;
	public static final String SENSOR_USAGE_KEY_TIME = "_time_sensor_usage";
	public static final int SENSOR_USAGE_COLUMN_TIME = 6;
	
	public static final String BATTERY_KEY_ID = "_battery_id";
	public static final int BATTERY_COLUMN_ID = 1;
	public static final String BATTERY_KEY_LEVEL = "_battery_level";
	public static final int BATTERY_COLUMN_LEVEL = 2;
	public static final String BATTERY_KEY_STATUS = "_battery_status";
	public static final int BATTERY_COLUMN_STATUS = 3;
	public static final String BATTERY_KEY_VOLTAGE = "_battery_voltage";
	public static final int BATTERY_COLUMN_VOLTAGE = 4;
	public static final String BATTERY_KEY_TIME = "_time_battery";
	public static final int BATTERY_COLUMN_TIME = 5;
	
	public static final String NO_RADIO_KEY_ID = "_no_radio_id";
	public static final int NO_RADIO_COLUMN_ID = 1;
	public static final String NO_RADIO_KEY_DURATION = "_no_radio_duration";
	public static final int NO_RADIO_COLUMN_DURATION = 2;
	public static final String NO_RADIO_KEY_START_TIME = "_no_radio_start_time";
	public static final int NO_RADIO_COLUMN_START_TIME = 3;
	public static final String NO_RADIO_KEY_TIME = "_time_no_radio";
	public static final int NO_RADIO_COLUMN_TIME = 4;
	
	java.sql.Connection conn;
	
	public db() {
		try {
			Class.forName("org.sqlite.JDBC").newInstance();
			System.out.println("SQL Driver Load");
		} catch (ClassNotFoundException e){
			System.out.println("Driver Load Error");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException  {
		if(conn != null && !conn.isClosed()) conn.close();		
	}
	
	public boolean importFromFile(String path) throws SQLException  {
		if(conn != null && !conn.isClosed()) conn.close();
		
		conn = DriverManager.getConnection(path);
		
		return true;
	}

	public void showEdges(int user) {

		Calendar timeCalendar = Calendar.getInstance();
		SimpleDateFormat sdfTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
				if(conn != null && !conn.isClosed()) conn.close();
				conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE[user]);
				
				Statement stmCs = conn.createStatement();
				Statement locationCs = conn.createStatement();
				ResultSet cs, locationRs;

				// Print user name and file name
				System.out.println("User name:\t" + USER_NAME[user]);
				System.out.println("file name:\t" + DB_FILE[user]);
				
				long nodeId = 0, destinationId = 0;

				cs = stmCs.executeQuery("Select * from edgeTable order by _edge_id");
				while(cs.next()) {
					nodeId = cs.getLong(cs.findColumn(EDGE_KEY_NODE_ID));
					destinationId = cs.getLong(cs.findColumn(EDGE_KEY_DES_NODE_ID));
					
					locationRs = locationCs.executeQuery("Select * from locationTable where _node_id = " + nodeId);
					locationRs.next();
					
					System.out.print(nodeId);
					System.out.print(" to ");
					System.out.print(destinationId);
					System.out.print(" lat ");
					System.out.print(locationRs.getInt(locationRs.findColumn("_latitude")));
					System.out.print(" lon ");
					System.out.print(locationRs.getInt(locationRs.findColumn("_longitude")));
					System.out.print(" place ");
					System.out.print(locationRs.getString(locationRs.findColumn("_place_name")));
					System.out.print(" time ");
					timeCalendar.setTime(sdfTimeStamp.parse(cs.getString(cs.findColumn(EDGE_KEY_TIME))));
					System.out.println(sdfTimeStamp.parse(cs.getString(cs.findColumn(EDGE_KEY_TIME))));
				}
				cs.close();
				stmCs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	//================================================================================
	// Gets the array of points by tracing all of the edges in order
	//
	// Parameters:
	//    user - The number of the user database to obtain the points from
	//
	// Returns:
	//    A set of points in the order that they were traversed by user.
	//================================================================================
	public List<Long> getLatLonPoints(int user) {
		List<Long> aEdges = new ArrayList<Long>();
		try {
			if(conn != null && !conn.isClosed()) conn.close();
			conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE[user]);
			
			Statement stmCs = conn.createStatement();
			ResultSet cs;

			long nodeId = 0, destinationId = 0;

			cs = stmCs.executeQuery("Select * from edgeTable order by _edge_id");
			while(cs.next()) {
				nodeId = cs.getLong(cs.findColumn(EDGE_KEY_NODE_ID));
				destinationId = cs.getLong(cs.findColumn(EDGE_KEY_DES_NODE_ID));
				aEdges.add(nodeId);
			}
			aEdges.add(destinationId);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		
		return aEdges;
	}

	//================================================================================
	// Gets the array of stay points by tracing all of the edges in order and calculating stay points from them.
	//
	// Parameters:
	//    user - The number of the user database to obtain the points from
	//    dist - Consider all points within dist of the last selected lat/lon to be the same point
	//           a dist of 1 is about 3.5 feet 
	//
	// Returns:
	//    The set of stay points in the order that they were traversed by user.
	//================================================================================
	public List<Long> getStayPoints(int user, long dist) {
		List<Long> aEdges = new ArrayList<Long>();
		List<Integer> aLats = new ArrayList<Integer>();
		List<Integer> aLons = new ArrayList<Integer>();
		try {
			if(conn != null && !conn.isClosed()) conn.close();
			conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE[user]);
			
			Statement stmCs = conn.createStatement(), locationCs = conn.createStatement();
			ResultSet cs, locationRs;

			long nodeId = 0, destinationId = 0, lastLatitude=0, lastLongitude=0;

			cs = stmCs.executeQuery("Select * from edgeTable order by _edge_id");
			while(cs.next()) {
				nodeId = cs.getLong(cs.findColumn(EDGE_KEY_NODE_ID));
				destinationId = cs.getLong(cs.findColumn(EDGE_KEY_DES_NODE_ID));

				locationRs = locationCs.executeQuery("Select * from locationTable where _node_id = " + nodeId);
				locationRs.next();
				
				int latitude = locationRs.getInt(locationRs.findColumn("_latitude"));
				int longitude = locationRs.getInt(locationRs.findColumn("_latitude"));
				
				// If this point is within dist of last lat/lon then do not keep it.
				// Always discard unknown lat/lon.
				if (latitude != 0 && longitude != 0 && ( latitude<=lastLatitude-dist || latitude>=lastLatitude+dist || longitude<=lastLongitude-dist || longitude>=lastLongitude+dist ))
				{
					// Add the new stay point
					aEdges.add(nodeId);
					aLats.add(latitude);
					aLons.add(longitude);
					lastLatitude = latitude;
					lastLongitude = longitude;
				}
			}

			// Test the last point
			locationRs = locationCs.executeQuery("Select * from locationTable where _node_id = " + nodeId);
			locationRs.next();
			
			int latitude = locationRs.getInt(locationRs.findColumn("_latitude"));
			int longitude = locationRs.getInt(locationRs.findColumn("_latitude"));

			// If the last point is within dist of last lat/lon then do not keep it. 
			// Always discard unknown lat/lon.
			if (latitude != 0 && longitude != 0 && ( latitude<=lastLatitude-dist || latitude>=lastLatitude+dist || longitude<=lastLongitude-dist || longitude>=lastLongitude+dist ))
			{
				aEdges.add(destinationId);
			}

			// Scan stay points and adjust overlapping point areas so that they become the same point
			for (int i=0; i<aEdges.size(); i++)
			{
				nodeId = aEdges.get(i);
				latitude = aLats.get(i);
				longitude = aLons.get(i);
				// Start the inner scan from one past the point being tested and scan the rest of the array
				for (int j=i+1; j<aEdges.size(); j++)
				{
					int latitudeTest = aLats.get(j);
					int longitudeTest = aLons.get(i);
					if ( Math.abs(latitudeTest-latitude) <= dist && Math.abs(longitudeTest-longitude) <= dist)
					{
						aEdges.set(j, nodeId);	// These points are close enough to be considered the same point
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		
		
		return aEdges;
	}

	//================================================================================
	// Gets the array of named points by tracing all of the edges in order only using the named points.
	//
	// Parameters:
	//    user - The number of the user database to obtain the points from
	//
	// Returns:
	//    The set of named points in the order that they were traversed by user.
	//================================================================================
	public List<Long> getNamedPoints(int user) {
		List<Long> aEdges = new ArrayList<Long>();
		try {
			if(conn != null && !conn.isClosed()) conn.close();
			conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE[user]);
			
			Statement stmCs = conn.createStatement(), locationCs = conn.createStatement();
			ResultSet cs, locationRs;

			long nodeId = 0, destinationId = 0;
			String lastName = "";

			cs = stmCs.executeQuery("Select * from edgeTable order by _edge_id");
			while(cs.next()) {
				nodeId = cs.getLong(cs.findColumn(EDGE_KEY_NODE_ID));
				destinationId = cs.getLong(cs.findColumn(EDGE_KEY_DES_NODE_ID));

				locationRs = locationCs.executeQuery("Select * from locationTable where _node_id = " + nodeId);
				locationRs.next();
				
				String name  = locationRs.getString(locationRs.findColumn("_place_name"));
				
				if (name != null && !name.equals(lastName))
				{
					// Add the new stay point
					aEdges.add(nodeId);
					lastName = name;
				}
			}

			// Test the last point
			locationRs = locationCs.executeQuery("Select * from locationTable where _node_id = " + nodeId);
			locationRs.next();
			
			String name  = locationRs.getString(locationRs.findColumn("_place_name"));

			if (name != null && !name.equals(lastName))
			{
				aEdges.add(destinationId);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		
		return aEdges;
	}

	//================================================================================
	// Determines the number of times one array is contained within another
	//
	// Parameters:
	//    shortArray - Array to test
	//    longsArray - Array to be tested against
	//
	// Returns:
	//    Percentage that short array is contained within long array.
	//================================================================================
	public double Probability(List<Long> shortArray, List<Long> longArray) {
		int total = longArray.size() - shortArray.size() + 1;	// Number to test
		int hits = 0;											// Number of finds
		
		for (int i=0; i<total; i++)
		{
			// See if the first one matches
			if (shortArray.get(0).longValue() == longArray.get(i).longValue())
			{
				// test the rest of the array
				int k = i+1;
				int l = 0;
				for (l=1; l<shortArray.size(); l++)
				{
					if (shortArray.get(l).longValue() != longArray.get(k).longValue())
					{
						break;
					}
					k++;
				}

				// test for match was found
				if (l == shortArray.size())
				{
					hits++;
				}
			}
		}
		
		return hits / 1.0 / total;
	}

	//================================================================================
	// Determines the entrophy taking into account the ordering of a set of points 
	// which is a subset of another set of points.
	//
	// Parameters:
	//    longsArray - Array of points in the complete set
	//    subLength - Number of points to test
	//                  ie. 2 means determine the entrophy given that a person goes from one point to the next in that order
	//
	// Returns:
	//    Entrophy given longArray and subLength.
	//================================================================================
	public double Entrophy(List<Long> longArray, int subLength) {
		double ret = 0;
        double log2 = Math.log(2);
        
		// Number of possible subsets to test
		int runs = longArray.size() - subLength;
		
		ArrayList<Long> subSetsArray = new ArrayList<Long>(); // Array to store subsets that have already been included

		for (int i=0; i<runs ;i++)
		{
				
			ArrayList<Long> shortArray = new ArrayList<Long>();

			// get the current subset to test
			for (int j=i; j<i+subLength; j++)
			{
				shortArray.add(longArray.get(j));
			}
			
			if (subSetExists(shortArray, subSetsArray)) continue; // This subset has already been included

			// Add new subset to subSets Array
			for (int j=0; j<shortArray.size();j++)
			{
				subSetsArray.add(shortArray.get(j).longValue());
			}
			
			// Find the probability that the subset is contained within longArray
			double percent = Probability(shortArray, longArray);
			
			// Sum the entrophy's of all of the subsets
			ret = ret + (percent * Math.log(percent)/log2);
		}
		
		return -ret;
	}
	
	//================================================================================
	// Determines if a subset already exists in a another set 
	//
	// Parameters:
	//    subset - Subset to test
	//    set - set to be tested against
	//
	// Returns:
	//    true when subset is already contained within set - false otherwise
	//================================================================================
	public boolean subSetExists(ArrayList<Long> subset, ArrayList<Long> set)
	{
		int len = subset.size();
		// Examine each member of set for a subset match, taken subset length items at a time
		for (int i = 0; i<set.size();i+=len)
		{
			int j = i;
			boolean isIn = true;
			for(int k=0; k<len; k++)
			{
				if (set.get(j).longValue() != subset.get(k).longValue())
				{
					isIn = false;
					break;
				}
				j++;
			}
			
			if (isIn) return true;
		}
		
		return false;
	}
	
	//================================================================================
	// Determines the predictability using FANo's method 
	//
	// Parameters:
	//    s - Entrophy of the tested points
	//    n - Number of points in the dataset
	//
	// Returns:
	//    Fano Predictibility %.
	//================================================================================
	public int getFannoPrediction(double s, int n)
	{
		// These were calculated in Excel, the only variable is %. Calaulted from 1 to 99 percent in whole percents
		double[] binaryEntropy = new double[] { 0.080793136, 0.141440543, 0.194391858, 0.242292189, 0.286396957, 0.327444919, 0.365923651, 0.40217919, 0.436469817, 0.468995594, 0.499915958, 0.529360865, 0.557438185, 0.584238812, 0.609840305, 0.634309555, 0.657704779, 0.680077046, 0.70147146, 0.721928095, 0.74148274, 0.760167503, 0.778011304, 0.795040279, 0.811278124, 0.826746372, 0.841464636, 0.855450811, 0.868721246, 0.881290899, 0.893173458, 0.904381458, 0.914926373, 0.924818705, 0.934068055, 0.942683189, 0.950672093, 0.958042022, 0.964799549, 0.970950594, 0.976500469, 0.981453895, 0.985815037, 0.989587521, 0.992774454, 0.995378439, 0.997401589, 0.998845536, 0.999711442, 1, 0.999711442, 0.998845536, 0.997401589, 0.995378439, 0.992774454, 0.989587521, 0.985815037, 0.981453895, 0.976500469, 0.970950594, 0.964799549, 0.958042022, 0.950672093, 0.942683189, 0.934068055, 0.924818705, 0.914926373, 0.904381458, 0.893173458, 0.881290899, 0.868721246, 0.855450811, 0.841464636, 0.826746372, 0.811278124, 0.795040279, 0.778011304, 0.760167503, 0.74148274, 0.721928095, 0.70147146, 0.680077046, 0.657704779, 0.634309555, 0.609840305, 0.584238812, 0.557438185, 0.529360865, 0.499915958, 0.468995594, 0.436469817, 0.40217919, 0.365923651, 0.327444919, 0.286396957, 0.242292189, 0.194391858, 0.141440543, 0.080793136 };
		int result = 0;
        double log2 = Math.log(2);

        double log2n = Math.log(n - 1) / log2;
        for (int i = 0; i < 99; i++)
        {
            double percentMax = ((i + 1) * .01);
            double FanoS = binaryEntropy[i] + ((1 - percentMax) * log2n);

            // entrophy always decreases as percentage of predictability increases
            // therefore as soon as the Fano entrophy is < the tested entrophy we have arrived at the prediction percentage
            if (s > FanoS)
            {
            	// We have found the closest percentage
            	result = i+1;
            	break;
            }
        }
        return result;
	}
}
