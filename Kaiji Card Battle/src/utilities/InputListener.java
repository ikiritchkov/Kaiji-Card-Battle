package utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * InputListener.java - This class is responsible for listening to the ObjectInputStream of a Socket.
 * Whenever a new object comes in through the input stream, the Observer is notified. This class is Threadable.
 * @author Iliya Kiritchkov
 *
 */
public class InputListener extends Observable implements Runnable{
	Debug debug = new Debug(1);
	
	Socket socket;
	ObjectInputStream ios;
	

	/**
	 * Constructs an InputListener object with an Observer object.
	 * @param observer observer that is added to the list of observers watching this input listener
	 * @param socket socket that creates the InputStream
	 */
	public InputListener(Observer observer, Socket socket) {
		addObserver(observer);
		this.socket = socket;
	}
	
	@Override
	public void run() {
		debug.out("Input Listener starting to listen");
		
		try {
			this.ios = new ObjectInputStream(socket.getInputStream());
			while(socket.isConnected()) {
				Object message = ios.readObject();
				setChanged();
				notifyObservers(message);
			}
			ios.close();
			
		} catch (IOException e1) {
			debug.out("Client disconnected");
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the InputListener thread.
	 */
	public void start() {
		Thread th = new Thread(this);
		th.start();
	}
	
	/**
	 * Gets the Socket that this input listener is listening to.
	 * @return socket of this input listener
	 */
	public Socket getSocket() {
		return this.socket;
	}
	
	/**
	 * Gets the object input stream that this input listener is listening to.
	 * @return objectInputStream that this listener is listening to
	 */
	public ObjectInputStream getObjectInputStream() {
		return ios;
	}
}