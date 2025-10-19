package eccezioni;

import java.text.ParseException;
import java.util.Objects;
import java.io.FileNotFoundException;

public class Eccezioni {
	public void eccezioniObbligatorie() throws FileNotFoundException, ParseException, ClassNotFoundException, NoSuchMethodException {
		new FileNotFoundException();
		new ParseException(null, 0);
		new ClassNotFoundException();
		new NoSuchMethodException();
		
		try{
			eccezioniObbligatorie();
		}
		catch(FileNotFoundException | ParseException e) {
			
		}
		catch(ClassNotFoundException e) {
			
		}
		catch(NoSuchMethodException e) {
			
		}
		finally {
			
		}
	}
	
	public void eccezioniNonObbligatorie(Eccezioni a) {
		new NullPointerException();   Objects.requireNonNull(a);
		new ArrayIndexOutOfBoundsException();
		new ArithmeticException();
		new IllegalArgumentException();
		new NumberFormatException();
		new IndexOutOfBoundsException();   Objects.checkIndex(3, 10);
		new UnsupportedOperationException();
	}
}
