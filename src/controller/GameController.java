package controller;

import com.model.Field;
import com.model.Player;

public class GameController implements Runnable {
	public final static Player[] players = new Player[2];
	public static boolean battleContinues = true;

	private static final GameController instance = new GameController();

	private Field[] fields;

	public static void addFieldPanels(Field[] fields) {
		instance.fields = fields;
	}

	private GameController() {}

	public static GameController getInstance() {
		return instance;
	}

	@Override
	public void run() {
		int i = 0;
		while (battleContinues) {
			i = i % fields.length;
			System.out.println("\nNow playing in field " + fields[i].fieldProperties); //Used for debugging
			fields[i++].battle();

			synchronized (this) {
				try {
					wait(250);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
