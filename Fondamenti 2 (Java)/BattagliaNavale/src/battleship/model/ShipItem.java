package battleship.model;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;

public enum ShipItem {
	LEFT("<", "sinistra.jpg", "Elemento SINISTRO"),
	RIGHT(">", "destra.jpg", "Elemento DESTRO"),
	UP("^", "su.jpg", "Elemento SUPERIORE"),
	DOWN("v", "giu.jpg", "Elemento INFERIORE"),
	CENTRE("x", "centro.jpg", "Elemento CENTRALE"),
	SINGLE("o", "singolo.jpg", "Elemento SINGOLO"),
	SEA("~", "mare.jpg", "MARE"),
	EMPTY("#", "vuoto.jpg", "Elemento VUOTO");

	private final String value;
	private final String descrizione;
	private Image image;

	ShipItem(String ch, String imageFileName, String descrizione) {
		if (ch.length() != 1) {
			throw new IllegalArgumentException("Unexpected string length: " + ch);
		}
		this.value = ch;
		this.descrizione = descrizione;
		try {
			// OPPURE: new Image(new FileInputStream("src/battleship/model/sinistra.jpg"));
			URL resource = Objects.requireNonNull(ShipItem.class.getResource(imageFileName));
			this.image = new Image(resource.toString());
		} catch (/* NullPointerException | IllegalArgumentException | */ RuntimeException e) {
			// The real exceptions to be caught should be the above one, but
			// JUnit does fire RuntimeException during testing, due to JavaFX not yet
			// initialised
			// So we catch RuntimeException, which is a superclass of the two others.
			this.image = null;
		}
	}


	@Override
	public String toString() {
		return descrizione;
	}

	public static ShipItem of(String ch) {
		if (ch.length() != 1) {
			throw new IllegalArgumentException("Unexpected value: " + ch);
		}
		return of(ch.charAt(0));
	}

	public static ShipItem of(char ch) {
		ShipItem[] opts = ShipItem.values();
		for(int i = 0; i < opts.length; i++) {
			if(opts[i].getValue().charAt(0) == ch) {
				return opts[i];
			}
		}
		
		throw new IllegalArgumentException("Char isn't valid");
	}

	public String getValue() {
		return this.value;
	}

	public Image getImage() {
		return this.image;
	}

}
