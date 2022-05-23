package controller;

import com.model.Player;
import com.view.panel.FieldPanel;

public class GameController implements Runnable {
	public final static Player[] players = new Player[2];
	public static boolean battleContinues = true;

	private static final GameController instance = new GameController();

	private FieldPanel[] fieldPanels;

	public static void addFieldPanels(FieldPanel[] fieldPanels) {
		instance.fieldPanels = fieldPanels;
	}

	private GameController() {}

	public static GameController getInstance() {
		return instance;
	}

	@Override
	public void run() {
		int i = 0;
		while (battleContinues) {
			System.out.println("Now playing in field " + i);

			synchronized (this) {
				try {
					wait(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			i = i % fieldPanels.length;
			fieldPanels[i++].battle();
		}
	}
}
