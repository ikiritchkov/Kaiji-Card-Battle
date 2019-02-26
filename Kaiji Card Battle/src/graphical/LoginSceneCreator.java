package graphical;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import network.Client;
import utilities.Debug;
import utilities.MusicPlayer;

/**
 * LoginSceneCreator.java - This class is responsible for creation, styling and functionality of the login scene.
 * Once the user has entered the server ip address and their username, they can connect to the server.
 * If the connection is sucessful, the game scene will be shown and the match will begin.
 * @author Iliya Kiritchkov
 *
 */
public class LoginSceneCreator {
	private Debug debug = new Debug(0);
	
	private MusicPlayer musicPlayer = MusicPlayer.getMusicPlayer();
	private static LoginSceneCreator loginSceneCreator;
	private Scene loginScene;
	private GridPane volumeBarPane;
	private BorderPane rightPane;
	
	private Font monospacedFont = new Font("Courier New", 12);
	private Font monospacedFontLarge = new Font("Courier New", 30);
	
	private Label volumeBar;
	private Button volumeBarMinus;
	private Button volumeBarPlus;
	
	private Label lblEnterServerIP;
	private Label lblEnterUsername;
	private double lblsTranslated = 82;
	
	private double stageHeight = 500;
	private double stageWidth = 1000;
	
	private String volumeBarStyleBorderColor = "-fx-border-color: rgba(150,150,150,0.8);";
	private String volumeBarStyleBorderWidth = "-fx-border-width: 2px; ";
	private String volumeBarStyleBorderRadius = "-fx-border-radius: 0px;";
	private String volumeBarStyleBackground = "-fx-background-color: white;";
	private String volumeBarStyleBackgroundRadius = "-fx-background-radius: 0px;";
	
	private String volumeButtonsBackgroundGreen = "-fx-background-color: rgba(97,216,22, 0.9);";
	private String volumeButtonsBackgroundBlue = "-fx-background-color: rgba(66, 197, 244, 0.9);";
	private String volumeButtonsStyleBorderColor = "-fx-border-color: rgba(150,150,150,0.8);";
			
	/**
	 * Constructs a LoginSceneCreator. The creator is responsible for creation, styling and functionality
	 * of the login scene.
	 */
	private LoginSceneCreator() {
		StackPane backgroundPane = new StackPane();
		loginScene = new Scene(backgroundPane, stageWidth, stageHeight);
		
		Image loginImage = new Image("file:res/images/loginbackground.jpg", stageWidth, stageHeight, true, false);
		BackgroundSize bs = new BackgroundSize(stageWidth, stageHeight, false, false, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(loginImage, null, null, null, bs);
		backgroundPane.setBackground(new Background(backgroundImage));
		
		BorderPane borderPane = new BorderPane();
		backgroundPane.getChildren().add(borderPane);
		
		volumeBarPane = new GridPane();
		rightPane = new BorderPane();
		rightPane.setStyle("-fx-padding: 0px 10px 0px 20px;");
		borderPane.setStyle("-fx-padding: 30px;");
		borderPane.setLeft(volumeBarPane);
		borderPane.setRight(rightPane);
				
		volumeBarMinus = new Button(" - ");
		volumeBarMinus.setFocusTraversable(false);
		volumeBarMinus.setFont(monospacedFont);
		volumeBarPlus = new Button(" + ");
		volumeBarPlus.setFocusTraversable(false);
		volumeBarPlus.setFont(monospacedFont);
		volumeBar = new Label(" Music Volume - " + Math.round(musicPlayer.getVolume() * 100) + "% ");
		volumeBar.setFont(monospacedFont);
		HBox hBoxVolumeControl = new HBox(10, volumeBarMinus, volumeBar, volumeBarPlus);
		HBox.setHgrow(volumeBar, null);
		hBoxVolumeControl.setAlignment(Pos.CENTER);
		volumeBarPane.add(hBoxVolumeControl, 1, 1);
		
		Label lblCredits = new Label("A Game Coded By\n"
				+ "Iliya Kiritchkov\n\n"
				+ "Inspired By\n"
				+ "Gyakkyou Burai Kaiji: Ultimate Survivor\n\n"
				+ "All Assets Borrowed From The Internet");
		lblCredits.setWrapText(true);
		lblCredits.setTextAlignment(TextAlignment.CENTER);
		lblCredits.setStyle("-fx-padding: 5px; -fx-background-color: rgb(82, 125, 131); -fx-text-fill: rgb(97, 226, 54); -fx-background-radius: 20px;");
		rightPane.setBottom(lblCredits);
		
		Button btnQuitGame = new Button("Quit Game");
		btnQuitGame.setStyle("-fx-background-color: red;");
		btnQuitGame.setFocusTraversable(false);
		rightPane.setTop(btnQuitGame);
		BorderPane.setAlignment(btnQuitGame, Pos.TOP_RIGHT);
		
		GridPane loginFormGrid = new GridPane();
		loginFormGrid.setStyle("-fx-border-width: 2px; "
				+ "-fx-border-color: black; "
				+ "-fx-background-color: rgb(82, 125, 131);"
				+ "-fx-background-radius: 25px;"
				+ "-fx-border-radius: 25px;");
		loginFormGrid.setHgap(10);
		loginFormGrid.setVgap(15);
		loginFormGrid.setMaxSize(350, 240);
		
		Label lblTitle = new Label("KAIJI CARD BATTLE");
		lblTitle.setFont(monospacedFontLarge);
		loginFormGrid.add(lblTitle, 1, 1, 2, 1);
		
		lblEnterServerIP = new Label("Server IP");
		lblEnterServerIP.setFont(monospacedFont);
		lblEnterServerIP.setMouseTransparent(true);
		TextField txtServerIP = new TextField();
		txtServerIP.setFont(monospacedFont);
		
		lblEnterUsername = new Label("Username");
		lblEnterUsername.setFont(monospacedFont);
		lblEnterUsername.setMouseTransparent(true);

		TextField txtUsername = new TextField();
		txtUsername.setFont(monospacedFont);
		txtServerIP.setMaxWidth(150);
		txtUsername.setMaxWidth(150);
		
		Label lblErrorMessage = new Label();
		Button btnConnect = new Button("Connect");
		
		loginFormGrid.add(lblEnterServerIP, 1, 2);
		loginFormGrid.add(txtServerIP, 2, 2);
		loginFormGrid.add(lblEnterUsername, 1, 3);
		loginFormGrid.add(txtUsername, 2, 3);
		loginFormGrid.add(lblErrorMessage, 1, 4, 2, 1);
		loginFormGrid.add(btnConnect, 1, 5, 2, 1);
		//loginFormGrid.add(lblErrorMessage, 1, 6, 2, 1);
		loginFormGrid.setAlignment(Pos.CENTER);
		
		lblEnterServerIP.toFront();
		lblEnterServerIP.setTranslateX(lblsTranslated);
		
		lblEnterUsername.toFront();
		lblEnterUsername.setTranslateX(lblsTranslated);
		
		GridPane.setHalignment(btnConnect, HPos.CENTER);

		borderPane.setCenter(loginFormGrid);
		
		//Add a window width listener
		ClientDriver.getStage().widthProperty().addListener((obs, oldVal, newVal) -> {
			stageWidth = (double) newVal;
		});
		
		//Add a window height listener
		ClientDriver.getStage().heightProperty().addListener((old, oldVal, newVal) -> {
			stageHeight = (double) newVal;
		});

		//Update login scene after it has loaded
		ClientDriver.getStage().setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				debug.out("setonShown triggered on login");
				updateVolumeBarAfterSceneLoad();
			}
		});
		
		ClientDriver.getStage().fullScreenProperty().addListener((obs, oldVal, newVal) -> {
			if (ClientDriver.getStage().isFullScreen() == true) {
				btnQuitGame.setTranslateY(0);
			} else {
				btnQuitGame.setTranslateY(-999);
			}
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
		
		txtUsername.focusedProperty().addListener((obs, newVal, oldVal) -> {
			TranslateTransition tr = new TranslateTransition(Duration.millis(500), lblEnterUsername);
			if (txtUsername.isFocused() == true) {
				tr.setToX(0);
			} else if (txtUsername.getText().isEmpty()) {
				tr.setToX(lblsTranslated);
			}
			tr.play();
		});
		
		txtServerIP.focusedProperty().addListener((obs, newVal, oldVal) -> {
			TranslateTransition tr = new TranslateTransition(Duration.millis(500), lblEnterServerIP);
			if (txtServerIP.isFocused() == true) {
				tr.setToX(0);
			} else if (txtServerIP.getText().isEmpty()) {
				tr.setToX(lblsTranslated);
			}
			tr.play();
		});
		
		btnQuitGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnConnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				lblErrorMessage.setText("Connecting...");
				if (!txtUsername.getText().equals("") && !txtServerIP.getText().equals("")) {
					Client c = new Client(txtUsername.getText(), txtServerIP.getText());
					ClientDriver.setClient(c);
					if (c.establishConnectionToServer()) {
						txtServerIP.setDisable(true);
						txtUsername.setDisable(true);
						lblErrorMessage.setText("Waiting for an opponent to connect!");
						btnConnect.setDisable(true);
					} else {
						lblErrorMessage.setText("Failed to connect to server!!!!");
					}
				} else {
					lblErrorMessage.setText("Enter an address and a username!");
				}
			}
		});

		//Same functionality as hitting the connect button
		txtUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
	            if (event.getCode().equals(KeyCode.ENTER)) {
					lblErrorMessage.setText("Connecting...");
					if (!txtUsername.getText().equals("") && !txtServerIP.getText().equals("")) {
						Client c = new Client(txtUsername.getText(), txtServerIP.getText());
						ClientDriver.setClient(c);
						if (c.establishConnectionToServer()) {
							txtServerIP.setDisable(true);
							txtUsername.setDisable(true);
							lblErrorMessage.setText("Waiting for an opponent to connect!");
							btnConnect.setDisable(true);
						} else {
							lblErrorMessage.setText("Failed to connect to server!!!!");
						}
					} else {
						lblErrorMessage.setText("Enter an address and a username!");
					}
	            }
	        }
		});
	}
	
	/**
	 * Updates the styling of the volume controls after the scene has been set on the stage.
	 */
	private void updateVolumeBarAfterSceneLoad() {
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
	}
	
	/**
	 * Gets the static class instance of the LoginSceneCreator. This class uses a Singleton pattern in order to maintain only 
	 * one instance of the loginSceneCreator class at any time. 
	 * @return loginSceneCreator 
	 */
	static LoginSceneCreator getSceneCreator() {
		if (loginSceneCreator == null) {
			loginSceneCreator = new LoginSceneCreator();
		}
		return loginSceneCreator;
	}

	/**
	 * Gets the login scene that has been created by the LoginSceneCreator.
	 * @return loginScene - login scene which has a form, including the server ip address and the username
	 */
	Scene getScene() {
		return loginScene;
	}
}
