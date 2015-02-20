// ======================================================================================
//  Used to store one Fano Inequality Triplet which consists of the three variables
//     s - Entropy calculated from the number of nodes and the percent-max using the Fano Inequality Method
//     PercentMax - between .01 and .99 inclusive - The percentage of predictability 
//     n - number of nodes
// ======================================================================================
public class FanoTriplet {
    private double s;
    private double percentMax;
    private double n;

    // s
    public double getS() {return this.s;}
    public void setS(double value) {this.s = value;}

    // Percent Max
    public double getPercentMax() {return this.percentMax;}
    public void setPercentMax(double value) {this.percentMax = value;}

    // n 
    public double getN() {return this.n;}
    public void setN(double value) {this.n = value;}
    
    public int size()
    {
        return 24; // double is 8 bytes * 3 doubles
    }
    
}
