package object;

import main.GamePanel;

public class GasStation extends GameObject {
	public GasStation(GamePanel gamepanal, int worldX, int worldY, int width, int height) {
		super(gamepanal, worldX, worldY, width, height);
		resorceImage = gamePanel.getImageGasStation();
	}

	public void buyFuel() {
		gamePanel.playSE(3);
		while (gamePanel.getPlayer().getMoney() > 0 && gamePanel.getPlayer().getFuel() < 100) {
			gamePanel.getPlayer().setMoney(gamePanel.getPlayer().getMoney() - 1);
			gamePanel.getPlayer().setFuel(gamePanel.getPlayer().getFuel() + 1);
		}
	}
}
