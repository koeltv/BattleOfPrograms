package controller;

import com.model.Field;
import com.model.Player;
import com.model.Soldier;
import com.view.MainView;
import com.view.component.FieldProperties;

import java.util.Arrays;

/**
 * The controller supervising access to model and the fighting step.
 */
public class GameController implements Runnable {
	private static final GameController instance = new GameController();

	private static boolean displayTutorial = true;

	private final Player[] players = new Player[2];

	private final Field[] fields = new Field[FieldProperties.values().length];

	private int step = 1;

	private static int waitingTime = 200;

	private GameController() {
		FieldProperties[] values = FieldProperties.values();
		for (int i = 0; i < values.length; i++) {
			fields[i] = new Field(values[i]);
		}
	}

	/**
	 * Gets the single instance of {@link GameController}.
	 *
	 * @return the instance
	 */
	public static GameController getInstance() {
		return instance;
	}

	/**
	 * Get the field array.
	 *
	 * @return the fields
	 */
	public static Field[] getFields() {
		return instance.fields;
	}

	/**
	 * Get the player array.
	 *
	 * @return the players
	 */
	public static Player[] getPlayers() {
		return instance.players;
	}

	/**
	 * Gets the current step.
	 * - Player information collecting and attribute repartition is step 1
	 * - Field attribution is step 2
	 * - The 1st battle is step 3
	 * - The next attribution will be step 4, next battle step 5, etc
	 *
	 * @return the step
	 */
	public static int getStep() {
		return instance.step;
	}

	/**
	 * Increase step count.
	 */
	public static void nextStep() {
		instance.step++;
	}

	/**
	 * Check if it is the first game.
	 *
	 * @return the boolean
	 */
	public static boolean displayTutorial() {
		return displayTutorial;
	}

	/**
	 * Disable the tutorial dialogs.
	 */
	public static void passTutorial() {
		GameController.displayTutorial = false;
	}

	@Override
	public synchronized void run() {
		int i = 0;
		while (!Thread.interrupted()) {
			if (!fields[i].battle()) break;
			i = ++i % fields.length;

			try {
				wait(waitingTime);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		nextStep();
		checkForWinner();
	}

	private void checkForWinner() {
		int numberOfPlayer1Field = 0, numberOfPlayer2Field = 0;
		for (Field field : fields) {
			if (field.getController() == getPlayers()[0]) numberOfPlayer1Field++;
			else if (field.getController() == getPlayers()[1]) numberOfPlayer2Field++;
		}

		int winningPlayerIndex = -1;
		if (numberOfPlayer1Field >= 3) winningPlayerIndex = 0;
		else if (numberOfPlayer2Field >= 3) winningPlayerIndex = 1;

		if (winningPlayerIndex > -1) {
			MainView.displayDialog(
					"Félicitations " + getPlayers()[winningPlayerIndex].name + ", tu as gagné !\nTu peux être fière d'avoir représenté le programme " + getPlayers()[winningPlayerIndex].program,
					true
			);
		}
		waitingTime = 200;
	}

	/**
	 * Skip battle.
	 * This is done by reducing time intervals between each attack.
	 */
	public static void skipBattle() {
		waitingTime = 5;
	}

	/**
	 * Move soldier to field.
	 *
	 * @param soldier          the soldier
	 * @param destinationField the destination field
	 */
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

	/**
	 * Find field by properties.
	 *
	 * @param fieldProperties the field properties
	 * @return the field
	 */
	public static Field findFieldByProperties(FieldProperties fieldProperties) {
		for (Field field : instance.fields) {
			if (field.fieldProperties == fieldProperties) return field;
		}
		return null;
	}

	/**
	 * Check attribution.
	 *
	 * @param playerIndex the player index
	 * @return true if the player has assigned all his soldiers correctly, false otherwise
	 */
	public static boolean checkAttribution(int playerIndex) {
		return Arrays.stream(getPlayers()[playerIndex].soldiers).filter(soldier -> !soldier.isReservist()).noneMatch(soldier -> soldier.getAssignedField() == null) &&
				Arrays.stream(instance.fields).noneMatch(field -> field.getPlayerSoldiers(getPlayers()[playerIndex]).size() < 1);
	}

	/**
	 * Reset the {@link GameController}.
	 */
	public static void reset() {
		Arrays.fill(getPlayers(), null);
		FieldProperties[] values = FieldProperties.values();
		for (int i = 0; i < values.length; i++) {
			instance.fields[i] = new Field(values[i]);
		}
		instance.step = 1;
	}
}
