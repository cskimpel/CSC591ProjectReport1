import java.sql.SQLException;
import java.util.List;

public class main {
	static db db;
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args)
	{
		db = new db();

//		db.showEdges(0);

		for (int user=0; user <12; user++)
		{
			int pathLength = 2; // Number of consecutive points to consider - 2 being only from one point to another 
			int stayPointRadius = 100; // # of feet to use as the radius of the stay points
			
			// Analyze based on LatLon Points
			List<Long> longArray = db.getLatLonPoints(user);
			double ent = db.Entrophy(longArray,pathLength);
			int n = longArray.size();
			int percent = db.getFannoPrediction(ent, n);
			System.out.println("User " + (user+1) + ", LatLon Entrophy = " + ent + ", n = " + n + ", Fano Prediction = " + percent);

			// a dist of 1 is about 3.5 feet 
			double unitDist = 3.5; 
			long dist = Math.round(stayPointRadius / unitDist);
			longArray = db.getStayPoints(user, dist);
			ent = db.Entrophy(longArray,pathLength);
			n = longArray.size();
			percent = db.getFannoPrediction(ent, n);
			System.out.println("User " + (user+1) + ", Stay Point Entrophy = " + ent + ", n = " + n + ", Fano Prediction = " + percent);

			longArray = db.getNamedPoints(user);
			ent = db.Entrophy(longArray,pathLength);
			n = longArray.size();
			percent = db.getFannoPrediction(ent, n);
			System.out.println("User " + (user+1) + ", Named Entrophy = " + ent + ", n = " + n + ", Fano Prediction = " + percent);

			System.out.println("");
		}
		System.out.println("Processing Complete !!!");
	}
}
