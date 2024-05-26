package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GameBoardController {

	
	private Deck deck;
	private PlayerHand playerHand, dealerHand;
	boolean isStanding;
	boolean turn;	// false player, true
	private Timeline tl;
	private boolean haveHiddenCard;
	
    @FXML
    private HBox dealerHandGrid;
    @FXML
    private Label dealerScoreLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private HBox playerHandGrid;
    @FXML
    private Label playerScoreLabel;
    @FXML
    private Button hitBtn;
    @FXML
    private Button standBtn;
    @FXML
    private Button resetBtn;
    
    @FXML
    public void initialize() {
    	// init timeline for the dealer
    	tl = new Timeline();
    	tl.setCycleCount(Timeline.INDEFINITE);
    	tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.3), e -> {
    		// if first draw, remove hidden card
    		if (haveHiddenCard) {
    			haveHiddenCard = false;
    			dealerHandGrid.getChildren().remove(1);
    		}
        	// add card to dealer
        	addCard(false);
        	// if score is equal or greater than 17, end draw, and stop tl
        	if (dealerHand.isOver16()) {
        		tl.stop();
        		gameOver();
        	}
    	}));
    	isStanding = false;
    	turn = false;
    	// init vars
    	deck = new Deck(false);
    	playerHand = new PlayerHand();
    	dealerHand = new PlayerHand();
    	// reset for the game to start
    	reset();
    }
    
    public void addCard(boolean toPlayer) {
    	// draw card
    	Card card = deck.draw();
    	// add it
    	addCard(toPlayer, card);
    }
    public void addCard(boolean toPlayer, Card card) {
    	Label scoreLabel = toPlayer? playerScoreLabel : dealerScoreLabel;
    	HBox grid = toPlayer? playerHandGrid : dealerHandGrid;
    	PlayerHand hand = toPlayer? playerHand : dealerHand;
    	// add card to hand
    	hand.addCard(card);
    	// add to grid and update score
    	grid.getChildren().add(card.getImage());
    	scoreLabel.setText(hand.getScoreString());
    }
    
    public void reset() {
    	// reset hands
    	playerHand.reset();
    	dealerHand.reset();
    	// reset board
    	playerScoreLabel.setText("0");
    	dealerScoreLabel.setText("0");
    	infoLabel.setText("");
    	playerHandGrid.getChildren().clear();
    	dealerHandGrid.getChildren().clear();
    	// reset vars
    	deck.reset(true);
    	isStanding = false;
    	turn = false;
    	haveHiddenCard = true;
    	// if hit button is disabled, enable it
    	if (hitBtn.isDisable())
    		hitBtn.setDisable(false);
    	// if stand button is disabled, enable it
    	if (standBtn.isDisable())
    		standBtn.setDisable(false);

    	// add two cards to player
    	for (int i=0;i<2; i++) {
    		addCard(true);
    	}
    	// and one to dealer, and add an hidden card as well
    	addCard(false);
    	dealerHandGrid.getChildren().add(Card.getImage(null, null));
    }
    
    @FXML
    void hitBtnClick(ActionEvent event) {
    	// if not at player's turn, return
    	if (turn)
    		return;
    	// add card to player
    	addCard(true);
    	// if busted or have 21, disable hit button
    	if (playerHand.have21() || playerHand.isBusted()) {
    		hitBtn.setDisable(true);
    	}
    }

    @FXML
    void standBtnClick(ActionEvent event) {
    	// if not at player's turn, return
    	if (turn)
    		return;
    	// end turn
		endPlayerTurn();
		// start dealer turn
    	tl.playFromStart();
    }
    

    @FXML
    void resetBtnClick(ActionEvent event) {
    	reset();
    }
    
    public void endPlayerTurn() {
		turn = true;	// dealer turn
		// disable buttons
		hitBtn.setDisable(true);
		standBtn.setDisable(true);
    }
    
    public void gameOver() {
    	int diff;
    	// if the player busted, its a loss
    	if (playerHand.isBusted())
    		playerLoss();
    	// else if dealer busted, its a win
    	else if (dealerHand.isBusted())
    		playerWin();
    	// else depend on the dealer score
    	else {
    		diff = playerHand.getScore() - dealerHand.getScore();
    		// if equal, its a tie
    		if (diff == 0)
    			playerTie();
    		else if (diff > 0)
    			playerWin();
    		else
    			playerLoss();
    	}
    }
    
    public void playerTie() {
    	infoLabel.setText(String.format("%sIt's a tie!",playerHand.have21()? "Blackjack! ":""));
    }
    
    public void playerLoss() {
    	infoLabel.setText("You Lose!");
    }

    public void playerWin() {
    	infoLabel.setText(String.format("%sYou Win!",playerHand.have21()? "Blackjack! ":""));
    }
}
