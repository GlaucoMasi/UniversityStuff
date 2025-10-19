package persistence;

import java.io.IOException;
import java.io.FileNotFoundException;

import media.collection.MediaCollection;

public interface MediaPersister {
	public MediaCollection load() throws FileNotFoundException, IOException, ClassNotFoundException;
	public void save(MediaCollection mc) throws IOException;
}
