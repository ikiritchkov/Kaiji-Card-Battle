package utilities;

/**
 * Debug.java - Utility class that allows displaying debug messages to the console terminal.
 *	
 * @author Iliya Kiritchkov
 * @version September 19, 2018
 *
 */
public final class Debug {
	
	private static int level;
	
	/**
	 * Construct a Debug object that sets the level of detail to be displayed for debugging messages.
	 * Level 0 - No debug information is displayed. This is for production.
	 * Level 1 - All debug information is displayed to the console.
	 * @param level - Level of debug information to be displayed.
	 */
	public Debug(int level) {
		Debug.level = level;
	}
	
	/**
	 * Display a message in the console terminal if the debug level has been set to 1, otherwise do not display message.
	 * @param debugString - String message to be displayed in the console terminal.
	 */
	public void out (String debugString) {
		if (level == 1) {
			System.out.println(debugString);
		}
	}
}
