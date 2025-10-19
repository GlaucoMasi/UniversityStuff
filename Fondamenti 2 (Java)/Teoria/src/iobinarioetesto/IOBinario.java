package iobinarioetesto;

import java.io.*;

record Persona(String nome, String cognome) {}

public class IOBinario {
	public void input() throws FileNotFoundException, IOException, ClassNotFoundException {
		try(FileInputStream is = new FileInputStream("ciao")){
			is.read();
		}catch(IOException e) { }
		
		try(DataInputStream is = new DataInputStream(new FileInputStream("ciao"))){
			is.read();
			is.readInt(); is.readDouble();
		}catch(IOException e) { }
		
		try(ObjectInputStream is = new ObjectInputStream(new FileInputStream("ciao"))){
			is.read();
			is.readObject();
		}catch(IOException e) { }
	}
	
	public void output() {
		try(FileOutputStream os = new FileOutputStream("ciao")){
			os.flush();
			os.write(10);
		}catch(IOException e) { }
		
		try(DataOutputStream os = new DataOutputStream(new FileOutputStream("ciao"))){
			os.write(10);
			os.writeInt(10); os.writeDouble(0.421);
			os.flush();
		}catch(IOException e) { }
		
		try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("ciao"))){
			os.write(10);
			os.writeObject(new Persona("Glauco", "Masi"));
		}catch(IOException e) { }
	}
}
