package main;

import entity.Player;

public class CollisionChecker {
	private GamePanel gamePanel;

	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public int checkHardness(Player player) {
		int entityLeftWorldX = player.getWorldX();
		int entityRightWorldX = player.getWorldX() + gamePanel.getTileSize();
		int entityTopWorldY = player.getWorldY();
		int entityButtomWorldY = player.getWorldY() + gamePanel.getTileSize();

		int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
		int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
		int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
		int entityButtomRow = entityButtomWorldY / gamePanel.getTileSize();

		switch (player.getDirection()) {
		case "UP": {
			entityTopRow = (entityTopWorldY - player.getVelocity()) / gamePanel.getTileSize();
			return gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityTopRow].getHardness();
		}
		case "DOWN": {
			entityButtomRow = (entityButtomWorldY + player.getVelocity()) / gamePanel.getTileSize();
			return gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityButtomRow].getHardness();
		}
		case "LEFT": {
			entityLeftCol = (entityLeftWorldX - player.getVelocity()) / gamePanel.getTileSize();
			return gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityTopRow].getHardness();
		}
		case "RIGHT": {
			entityRightCol = (entityRightWorldX + player.getVelocity()) / gamePanel.getTileSize();
			return gamePanel.getBlockManager().map.getMapTileNum()[entityRightCol][entityTopRow].getHardness();
		}
		case "STAND": {
			return gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityTopRow].getHardness();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + player.getDirection());

		}
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

}
