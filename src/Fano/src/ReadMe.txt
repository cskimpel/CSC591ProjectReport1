FanoInequality.xslx
   Calculates the Fano binary entropy using H(Pmax) = –Pmax log2(Pmax) – (1 – Pmax) log2(1 – Pmax)
   for all percentages from .01-.99 in whole perrcent values.
   These contast values are used in Fano.java for working with the Fano Inequality.

Fano.java
   Used for working with the Fano Inequality in java.

    public static void main(String[] args)
	// ==================================================================================================
    	// Contains comments out examples for creating or Interpreting Fano Inequalities
	// ==================================================================================================

    static void CreateFile(long start, long end)
	//=================================================================================================
	// Creates a very large array of doubles and stores them in the specified file
	// The doubles are stored in triplets where each triplet matches the three variables in the Fano Inequality
	//	     s - Entropy
	//	     percent Max - Maximum percent of predictability
	//	     n - Number if nodes
	// =================================================================================================

   static double InterpretFano(double s, long n)
	// ==================================================================================================
    	// Returns the percent predictability given the number of nodes and entropy
	// Parameters
	//   s - entropy
	//   n - number of nodes
	// ==================================================================================================

FanoTriplet.java
   Provides structure for he Fano variables
