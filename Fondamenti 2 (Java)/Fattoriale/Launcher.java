public class Launcher{
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("Numero di argomenti errato");
            return;
        }

        int n = Integer.parseInt(args[0]);
        System.out.println(Fattoriale.calc(n));
    }
}