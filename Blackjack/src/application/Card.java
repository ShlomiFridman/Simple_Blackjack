package application;

import java.net.URISyntaxException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
	
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	public Suit getSuit() {
		return suit;
	}
	public int getValue() {
		return rank.getValue();
	}
	
	public String getImgSrc() {
		try {
			return getClass().getResource(String.format("/%s%c.png", rank.getLetter(),suit.getLetter())).toURI().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ImageView getImage() {
		return getImage(this.rank, this.suit);
	}
	
	public static ImageView getImage(Rank rank, Suit suit) {
		String src;
		try {
			src = Card.class.getResource(rank == null? "/back.png" : String.format("/%s%c.png", rank.getLetter(),suit.getLetter())).toURI().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		ImageView img = new ImageView(new Image(src));
		img.setFitHeight(140);
		img.setFitWidth(100);
		return img;
	}

}
