package persistence;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import media.collection.MediaCollection;

public class MyMediaPersister implements MediaPersister {
	private final static String FILE_PATH = "Media.bin";
	
	public MediaCollection load() throws FileNotFoundException, IOException, ClassNotFoundException {
		try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILE_PATH))){
			return (MediaCollection) is.readObject();
		}	
	}
	
	public void save(MediaCollection mc) throws IOException {
		try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
			os.writeObject(mc);
		}
	}
}
