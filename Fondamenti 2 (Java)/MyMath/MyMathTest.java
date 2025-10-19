/**
 * Classe che testa la mia classe MyMath
 * @author Glauco Masi
 * @version 26/02/2025
*/
public class MyMathTest{
    public static void main(String args[]){
        testmcd();
        testmcm();
    }
    
    private static void testmcd(){
        assert(MyMath.mcd(1, 2) == 1);
        assert(MyMath.mcd(4, 10) == 2);
        assert(MyMath.mcd(21, 49) == 7);
    }

    private static void testmcm(){
        assert(MyMath.mcm(1, 2) == 2);
        assert(MyMath.mcm(4, 10) == 20);
        assert(MyMath.mcm(21, 49) == 147);
    }
}