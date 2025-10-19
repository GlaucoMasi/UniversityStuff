package iobinarioetesto;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Tokenizzazione {
	public void tokenizzazione() throws FileNotFoundException {
		StringTokenizer stk = new StringTokenizer("ciaociao", ",");
		stk.nextToken();
		stk.nextToken(" ");
		stk.nextToken(); // Sempre con delimitatore " "
		stk.hasMoreTokens();
		
		Scanner sc;
		sc = new Scanner("ciaociao"); sc.close();
		sc = new Scanner(new FileReader("ciaociao")); sc.close();
		sc = new Scanner(new File("ciao ciao")); sc.close();
		sc = new Scanner(System.in);
		
		sc.next(); sc.nextDouble();
		sc.hasNext(); sc.next();
		sc.delimiter(); sc.useDelimiter(" ");
		sc.reset(); sc.close();
		
		String[] parti = "ciaociao".split("c");
		System.out.println(parti);
	}
}
