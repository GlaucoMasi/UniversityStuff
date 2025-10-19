package media.filters;

import media.Type;
import media.Media;

public class TypeFilter implements Filter {
	private Type type = null;
	
	public TypeFilter(Type type) {
		this.type = type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public boolean filter(Media media) {
		if(media instanceof HasType m) {
			return this.type == m.getType();
		}
		
		return false;
	}
}
