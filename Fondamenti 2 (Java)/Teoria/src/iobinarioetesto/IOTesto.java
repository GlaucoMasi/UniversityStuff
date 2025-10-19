package iobinarioetesto;

import java.io.*;

public class IOTesto {
	public void input() {
		try(FileReader is = new FileReader("ciao")){
			is.read();
		}catch(IOException e) { }
		
		try(BufferedReader is = new BufferedReader(new FileReader("ciao"))){
			is.read();
			is.readLine();
		}catch(IOException e) { }
	}
	
	public void output() {
		try(FileWriter os = new FileWriter("ciao")){
			os.flush();
			os.write(10);
		}catch(IOException e) { }
		
		try(BufferedWriter os = new BufferedWriter(new FileWriter("ciao"))){
			os.write(10);
			os.flush();
			os.write(new char[10]);
		}catch(IOException e) { }
		
		try(PrintWriter os = new PrintWriter(new FileWriter("ciao"))){
			os.write(10);
			os.flush();
			os.write(new char[10]);
		}catch(IOException e) { }
	}
}
