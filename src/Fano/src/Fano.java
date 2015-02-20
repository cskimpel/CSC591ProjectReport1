import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//=================================================================================================
// This program is for working with the Fano Inequality to determine the percent predictability
// of a set of nodes given their entropy
// =================================================================================================
public class Fano {

	// ============================= Globals ===========================================================
	private static final String FilePath = "D:\\CSC 591\\ProjectReport\\Java\\Fano.dat";
	
	// ==================================================================================================
    // Returns the percent predictability given the number of nodes and entropy
	// Parameters
	//   s - entropy
	//   n - number of nodes
	// ==================================================================================================
	static double InterpretFano(double s, long n)
    {
    	FileInputStream fIn = null;

        //open file to read data
        try {
			fIn = new FileInputStream(FilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        DataInputStream dIn = new DataInputStream(fIn);

        double ret = 100; // Initialize to 100% 
        FanoTriplet t = new FanoTriplet(); 
        
        try {
            //find start record
			dIn.skip((n-2) * 99 * t.size());
			// Read in the next 99 records
	        for(int i=0; i<99; i++)
	        {
	            if (dIn.readDouble() < s)
	            {
	            	ret = dIn.readDouble();	// Max Percent
	            	break;
	            }
	            dIn.readDouble();	// skip past Percent Max
	            dIn.readDouble();	// skip past n
	        }
	        dIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return ret;
    }

	//=================================================================================================
	// Creates a very large array of doubles and stores them in the specified file
	// The doubles are stored in triplets where each triplet matches the three variables in the Fano Inequality
	//	     s - Entropy
	//	     percent Max - Maximum percent of predictability
	//	     n - Number if nodes
	// =================================================================================================
    static void CreateFile(long start, long end)
    {
        //delete the file if it already exists
    	File f = new File(FilePath);
    	f.delete();

    	FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(FilePath, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        DataOutputStream dOut = new DataOutputStream(fOut);

        // Array of Fano Entropies based on percent-max from 1% to 99%
        double[] fanoEntropy = new double[] { 0.080793136, 0.141440543, 0.194391858, 0.242292189, 0.286396957, 0.327444919, 0.365923651, 0.40217919, 0.436469817, 0.468995594, 0.499915958, 0.529360865, 0.557438185, 0.584238812, 0.609840305, 0.634309555, 0.657704779, 0.680077046, 0.70147146, 0.721928095, 0.74148274, 0.760167503, 0.778011304, 0.795040279, 0.811278124, 0.826746372, 0.841464636, 0.855450811, 0.868721246, 0.881290899, 0.893173458, 0.904381458, 0.914926373, 0.924818705, 0.934068055, 0.942683189, 0.950672093, 0.958042022, 0.964799549, 0.970950594, 0.976500469, 0.981453895, 0.985815037, 0.989587521, 0.992774454, 0.995378439, 0.997401589, 0.998845536, 0.999711442, 1, 0.999711442, 0.998845536, 0.997401589, 0.995378439, 0.992774454, 0.989587521, 0.985815037, 0.981453895, 0.976500469, 0.970950594, 0.964799549, 0.958042022, 0.950672093, 0.942683189, 0.934068055, 0.924818705, 0.914926373, 0.904381458, 0.893173458, 0.881290899, 0.868721246, 0.855450811, 0.841464636, 0.826746372, 0.811278124, 0.795040279, 0.778011304, 0.760167503, 0.74148274, 0.721928095, 0.70147146, 0.680077046, 0.657704779, 0.634309555, 0.609840305, 0.584238812, 0.557438185, 0.529360865, 0.499915958, 0.468995594, 0.436469817, 0.40217919, 0.365923651, 0.327444919, 0.286396957, 0.242292189, 0.194391858, 0.141440543, 0.080793136 };

        double log2 = Math.log(2);
        for (long n = start; n < end; n++)
        {
            double log2n = Math.log(n - 1) / log2;
            for (int i = 0; i < 99; i++)
            {
                double percentMax = ((i + 1) * .01);
                //write data
                try {
        			dOut.writeDouble(( fanoEntropy[i] + ((1 - percentMax) * log2n) ));	//s
        	        dOut.writeDouble(percentMax);										//% max
        	        dOut.writeDouble((double)n);										// n

        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
            System.out.println(n);
        }
        
        // close file
        try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    public static void main(String[] args) {
//        CreateFile(2, 10000000);

/*
    	double s = 6.2;
    	long n = 99; 
    	System.out.println("Fano Percent for s= " + s + ", n= " + n + " is " + InterpretFano(s, n));

    	s = 4.2;
    	n = 99; 
    	System.out.println("Fano Percent for s= " + s + ", n= " + n + " is " + InterpretFano(s, n));

    	s = 2.2;
    	n = 99; 
    	System.out.println("Fano Percent for s= " + s + ", n= " + n + " is " + InterpretFano(s, n));

    	s = 0.2;
    	n = 99; 
    	System.out.println("Fano Percent for s= " + s + ", n= " + n + " is " + InterpretFano(s, n));
    	
    	s = 0.01;
    	n = 99; 
    	System.out.println("Fano Percent for s= " + s + ", n= " + n + " is " + InterpretFano(s, n));
*/

    	System.out.println("Processing Complete");
	}

}
