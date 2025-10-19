/**
 * Classe MyMath. Calcola:
 * - mcd
 * - mcm
 * @author Glauco Masi
 * @version 26/02/2025 
*/
public class MyMath{
    /** Calcola l'mcd tra due numeri */
    public static int mcd(int a, int b){
        while(b != 0){
            int t = a%b;
            a = b;
            b = t;
        }

        return a;
    }

    /** Calcola l'mcm tra due numeri */
    public static int mcm(int a, int b){
        return a*b/mcd(a, b);
    }
}