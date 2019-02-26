package graphical;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.Client;

/**
 * ClientDriver.java - JavaFX stage driver and scene switch controller for the Kaiji Card Battle client-side application.
 * @author Iliya Kiritchkov
 *
 */
public class ClientDriver extends Application {
	private static Stage stage;
	private static Client client;
	
	/**
	 * Main method of the ClientDriver class. This executes the launch(args) method which in turn executes the start(Stage _stage) method.
	 * @param args arguments passed into the main method before runtime. It is recommended that no parameters are passed.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage _stage) {
		stage = _stage;
		stage.getIcons().add(new Image("file:res/images/scissors.png"));
		stage.setTitle("Kaiji Card Battle");
		stage.setFullScreenExitHint("");
		switchToLoginScene();
		stage.show();
		
		//Replace maximize with full screen
		stage.maximizedProperty().addListener((obs, oldVal, newVal) -> {
			if (stage.isMaximized() == true) {
				stage.setMaximized(false);
				stage.setFullScreen(true);
			}
		});
		
		stage.setOnCloseRequest(event -> {
			if (client == null || client.getSocket() == null) {
				Platform.exit();
			} else {
				client.sendGameExitedMessage();
				Platform.exit();
			}
		});
	}
	
	/**
	 * Gets the Stage of the Application.
	 * @return stage
	 */
	public static Stage getStage() {
		return stage;
	}
	
	/**
	 * Switch the scene on the stage to the Game Scene.
	 */
	public static void switchToGameScene() {
		stage.setScene(GameSceneCreator.getSceneCreator().getScene());
		stage.setFullScreen(true);
	}
	
	/**
	 * Switch the scene on the stage to the Login Scene.
	 */
	public static void switchToLoginScene() {
		stage.setScene(LoginSceneCreator.getSceneCreator().getScene());
		stage.setFullScreen(true);
	}
	
	/**
	 * Get the client object. This is used to share client information between the scenes.
	 * @return client
	 */
	public static Client getClient() {
		return client;
	}
	
	/**
	 * Sets the client. This is used to share client information between the scenes.
	 * @param c client to be set
	 */
	public static void setClient(Client c) {
		client = c;
	}
	
	/**
	 * Resets the game scene which allows two clients to play each other again.
	 */
	public static void restartMatch() {
		switchToLoginScene();
		GameSceneCreator.resetGameScreen();
		client.sendGameResetFinishedMessage();
	}
}