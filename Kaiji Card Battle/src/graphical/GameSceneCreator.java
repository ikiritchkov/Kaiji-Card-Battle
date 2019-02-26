package graphical;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import network.Client;
import utilities.Card;
import utilities.Debug;
import utilities.MusicPlayer;

/**
 * GameSceneCreator.java - This class is responsible for creating, styling and assigning functionality to the Game Scene.
 * @author Iliya Kiritchkov
 *
 */
public class GameSceneCreator {
	private Debug debug = new Debug(0);
	
	private static Media chatMessageReceived = new Media(new File("res/music/message.mp3").toURI().toString());
	private static MediaPlayer chatMessagePlayer = new MediaPlayer(chatMessageReceived);
	
	private Image cardPaperImage = new Image("file:res/images/paper.jpg");
	private Image cardRockImage = new Image("file:res/images/rock.jpg");
	private Image cardScissorImage = new Image("file:res/images/scissors.jpg");
	private Image cardBlankImage = new Image("file:res/images/blank.jpg");
	
	private ImageView cardPaperImageView1 = new ImageView(cardPaperImage);
	private ImageView cardPaperImageView2 = new ImageView(cardPaperImage);
	private ImageView cardRockImageView1 = new ImageView(cardRockImage);
	private ImageView cardRockImageView2 = new ImageView(cardRockImage);
	private ImageView cardScissorImageView1 = new ImageView(cardScissorImage);
	private ImageView cardScissorImageView2 = new ImageView(cardScissorImage);	
	private ImageView cardBlankImageView1 = new ImageView(cardBlankImage);
	private ImageView cardBlankImageView2 = new ImageView(cardBlankImage);
	private ImageView cardBlankImageView3 = new ImageView(cardBlankImage);
	private ImageView cardBlankImageView4 = new ImageView(cardBlankImage);
	private ImageView cardBlankImageView5 = new ImageView(cardBlankImage);
	private ImageView cardBlankImageView6 = new ImageView(cardBlankImage);
	private ImageView cardBlankTablePlayerHiddenImageView = new ImageView(cardBlankImage);
	private ImageView cardBlankTableOpponentHiddenImageView = new ImageView(cardBlankImage);
	private ImageView cardPaperTableOpponentPossibleImageView = new ImageView(cardPaperImage);
	private ImageView cardRockTableOpponentPossibleImageView = new ImageView(cardRockImage);
	private ImageView cardScissorOpponentPossibleImageView = new ImageView(cardScissorImage);
	
	private Pane imageViewPaneCardPaper1 = new Pane(cardPaperImageView1);
	private Pane imageViewPaneCardPaper2 = new Pane(cardPaperImageView2);
	private Pane imageViewPaneCardRock1 = new Pane(cardRockImageView1);
	private Pane imageViewPaneCardRock2 = new Pane(cardRockImageView2);
	private Pane imageViewPaneCardScissor1 = new Pane(cardScissorImageView1);
	private Pane imageViewPaneCardScissor2 = new Pane(cardScissorImageView2);
	private Pane imageViewPaneCardBlank1 = new Pane(cardBlankImageView1);
	private Pane imageViewPaneCardBlank2 = new Pane(cardBlankImageView2);
	private Pane imageViewPaneCardBlank3 = new Pane(cardBlankImageView3);
	private Pane imageViewPaneCardBlank4 = new Pane(cardBlankImageView4);
	private Pane imageViewPaneCardBlank5 = new Pane(cardBlankImageView5);
	private Pane imageViewPaneCardBlank6 = new Pane(cardBlankImageView6);
	private Pane imageViewPaneCardTablePlayerHidden = new Pane(cardBlankTablePlayerHiddenImageView);
	private Pane imageViewPaneCardTableOpponentHidden = new Pane(cardBlankTableOpponentHiddenImageView);
	private Pane imageViewPaneCardTableOpponentPaper = new Pane(cardPaperTableOpponentPossibleImageView);
	private Pane imageViewPaneCardTableOpponentRock = new Pane(cardRockTableOpponentPossibleImageView);
	private Pane imageViewPaneCardTableOpponentScissor = new Pane(cardScissorOpponentPossibleImageView);

	private static ArrayList<Pane> myCardsPaneList;
	private ArrayList<Pane> opponentCardsPaneList;
	private ArrayList<Pane> tableHiddenCardsPaneList;
	private static ArrayList<Pane> tablePossibleOpponentCardsPaneList;

	private MusicPlayer musicPlayer = MusicPlayer.getMusicPlayer();
	private static GameSceneCreator gameSceneCreator;
	private Scene gameScene;
	private static StackPane backgroundPane;
	
	private VBox gameBoard;
	private static FlowPane opponentHandBox;
	private static FlowPane opponentTableBox;
	private FlowPane playerTableBox;
	private FlowPane playerHandBox;
	
	private VBox miscBox;
	private VBox controlsBox;
	private VBox instructionsBox;
	private GridPane scoreGrid;
	
	private Label lblGameScreenTitle;
	private Label lblGameBoardOpponentName;
	private Label lblGameBoardPlayerName;
	
	private GridPane chatBox;
	private static Label lblChatLog;
	private static ScrollPane scrChatLog;
	private Label lblMessagePlayerName;
	
	private Label lblGameInstructionsTitle;
	private Label lblGameInstructionsBody;
	
	private Label lblPlayerScoreName;
	private static Label lblPlayerScorePoints;
	private Label lblOpponentScoreName;
	private static Label lblOpponentScorePoints;
	
	private TextField txtMessage;
	private static Client client;
	private Button volumeBarMinus;
	private Button volumeBarPlus;
	private Label volumeBar;
	private Font monospacedFont = new Font("Courier New", 12);
	private Font monospacedFontLarge = new Font("Courier New", 30);
	
	private String volumeBarStyleBorderColor = "-fx-border-color: rgba(150,150,150,0.8);";
	private String volumeBarStyleBorderWidth = "-fx-border-width: 2px; ";
	private String volumeBarStyleBorderRadius = "-fx-border-radius: 0px;";
	private String volumeBarStyleBackground = "-fx-background-color: white;";
	private String volumeBarStyleBackgroundRadius = "-fx-background-radius: 0px;";
	
	private String volumeButtonsBackgroundGreen = "-fx-background-color: rgba(97,216,22, 0.9);";
	private String volumeButtonsBackgroundBlue = "-fx-background-color: rgba(66, 197, 244, 0.9);";
	private String volumeButtonsStyleBorderColor = "-fx-border-color: rgba(150,150,150,0.8);";

	private double stageWidth;
	private double stageHeight;

	private Label lblScoreTitle = new Label("Score");
	
	/**
	 * Default constructor for the GameSceneCreator. The constructor assigns all children in 
	 * the Game Scene to their parents and creates a Game Scene.
	 */
	private GameSceneCreator() {
		setClient(ClientDriver.getClient());
		backgroundPane = new StackPane();

		Image loginImage = new Image("file:res/images/loginbackground.jpg", stageWidth, stageHeight, true, false);
		BackgroundSize bs = new BackgroundSize(stageWidth, stageHeight, false, false, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(loginImage, null, null, null, bs);
		backgroundPane.setBackground(new Background(backgroundImage));
		
		// Title Components
		BorderPane gameScreen = new BorderPane();
		lblGameScreenTitle = new Label("Kaiji Card Battle");

		// Game board components
		gameBoard = new VBox();
		
		myCardsPaneList = new ArrayList<Pane>();
		myCardsPaneList.add(imageViewPaneCardPaper1);
		myCardsPaneList.add(imageViewPaneCardPaper2);
		myCardsPaneList.add(imageViewPaneCardRock1);
		myCardsPaneList.add(imageViewPaneCardRock2);
		myCardsPaneList.add(imageViewPaneCardScissor1);
		myCardsPaneList.add(imageViewPaneCardScissor2);
		
		opponentCardsPaneList = new ArrayList<Pane>();
		opponentCardsPaneList.add(imageViewPaneCardBlank1);
		opponentCardsPaneList.add(imageViewPaneCardBlank2);
		opponentCardsPaneList.add(imageViewPaneCardBlank3);
		opponentCardsPaneList.add(imageViewPaneCardBlank4);
		opponentCardsPaneList.add(imageViewPaneCardBlank5);
		opponentCardsPaneList.add(imageViewPaneCardBlank6);
		
		tableHiddenCardsPaneList = new ArrayList<Pane>();
		tableHiddenCardsPaneList.add(imageViewPaneCardTablePlayerHidden);
		tableHiddenCardsPaneList.add(imageViewPaneCardTableOpponentHidden);

		tablePossibleOpponentCardsPaneList = new ArrayList<Pane>();
		tablePossibleOpponentCardsPaneList.add(Card.CARD_ROCK, imageViewPaneCardTableOpponentRock);
		tablePossibleOpponentCardsPaneList.add(Card.CARD_PAPER, imageViewPaneCardTableOpponentPaper);
		tablePossibleOpponentCardsPaneList.add(Card.CARD_SCISSORS, imageViewPaneCardTableOpponentScissor);

		lblGameBoardOpponentName = new Label(client.getOpponentName());
		
		opponentHandBox = new FlowPane();
		for (Pane p : opponentCardsPaneList) {
			opponentHandBox.getChildren().add(p);
		}
		
		opponentTableBox = new FlowPane();
		opponentTableBox.getChildren().add(imageViewPaneCardTableOpponentHidden);
		
		playerTableBox = new FlowPane();
		playerTableBox.getChildren().add(imageViewPaneCardTablePlayerHidden);

		playerHandBox = new FlowPane();
		for (Pane p : myCardsPaneList) {
			playerHandBox.getChildren().add(p);
		}
		
		lblGameBoardPlayerName = new Label(client.getUsername());

		gameBoard.getChildren().add(lblGameBoardOpponentName);
		gameBoard.getChildren().add(opponentHandBox);
		gameBoard.getChildren().add(opponentTableBox);
		gameBoard.getChildren().add(playerTableBox);
		gameBoard.getChildren().add(playerHandBox);
		gameBoard.getChildren().add(lblGameBoardPlayerName);

		// Chat box components
		lblChatLog = new Label("Welcome to Kaiji Card Battle!");
		scrChatLog = new ScrollPane();
		scrChatLog.setContent(lblChatLog);
		lblMessagePlayerName = new Label(client.getUsername() + ": ");
		txtMessage = new TextField();
		
		chatBox = new GridPane();
		chatBox.add(scrChatLog, 0, 0, 2, 1);
		chatBox.add(lblMessagePlayerName, 0, 1);
		chatBox.add(txtMessage, 1, 1);

		// Miscellaneous components
		// Controls components		
		volumeBarMinus = new Button(" - ");
		volumeBarMinus.setFocusTraversable(false);
		volumeBarMinus.setFont(monospacedFont);
		volumeBarPlus = new Button(" + ");
		volumeBarPlus.setFocusTraversable(false);
		volumeBarPlus.setFont(monospacedFont);
		volumeBar = new Label(" Music Volume - " + Math.round(musicPlayer.getVolume() * 100) + "% ");
		volumeBar.setFont(monospacedFont);
		HBox volumeBarBox = new HBox(10, volumeBarMinus, volumeBar, volumeBarPlus);
		HBox.setHgrow(volumeBar, null);
		volumeBarBox.setAlignment(Pos.CENTER);
		
		controlsBox = new VBox(volumeBarBox);

		// Instructions Components
		lblGameInstructionsTitle = new Label("Instructions");
		lblGameInstructionsBody = new Label("At the start, each player has 2 ROCK cards, 2 PAPER cards \n"
				+ "and 2 SCISSORS cards.\n"
				+ "Click on one of your cards to put it down on the table.\n"
				+ "Your opponent cannot see what card you've played until the reveal.\n"
				+ "Once both players have played a card, both cards are revealed\n"
				+ "and the score is updated.\n"
				+ "PLEASE ENJOY THIS GAME FOR YOUR PLEASURE THANK YOU\n");
		instructionsBox = new VBox(lblGameInstructionsTitle, lblGameInstructionsBody);
		
		lblPlayerScoreName = new Label(client.getUsername());
		lblPlayerScorePoints = new Label("0");
		lblOpponentScoreName = new Label(client.getOpponentName());
		lblOpponentScorePoints = new Label("0");
		
		scoreGrid = new GridPane();
		scoreGrid.add(lblScoreTitle, 0, 0, 2, 1);
		scoreGrid.add(lblPlayerScoreName, 0, 1);
		scoreGrid.add(lblPlayerScorePoints, 1, 1);
		scoreGrid.add(lblOpponentScoreName, 0, 2);
		scoreGrid.add(lblOpponentScorePoints, 1, 2);
				
		miscBox = new VBox(controlsBox, instructionsBox, scoreGrid);

		//Add all scene component functionality in separate method
		addAllSceneFunctionality();
		
		//Add scene styling
		addAllSceneStyling();
		
		// Setting all of the panels on the game screen
		gameScreen.setTop(lblGameScreenTitle);
		gameScreen.setCenter(gameBoard);
		gameScreen.setBottom(chatBox);
		gameScreen.setRight(miscBox);
		
		backgroundPane.getChildren().add(gameScreen);
		gameScene = new Scene(backgroundPane, 1000, 700);
	}
	
	/**
	 * Adds the button and pane functionality to the scene
	 */
	private void addAllSceneFunctionality() {
		
		txtMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
	            if (key.getCode().equals(KeyCode.ENTER)) {
	            	client.sendChatMessage(txtMessage.getText());
	            	txtMessage.setText("");
	            }
			}
		});
		
		//Add a window width listener
		ClientDriver.getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
			stageWidth = (double) newVal;
		});
		
		//Add a window height listener
		ClientDriver.getStage().heightProperty().addListener((old, oldVal, newVal) -> {
			stageHeight = (double) newVal;
		});
		
		volumeBarMinus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (musicPlayer.getVolume() > 0.11 ) {
					musicPlayer.setVolume(musicPlayer.getVolume() - 0.10);
					volumeBar.setText(" Music Volume - " + Math.round(musicPlayer.getVolume() * 100) + "% ");
				} else {
					musicPlayer.setVolume(0.0);
					volumeBar.setText(" Music Volume - OFF ");
				}
			}
		});
		
		volumeBarPlus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (musicPlayer.getVolume() < 0.89 ) {
					musicPlayer.setVolume(musicPlayer.getVolume() + 0.10);
					volumeBar.setText(" Music Volume - " + Math.round(musicPlayer.getVolume() * 100) + "% ");
				} else {
					musicPlayer.setVolume(1.0);
					volumeBar.setText(" Music Volume - MAX ");
				}
			}
		});
		
		//Card functionality
		for (Pane thisPane : myCardsPaneList) {
			
			thisPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					thisPane.setStyle("-fx-border-width: 2px;"
							+ "-fx-border-color: gold;"
							+ "-fx-border-radius: 2px;");
				}
			});
			
			thisPane.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					thisPane.setStyle("-fx-border-width: 2px;"
							+ "-fx-border-color: blue;"
							+ "-fx-border-radius: 2px;");
				}
			});
			
			thisPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					//Disable all cards
					for (Pane everyPaneInHand : myCardsPaneList) {
						everyPaneInHand.setDisable(true);						
					} 
					
					//Send a game move message to the server
					if (thisPane == myCardsPaneList.get(0) || thisPane == myCardsPaneList.get(1)) {
						client.sendGameMoveMessage(Card.CARD_PAPER);
						debug.out("client clicked on a PAPER card");
						
					} else if (thisPane == myCardsPaneList.get(2) || thisPane == myCardsPaneList.get(3)) {
						client.sendGameMoveMessage(Card.CARD_ROCK);
						debug.out("client clicked on a ROCK card");
						
					} else {
						client.sendGameMoveMessage(Card.CARD_SCISSORS);
						debug.out("client clicked on a SCISSOR card");
					}
					
					thisPane.setMouseTransparent(true);
					playerHandBox.getChildren().remove(thisPane);
					playerTableBox.getChildren().remove(0);
					playerTableBox.getChildren().add(thisPane);
				}
			});
		}
	}
	
	/**
	 * Adds all node styling to the scene. 
	 */
	private void addAllSceneStyling() {
		//Background
		backgroundPane.setStyle("-fx-padding: 30px");
		
		//Title
		lblGameScreenTitle.setFont(monospacedFontLarge);
		lblGameScreenTitle.setTextFill(Color.AZURE);
		lblGameScreenTitle.setStyle("-fx-padding: 10px;"
				+ "-fx-border-width: 2px; "
				+ "-fx-border-color: black; "
				+ "-fx-background-color: rgb(82, 125, 131);"
				+ "-fx-background-radius: 25px;"
				+ "-fx-border-radius: 25px;");
		BorderPane.setAlignment(lblGameScreenTitle, Pos.TOP_CENTER);
		
		//Game board styling
		gameBoard.setAlignment(Pos.CENTER);
		gameBoard.setSpacing(30);
		gameBoard.setStyle("-fx-padding: 10px;"
				+ "-fx-border-width: 2px; "
				+ "-fx-border-color: black; "
				+ "-fx-background-color: rgb(244, 66, 92);"
				+ "-fx-background-radius: 25px;"
				+ "-fx-border-radius: 25px;");
		
		lblGameBoardOpponentName.setFont(monospacedFontLarge);
		lblGameBoardPlayerName.setFont(monospacedFontLarge);
		
		for (Pane p : myCardsPaneList) {
			ImageView iv = (ImageView) p.getChildren().get(0);
	        
			iv.setFitWidth(40);
	        iv.setFitHeight(80);
	        iv.setSmooth(true);
	        iv.setCache(true);
	        p.setStyle("-fx-border-width: 2px;"
					+ "-fx-border-color: blue;"
					+ "-fx-border-radius: 2px;");
		}
		
		for (Pane p : opponentCardsPaneList) {
			ImageView iv = (ImageView) p.getChildren().get(0);
	        
			iv.setFitWidth(40);
	        iv.setFitHeight(80);
	        iv.setSmooth(true);
	        iv.setCache(true);
			p.setStyle("-fx-border-width: 2px;"
					+ "-fx-border-color: blue;"
					+ "-fx-border-radius: 2px;");
		}
		
		for (Pane p : tableHiddenCardsPaneList) {
			ImageView iv = (ImageView) p.getChildren().get(0);
	        
			iv.setFitWidth(40);
	        iv.setFitHeight(80);
	        iv.setSmooth(true);
	        iv.setCache(true);	
			p.setStyle("-fx-border-width: 2px;"
					+ "-fx-border-color: blue;"
					+ "-fx-border-radius: 2px;");
		}
		
		for (Pane p : tablePossibleOpponentCardsPaneList) {
			ImageView iv = (ImageView) p.getChildren().get(0);
	        
			iv.setFitWidth(40);
	        iv.setFitHeight(80);
	        iv.setSmooth(true);
	        iv.setCache(true);	
			p.setStyle("-fx-border-width: 2px;"
					+ "-fx-border-color: blue;"
					+ "-fx-border-radius: 2px;");
		}
		
		opponentHandBox.setAlignment(Pos.CENTER);
		opponentHandBox.setHgap(30);
		
		opponentTableBox.setAlignment(Pos.CENTER);
		opponentTableBox.getChildren().get(0).setVisible(false);
		
		playerTableBox.setAlignment(Pos.CENTER);
		playerTableBox.getChildren().get(0).setVisible(false);
		
		playerHandBox.setAlignment(Pos.CENTER);
		playerHandBox.setHgap(30);
		
		//Chat box
		chatBox.setStyle("-fx-padding: 10px;"
				+ "-fx-border-width: 2px; "
				+ "-fx-border-color: black; "
				+ "-fx-background-color: rgb(82, 125, 131);"
				+ "-fx-background-radius: 25px;"
				+ "-fx-border-radius: 25px;");
		scrChatLog.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrChatLog.setPrefHeight(100);
		
		lblChatLog.setAlignment(Pos.TOP_LEFT);
		GridPane.setHgrow(txtMessage, Priority.ALWAYS);
		
		//Volume buttons
		volumeBarStyleBorderRadius = "-fx-border-radius: " + volumeBar.getHeight() + "px;";
		volumeBarStyleBackgroundRadius = "-fx-background-radius: " + volumeBar.getHeight() + "px;";
		volumeBar.setStyle(volumeBarStyleBorderColor
				+ volumeBarStyleBorderWidth
				+ volumeBarStyleBorderRadius
				+ volumeBarStyleBackground
				+ volumeBarStyleBackgroundRadius);
		
		volumeBarMinus.setStyle(volumeButtonsStyleBorderColor
				+ volumeBarStyleBorderWidth
				+ volumeBarStyleBorderRadius
				+ volumeBarStyleBackgroundRadius
				+ volumeButtonsBackgroundBlue
				+ "-fx-pref-width: " + (volumeBarPlus.getWidth() + 1) + "px;");
		volumeBarPlus.setStyle(volumeButtonsStyleBorderColor
				+ volumeBarStyleBorderWidth
				+ volumeBarStyleBorderRadius
				+ volumeButtonsBackgroundGreen
				+ volumeBarStyleBackgroundRadius);
		
		//Miscellaneous box styling
		miscBox.setStyle("-fx-padding: 10px;"
				+ "-fx-border-width: 2px; "
				+ "-fx-border-color: black; "
				+ "-fx-background-color: rgb(82, 125, 131);"
				+ "-fx-background-radius: 25px;"
				+ "-fx-border-radius: 25px;");
		
		//Controls box styling
		controlsBox.setAlignment(Pos.CENTER);
		controlsBox.setSpacing(15);
				
		//Instructions box styling
		instructionsBox.setStyle("-fx-padding: 10px;");
		instructionsBox.setSpacing(5);
		lblGameInstructionsTitle.setFont(monospacedFontLarge);
		lblGameInstructionsTitle.setAlignment(Pos.CENTER);
		lblGameInstructionsBody.setFont(monospacedFont);
	
		//Score box styling
		ColumnConstraints scoreBoxColumnName = new ColumnConstraints();
		ColumnConstraints scoreBoxColumnPoints = new ColumnConstraints();
		scoreBoxColumnName.setPercentWidth(80);
		scoreBoxColumnPoints.setPercentWidth(20);
		scoreGrid.getColumnConstraints().addAll(scoreBoxColumnName, scoreBoxColumnPoints);
		scoreGrid.setStyle("-fx-padding: 15px;");
		lblScoreTitle.setFont(monospacedFontLarge);
		GridPane.setHalignment(lblPlayerScorePoints, HPos.RIGHT);
		GridPane.setHalignment(lblOpponentScorePoints, HPos.RIGHT);
	}
	
	/**
	 * Sets the client variable contained in this class. The client contains information on the player and their opponent.
	 * @param c Client object to be stored
	 */
	public void setClient(Client c) {
		client = c;
	}

	/**
	 * Gets the static class instance of the GameSceneCreator. This class uses a Singleton pattern in order to maintain only 
	 * one instance of the GameSceneCreator class at any time. 
	 * @return gameSceneCreator 
	 */
	static GameSceneCreator getSceneCreator() {
		if (gameSceneCreator == null) {
			gameSceneCreator = new GameSceneCreator();
		}
		return gameSceneCreator;
	}

	/**
	 * Gets the game scene that has been created by the GameSceneCreator.
	 * @return gameScene - game scene that contains the entire play area, including instructions and chat log 
	 */
	public Scene getScene() {
		return gameScene;
	}
	
	/**
	 * Updates the chat log in the game scene to show the new message. Includes the username and the chat message.
	 * Plays a sound when the opponent sent the new chat message.
	 * @param username username of the chat message to be displayed
	 * @param chatMessage chat message to be displayed
	 */
	public static void displayChatMessage(String username, String chatMessage) {
		String currentText = lblChatLog.getText();
		String newText = currentText + "\n" + username + ": " + chatMessage;
		lblChatLog.setText(newText);
		scrChatLog.setVvalue(1.0);
		
		if (!username.equals(client.getUsername())) {
			chatMessagePlayer.seek(Duration.ZERO);
			chatMessagePlayer.setVolume(0.3);
			chatMessagePlayer.play();
		}
	}
	
	/**
	 * Updates the game score of the player and their opponent in the game scene. 
	 * Once both scores are updated, the player's card are enabled. Requires both player 
	 * and opponent score. The points passed in are added to the existing scores in the 
	 * game scene. 
	 * @param playerNewPoints points to be added to the player's score
	 * @param opponentNewPoints points to be added to the opponent's score
	 */
	public static void updateGameScores(int playerNewPoints, int opponentNewPoints) {
		int currentPlayerPoints = Integer.parseInt(lblPlayerScorePoints.getText());
		int currentOpponentPoints = Integer.parseInt(lblOpponentScorePoints.getText());
		currentPlayerPoints += playerNewPoints;
		currentOpponentPoints += opponentNewPoints;
		lblPlayerScorePoints.setText(Integer.toString(currentPlayerPoints));
		lblOpponentScorePoints.setText(Integer.toString(currentOpponentPoints));
		
		for (Pane p : myCardsPaneList) {
			p.setDisable(false);
		}
	}
	
	/**
	 * Updates the opponent's hand by removing a single card, updates the opponent's 
	 * table by removing the existing card and replaces it with the opponentCard 
	 * that is passed in.
	 * @param opponentCard card to be displayed on the opponent's side of the play table
	 */
	public static void updateOpponentHandAndTable(int opponentCard) {
		opponentHandBox.getChildren().remove(0);
		opponentTableBox.getChildren().remove(0);
		switch (opponentCard) {
		case Card.CARD_PAPER:
			opponentTableBox.getChildren().add(tablePossibleOpponentCardsPaneList.get(Card.CARD_PAPER));
			break;
		case Card.CARD_ROCK:
			opponentTableBox.getChildren().add(tablePossibleOpponentCardsPaneList.get(Card.CARD_ROCK));
			break;
		case Card.CARD_SCISSORS:
			opponentTableBox.getChildren().add(tablePossibleOpponentCardsPaneList.get(Card.CARD_SCISSORS));
			break;
		}
	}
	
	/**
	 * Resets the game scene creator and all static variables.
	 */
	public static void resetGameScreen() {
		gameSceneCreator = null;
	}
	
	/**
	 * Displays the game over screen and asks the player if they want to play again.
	 * @param question question about playing again
	 */
	public static void showGameOverScreen(String question) {
		StackPane popUp = new PopUpScreen(question).getPopUpScreen();
		backgroundPane.getChildren().add(popUp);
		popUp.setScaleX(.5);
		popUp.setScaleY(.5);
		StackPane.setAlignment(popUp, Pos.CENTER);
	}
	
	/**
	 * Receives the response to the game over question. Calls the client to send a response to the server.
	 * @param response response to the game over screen question
	 */
	public void receiveResponse(boolean response) {
		if (response == true) {
			client.sendPlayAgainMessage();
		} else {
			client.sendGameExitedMessage();
			Platform.exit();
		}
	}
}