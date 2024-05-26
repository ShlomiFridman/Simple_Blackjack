package application;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {

	private List<Card> hand;
	private Card firstAce;
	private int score;

	public PlayerHand() {
		this.hand = new ArrayList<>();
		this.firstAce = null;
		this.score = 0;
	}

	public List<Card> getHand() {
		return hand;
	}

	public int getScore() {
		return score;
	}
	
	public String getScoreString() {
		return Integer.toString(score);
	}

	public boolean isBusted() {
		return score > 21;
	}

	// for the dealer instance
	public boolean isOver16() {
		return score > 16;
	}
	// for the player instance
	public boolean have21() {
		return score == 21;
	}
	
	public boolean addCard(Card card) {
		// add card to hand, and value to score
		this.hand.add(card);
		this.score += card.getValue();
		// if first ace and will not exceed 20, add 10 bonus
		if (card.getRank() == Rank.ACE && firstAce == null && score + 10 < 21) {
			firstAce = card;
			score += 10;
		}
		// else if firstAce isn't null and is now busted, remove the bonus
		else if (firstAce != null && isBusted()) {
			score -= 10;
			// reset value so the bonus will only be removed once
			firstAce = null;
		}
		// return if busted
		return isBusted();
	}
	
	public void reset() {
		// empty hand
		this.hand.clear();
		// reset score and flag
		this.score = 0;
		this.firstAce = null;
	}

}
