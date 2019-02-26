package utilities;

/**
 * Card.java - This class stores the three card types (CARD_ROCK, CARD_PAPER, CARD_SCISSORS) and a CARD_TIE card. Also has a comparison method to determine
 * the winning card.
 * @author Iliya Kiritchkov
 *
 */
public class Card {
	public static final int CARD_ROCK = 0;
	public static final int CARD_PAPER = 1;
	public static final int CARD_SCISSORS = 2;
	public static final int CARD_TIE = 3;
	
	/**
	 * Returns the winning card type when comparing two given cards. If the cards are the same, then returns a TIE card.
	 * @param card1 first card to be compared
	 * @param card2 second card to be compared
	 * @return returns winning card, or TIE card if card1 and card2 are the same
	 */
	public static int compareCards(int card1, int card2) {
		int result = -1;
		
		switch (card1) {
		case CARD_ROCK:
			switch (card2) {
			case CARD_ROCK:
				result = CARD_TIE;
				break;
			case CARD_PAPER:
				result = CARD_PAPER;
				break;
			case CARD_SCISSORS:
				result = CARD_ROCK;
				break;
			}
			break;
			
		case CARD_PAPER:
			switch (card2) {
			case CARD_ROCK:
				result = CARD_PAPER;
				break;
			case CARD_PAPER:
				result = CARD_TIE;
				break;
			case CARD_SCISSORS:
				result = CARD_SCISSORS;
				break;
			}
			break;
			
		case CARD_SCISSORS:
			switch (card2) {
			case CARD_ROCK:
				result = CARD_ROCK;
				break;
			case CARD_PAPER:
				result = CARD_SCISSORS;
				break;
			case CARD_SCISSORS:
				result = CARD_TIE;
				break;
			}
			break;
		}
		
		return result;
	}
}
