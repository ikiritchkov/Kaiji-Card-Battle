package utilities;

import java.io.Serializable;

/**
 * Message.java - This class contains all of the possible message types that can be sent back and forth
 * between the client and the server.
 * 
 * @author Iliya Kiritchkov
 *
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = -2665065114989786769L;

	public final static int GAME_START = 0;
	public final static int CHAT = 1;
	public final static int GAME_MOVE = 2;
	public final static int GAME_ROUND_WON = 3;
	public final static int GAME_ROUND_LOST = 4;
	public final static int GAME_ROUND_TIE = 5;
	public final static int GAME_OVER = 6;
	public final static int PING = 7;
	public final static int GAME_GREETING_EXCHANGE = 8;
	public final static int GAME_OPPONENT_EXITED = 9;
	public final static int GAME_PLAYER_EXITED = 10;
	public final static int GAME_SAME_OPPONENT = 12;
	public final static int GAME_RESET = 13;
	
	String username;
	int messageType;
	String messageContent;
	
	/**
	 * Default constructor for a Message object.
	 */
	public Message() {
		
	}
	
	/**
	 * Constructs a Message object with a username, a message type, and message content
	 * @param username username of the message sender
	 * @param messageType type of message to be sent
	 * @param messageContent contents of the message
	 */
	public Message(String username, int messageType, String messageContent) {
		this.username = username;
		this.messageType = messageType;
		this.messageContent = messageContent;
	}

	/**
	 * Gets the username of the message.
	 * @return username of the message
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the message.
	 * @param username username of the message
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the message type of the message.
	 * @return message type of the message
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * Sets the message type of the message.
	 * @param messageType message type of the message
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * Gets the message contents of the message.
	 * @return message contents of the message
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * Sets the contents of the message.
	 * @param messageContent contents of the message
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
