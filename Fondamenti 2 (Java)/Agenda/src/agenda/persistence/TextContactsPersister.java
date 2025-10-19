package agenda.persistence;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;

import agenda.model.Contact;
import agenda.model.Detail;

public class TextContactsPersister implements ContactsPersister {
	private static final String SEPARATOR = ";";

	private String readLineSkippingEmpty(BufferedReader innerReader) throws IOException{
		String ans = "";
		while(ans != null && ans.isBlank()) ans = innerReader.readLine();
		return ans;
	}

	@Override
	public List<Contact> load(Reader reader) throws IOException, BadFileFormatException {
		if(reader == null) throw new IOException("reader null");
		List<Contact> ans = new ArrayList<Contact>();
		Optional<Contact> curr;
		BufferedReader br = new BufferedReader(reader);
		while((curr = readContact(br)).isPresent()) ans.add(curr.get());
		return ans;
	}
	
	private Optional<Contact> readContact(BufferedReader innerReader) throws IOException, BadFileFormatException{
		String firstLine = readLineSkippingEmpty(innerReader);
		if(firstLine == null) return Optional.empty();
		if(!firstLine.equals("StartContact")) throw new BadFileFormatException("StartContact expected");
		
		String secondLine = readLineSkippingEmpty(innerReader);
		if(secondLine == null) throw new BadFileFormatException();
		String[] tokens = secondLine.split(SEPARATOR);
		if(tokens.length != 2) throw new BadFileFormatException();

		Contact ans = new Contact(tokens[0], tokens[1]);
		readDetails(ans, innerReader);
		return Optional.of(ans);
		
	}
	
	private void readDetails(Contact c, BufferedReader innerReader) throws IOException, BadFileFormatException{
		String curr = "";
		while(!"EndContact".equals(curr = readLineSkippingEmpty(innerReader))){
			if(curr == null) throw new BadFileFormatException("Detail or EndContact expected");
			String[] tokens = curr.split(SEPARATOR);
			DetailPersister persister = DetailPersister.of(tokens[0]);
			if(persister == null) throw new BadFileFormatException("Unknown Detail Type");
			c.getDetailList().add(persister.load(tokens));
		}
	}

	@Override
	public void save(List<Contact> contacts, Writer writer) throws IOException {
		if(writer == null) throw new IOException("writer null");
		for(Contact contact : contacts) {
			writer.write("StartContact" + FileUtils.NEWLINE);
			saveContact(contact, writer);
			writer.write("EndContact" + FileUtils.NEWLINE);
		}
	}
	
	private void saveContact(Contact c, Writer innerWriter) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(c.getName() + SEPARATOR + c.getSurname() + FileUtils.NEWLINE);
		saveDetails(c.getDetailList(), sb);
		innerWriter.write(sb.toString());
		
	}
	
	private void saveDetails(List<Detail> detailList, StringBuilder sb) throws IOException{
		for(Detail detail : detailList) {
			DetailPersister persister = DetailPersister.of(detail.getName());
			persister.save(detail, sb);
		}
	}
	
}
