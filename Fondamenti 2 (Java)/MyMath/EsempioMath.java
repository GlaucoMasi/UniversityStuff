/**
 * Esempio di cliente che usa la classe MyMath
 * @author Glauco Masi
 * @version 26/02/2025
*/
public class EsempioMath{
    public static void main(String args[]){
        if(args.length != 2){
            System.out.println("Erorre: numero sbagliato di argomenti");
            return;
        }

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        System.out.println("mcd(" + a + ", " + b + ") = " + MyMath.mcd(a, b));
        System.out.println("mcm(" + a + ", " + b + ") = " + MyMath.mcm(a, b));
    }
}