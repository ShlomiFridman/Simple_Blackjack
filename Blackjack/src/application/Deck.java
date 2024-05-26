package application;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Deck {
	
	private List<Card> deck;
	private int drawIndex;
	
	public Deck(boolean shuffle) {
		// create a new deck, with 52 cards
		deck = new ArrayList<>();
		drawIndex = 0;
		for (Suit suit : Suit.values())
			for (Rank rank : Rank.values())
				deck.add(new Card(rank, suit));
		// if shuffle flag is true, shuffle deck
		if (shuffle)
			Collections.shuffle(deck);
	}

	public Card draw() {
		// if at the end of the deck, return null
		if (drawIndex == deck.size())
			return null;
		// else draw card from top
		return deck.get(drawIndex++);
	}
	
	public void reset(boolean shuffle) {
		// reset index
		drawIndex = 0;
		// if flag is true, shuffle deck
		if (shuffle)
			Collections.shuffle(deck);
	}
	
	public List<Card> getDeck(){
		return this.deck;
	}
	
}
