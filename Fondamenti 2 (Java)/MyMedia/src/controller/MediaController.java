package controller;

import java.io.IOException;

import media.*;
import media.filters.Filter;
import persistence.MediaPersister;
import media.collection.MediaCollection;

public class MediaController {
	private MediaCollection allMedias = null;
	private MediaPersister persister;
	
	public MediaController(MediaPersister persister) {
		this.persister = persister;
		
		try {
			allMedias = this.persister.load();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			allMedias = new MediaCollection();
		}
	}
	
	public MediaCollection getAll() {
		MediaCollection copy = new MediaCollection();
		int size = allMedias.size();
		
		for(int i = 0; i < size; i++) {
			copy.add(allMedias.get(i));
		}
		
		return copy;
	}
	
	public boolean add(Media m) {
		if(this.allMedias.indexOf(m) != -1) {
			return false;
		}else {
			this.allMedias.add(m);
			saveAll();
			return true;
		}
	}
	
	public boolean remove(Media m) {
		int index = this.allMedias.indexOf(m);
		
		if(index == -1) {
			return false;
		}else {
			this.allMedias.remove(index);
			saveAll();
			return true;
		}
	}
	
	public MediaCollection find(Filter f) {
		MediaCollection ans = new MediaCollection();
		int size = allMedias.size();
		
		for(int i = 0; i < size; i++) {
			Media m = allMedias.get(i);
			
			if(f.filter(m)) {
				ans.add(m);
			}
		}
		
		return ans;
	}
	
	private void saveAll() {
		try {
			persister.save(allMedias);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
