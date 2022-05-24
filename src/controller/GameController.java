package controller;

import com.model.Field;
import com.model.Player;
import com.model.Soldier;
import com.view.MainView;
import com.view.component.FieldProperties;

public class GameController implements Runnable {
	public final static Player[] players = new Player[2];

	public static int step = 1;

	private static final GameController instance = new GameController();

	private final Field[] fields = new Field[FieldProperties.values().length];

	private static int waitingTime = 200;

	private GameController() {
		FieldProperties[] values = FieldProperties.values();
		for (int i = 0; i < values.length; i++) {
			fields[i] = new Field(values[i]);
		}
	}

	public static GameController getInstance() {
		return instance;
	}

	public static Field[] getFields() {
		return instance.fields;
	}

	@Override
	public synchronized void run() {
		int i = 0;
		while (!Thread.interrupted()) {
			i = i % fields.length;
			System.out.println("\nNow playing in field " + fields[i].fieldProperties); //Used for debugging

			if (!fields[i].battle()) break;
			i++;

			try {
				wait(waitingTime);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		step++;
		checkForWinner();
	}

	private void checkForWinner() {
		int numberOfPlayer1Field = 0, numberOfPlayer2Field = 0;
		for (Field field : fields) {
			if (field.getController() == players[0]) numberOfPlayer1Field++;
			else if (field.getController() == players[1]) numberOfPlayer2Field++;
		}

		int winningPlayerIndex = -1;
		if (numberOfPlayer1Field >= 3) winningPlayerIndex = 0;
		else if (numberOfPlayer2Field >= 3) winningPlayerIndex = 1;

		if (winningPlayerIndex > -1) {
			MainView.displayDialog(
					"Félicitations " + players[winningPlayerIndex].name + ", tu as gagné !\nTu peux être fière d'avoir représenté le programme " + players[winningPlayerIndex].program,
					true
			);
		}
		waitingTime = 200;
	}

	public static void skipBattle() {
		waitingTime = 10;
	}

	public static void moveSoldierToField(Soldier soldier, Field destinationField) {
		if (destinationField != null) {
			for (Field field : instance.fields) {
				if (field.contains(soldier)) field.removeSoldier(soldier);
			}
			destinationField.addSoldier(soldier);
		} else {
			soldier.sendToField(null);
		}
	}

	public static Field findFieldByProperties(FieldProperties fieldProperties) {
		for (Field field : instance.fields) {
			if (field.fieldProperties == fieldProperties) return field;
		}
		return null;
	}
}
