package graphical;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * PopUpScreen.java - This class creates a pane with a question that returns a yes or no response.
 * @author Iliya Kiritchkov
 *
 */
public class PopUpScreen {
	private StackPane popUpBackground;
	private boolean response = false;
	private GameSceneCreator gameSceneCreator = GameSceneCreator.getSceneCreator();
	private Font monospacedFontLarge = new Font("Courier New", 30);
	private Font monospacedFont = new Font("Courier New", 30);

	/**
	 * Constructs a PopUpScreen object with a parameter of a String question.
	 * @param question question to be asked
	 */
	public PopUpScreen(String question) {
		popUpBackground = new StackPane();
		GridPane popUpForeground = new GridPane();
		popUpForeground.setStyle("-fx-background-color: rgba(99,99,99, 0.6);"
				+ "-fx-padding: 30px");
		popUpBackground.getChildren().add(popUpForeground);
		Label popUpQuestion = new Label(question);
		popUpQuestion.setFont(monospacedFontLarge);
		
		Button btnPopUpYes = new Button("Yes");
		btnPopUpYes.setFont(monospacedFont);
		
		Button btnPopUpNo = new Button("No");
		btnPopUpNo.setFont(monospacedFont);
		HBox yesNoBox = new HBox(btnPopUpYes, btnPopUpNo);
		
		popUpForeground.add(popUpQuestion, 0, 0);
		popUpForeground.add(yesNoBox, 0, 1);
		
		StackPane.setAlignment(popUpBackground, Pos.CENTER);
		
		btnPopUpYes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				response = true;
				gameSceneCreator.receiveResponse(response);
			}
		});
		
		btnPopUpNo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				response = false;
				gameSceneCreator.receiveResponse(response);
			}
		});
	}
	
	/**
	 * Get the PopUpScreen StackPane object after it has been created.
	 * @return popUpScreen object
	 */
	public StackPane getPopUpScreen() {
		return popUpBackground;
	}
	
	/**
	 * Gets the boolean reponse to the question. This is set when one of the buttons is clicked.
	 * @return response boolean that is set when one of the buttons is clicked
	 */
	public boolean getResponseToQuestion() {
		return response;
	}
}