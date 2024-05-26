package application;

public enum Suit {

	HEARTS('H'), DIAMONDS('D'), CLUBS('C'), SPADES('S');
	
	private final char letter;
	
	Suit(final char newLetter){
		this.letter = newLetter;
	}
	
	public int getLetter() {
		return letter;
	}
}
