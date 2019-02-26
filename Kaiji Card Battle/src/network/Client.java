package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import graphical.GameSceneCreator;
import graphical.ClientDriver;
import javafx.application.Platform;
import utilities.Card;
import utilities.Debug;
import utilities.InputListener;
import utilities.Message;

/**
 * Client.java - This class handles all of the communication between the client and the server. 
 * It creates an InputListener object that notifies it whenever a message comes in from the server.
 * The client object sends out messages to the server based on the setup or game play sequence at the time.
 * 
 * @author Iliya Kiritchkov
 *
 */
public class Client implements Observer {
	private Debug debug = new Debug(1);

	private Socket socket;
	private String playerUsername;
	private String serverAddress;
	
	private String opponentName;

	private ObjectOutputStream oos;

	private InputListener inputListener;

	/**
	 * Constructs a client object. The client stores the username of the player as well as the ip address of the server.
	 * @param _username username of the player
	 * @param _serverAddress ip address of the server
	 */
	public Client(String _username, String _serverAddress) {
		this.playerUsername = _username;
		this.serverAddress = _serverAddress;
	}

	/**
	 * The client attempts to connect to the server ip address that has been entered in the login scene.
	 * @return true if connection was successful, false if the connection was refused for any reason
	 */
	public boolean establishConnectionToServer() {
		debug.out("trying to connect to server");
		try {
			socket = new Socket(serverAddress, 45126);
			debug.out("connected to server");
			debug.out(socket.getInetAddress().getHostName());

			oos = new ObjectOutputStream(socket.getOutputStream());
			debug.out("created outputStream");

			inputListener = new InputListener(this, socket);
			debug.out("input listener created successfully");
			inputListener.start();

		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
		debug.out("connection established");
		return true;
	}

	/**
	 * Sends a chat message to the server when the player has entered a message in the chat box of the
	 * game scene.
	 * @param chatText chat message that is to be sent to the server
	 */
	public void sendChatMessage(String chatText) {
		Message message = new Message(playerUsername, Message.CHAT, chatText);

		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a game move message to the server when the player makes a game move in the game scene.
	 * @param card card that the player chose to place on the game table
	 */
	public void sendGameMoveMessage(int card) {

		Message message = new Message (null, Message.GAME_MOVE, null);
		
		switch (card) {
		case Card.CARD_PAPER:
			message.setMessageContent(Integer.toString(Card.CARD_PAPER));
			break;
		case Card.CARD_ROCK:
			message.setMessageContent(Integer.toString(Card.CARD_ROCK));
			break;
		case Card.CARD_SCISSORS:
			message.setMessageContent(Integer.toString(Card.CARD_SCISSORS));
			break;
		}
		
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a game exited message to the server. This notifies the server that the player is exiting the application.
	 */
	public void sendGameExitedMessage() {
		Message message = new Message(null, Message.GAME_PLAYER_EXITED, null);

		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a play again message to the server. This notifies the server that the player wants to play another game
	 * with the same opponent.
	 */
	public void sendPlayAgainMessage() {
		Message message = new Message(null, Message.GAME_SAME_OPPONENT, null);

		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a game reset finished message to the server. This notifies the server that the client has finished resetting
	 * the scenes and that a new game with the same opponent can begin.
	 */
	public void sendGameResetFinishedMessage() {
		Message message = new Message(null, Message.GAME_RESET, null);
		
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable observable, Object message) {
		Message incomingMessage = (Message) message;
		
		
		switch (incomingMessage.getMessageType()) {
		
		case Message.GAME_START:
			opponentName = new String(incomingMessage.getMessageContent());
			Platform.runLater(() -> 
				ClientDriver.switchToGameScene());
			break;
			
		case Message.CHAT:
			Platform.runLater(() -> 
				GameSceneCreator.displayChatMessage(incomingMessage.getUsername(), incomingMessage.getMessageContent()));
			break;
			
		case Message.GAME_GREETING_EXCHANGE:
			
			Message gameGreetingMessage = new Message(null, Message.GAME_GREETING_EXCHANGE, playerUsername);			
			try {
				oos.writeObject(gameGreetingMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case Message.GAME_ROUND_WON:
			Platform.runLater(() -> {
				GameSceneCreator.updateGameScores(2, 0);
				GameSceneCreator.updateOpponentHandAndTable(Integer.parseInt(incomingMessage.getMessageContent()));
			});
			break;
			
		case Message.GAME_ROUND_LOST:
			Platform.runLater(() -> {
				GameSceneCreator.updateGameScores(0, 2);
				GameSceneCreator.updateOpponentHandAndTable(Integer.parseInt(incomingMessage.getMessageContent()));
			});
			break;

		case Message.GAME_ROUND_TIE:
			Platform.runLater(() -> {
				GameSceneCreator.updateGameScores(1, 1);
				GameSceneCreator.updateOpponentHandAndTable(Integer.parseInt(incomingMessage.getMessageContent()));
			});
			break;
			
		case Message.GAME_OVER:
			Platform.runLater(() -> {
				GameSceneCreator.showGameOverScreen("Well played! Do you want to play again?");
			});
			break;
			
		//Server checks if client is connected
		case Message.PING:
			//Do nothing
			break;
			
		//Reset the game board and reload the scene
		case Message.GAME_RESET:
			Platform.runLater(() -> {
				ClientDriver.restartMatch();
			});
			break;
		}
	}

	/**
	 * Gets the socket of the client connection with the server.
	 * @return socket of the client connection
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Gets the username of the player.
	 * @return username of the player
	 */
	public String getUsername() {
		return this.playerUsername;
	}
	
	/**
	 * Gets the username of the opponent.
	 * @return username of the opponent
	 */
	public String getOpponentName() {
		return this.opponentName;
	}
}